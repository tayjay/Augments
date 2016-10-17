package com.tayjay.augments.capability;

import com.google.common.collect.Lists;
import com.tayjay.augments.Augments;
import com.tayjay.augments.api.AugmentsAPI;
import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.events.IActivate;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.network.packets.PacketChangeEnergy;
import com.tayjay.augments.network.packets.PacketCycleAugment;
import com.tayjay.augments.network.packets.PacketSyncPlayerData;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.ChatHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by tayjay on 2016-06-26.
 */
public class PlayerDataImpl
{
    public static void init()
    {
        CapabilityManager.INSTANCE.register(IPlayerDataProvider.class,
                new Capability.IStorage<IPlayerDataProvider>()
                {
                    @Override
                    public NBTBase writeNBT(Capability<IPlayerDataProvider> capability, IPlayerDataProvider instance, EnumFacing side)
                    {
                        return instance.serializeNBT();
                    }

                    @Override
                    public void readNBT(Capability<IPlayerDataProvider> capability, IPlayerDataProvider instance, EnumFacing side, NBTBase nbt)
                    {
                        if(nbt instanceof NBTTagCompound)
                            instance.deserializeNBT((NBTTagCompound)nbt);
                    }
                },DefaultImpl.class);
    }

    private static class DefaultImpl implements IPlayerDataProvider
    {
        private EntityPlayer player;

        private float maxEnergy;
        private float currentEnergy;
        private float rechargeRate;
        private long lastEnergyUseTick;
        private int currentActiveIndex;
        private byte playerTier;
        /**How many ticks after discharging until energy can charge up again*/
        private final long RECHARGE_DELAY = 40;

        /**
         * Counter for how long until player experiences effects from body rejecting augmented parts!
         * Ignored if Drug Dependancy is disabled in config.
         */
        private int drugTimer;

        private final String TAG_NAME = "player_aug_data";

        public DefaultImpl(){this(null);}

        public DefaultImpl(EntityPlayer player)
        {
            this.player = player;
            this.playerTier = 0;
            //this.currentActiveIndex = -1;
            //this.cycleActiveAugment();
        }


        @Override
        public boolean validate()
        {
            if(Augments.drugDependant)
                return drugTimer>0;
            return true;
        }

        @Override
        public void sync(EntityPlayerMP player)
        {
            NetworkHandler.sendTo(new PacketSyncPlayerData(writeNBT(),this.player),player);
        }

        @Override
        public float getMaxEnergy()
        {
            /*
            if(CapHelper.getPlayerBodyCap(this.player).getBodyParts().getStackInSlot(7).getItem() == null)
            {
                if (CapHelper.getPlayerBodyCap(this.player).getBodyParts().getStackInSlot(7).getItem() instanceof IEnergySupply)
                {
                    IEnergySupply supply = (IEnergySupply) CapHelper.getPlayerBodyCap(this.player).getBodyParts().getStackInSlot(7).getItem();
                    maxEnergy = supply.maxEnergy(CapHelper.getPlayerBodyCap(this.player).getBodyParts().getStackInSlot(7));
                }
                maxEnergy = 0;
            }
            */
            return maxEnergy;
        }

        @Override
        public void setMaxEnergy(float maxEnergy)
        {
            this.maxEnergy = maxEnergy;
        }

        @Override
        public float getCurrentEnergy()
        {
            currentEnergy = (currentEnergy/10)*10;//Round value
            if(currentEnergy>=maxEnergy-0.05)//Fix error margin with float values
                currentEnergy = maxEnergy;
            return this.currentEnergy;
        }

        @Override
        public void setCurrentEnergy(float currentEnergy)
        {
            this.currentEnergy = currentEnergy;
        }

        @Override
        public float getRechargeRate()
        {
            return this.rechargeRate;
        }

        @Override
        public void setRechargeRate(float rechargeRate)
        {
            this.rechargeRate = rechargeRate;
        }

        @Override
        public void addEnergy(float energyIn)
        {
            if(this.currentEnergy + energyIn <= this.maxEnergy)
            {
                this.currentEnergy += energyIn;
            }
            else
            {
                this.currentEnergy = maxEnergy;
            }
            if(this.currentEnergy + energyIn <=0)
            {
                this.currentEnergy = 0;
            }
        }

        @Override
        public boolean removeEnergy(float energyIn)
        {
            if(this.currentEnergy>=energyIn)
            {
                lastEnergyUseTick = player.worldObj.getTotalWorldTime();
                this.currentEnergy-=energyIn;
                if(FMLCommonHandler.instance().getEffectiveSide()== Side.CLIENT)
                    NetworkHandler.sendToServer(new PacketChangeEnergy(energyIn, PacketChangeEnergy.EnergyType.CURRENT));
                return true;
            }
            return false;

        }

        @Override
        public void rechargeTick()
        {
            if(Math.abs(lastEnergyUseTick-player.worldObj.getTotalWorldTime())>RECHARGE_DELAY)
                addEnergy(getRechargeRate());
        }

        @Override
        public void reboot()
        {
            setCurrentEnergy(0);
            ChatHelper.send(player,"Rebooting...");
            player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA,5));
        }

        @Override
        public boolean needsDrug()
        {
            return checkPlayerAugmented(player);
        }

        private boolean checkPlayerAugmented(EntityPlayer player)
        {
            IItemHandler parts = player.getCapability(AugmentsAPI.PLAYER_BODY_CAPABILITY,null).getBodyParts();
            for(int i =0;i<parts.getSlots();i++)
            {
                if(parts.getStackInSlot(i)!=null)
                    return true;
            }
            return false;
        }

        @Override
        public void doDrugTick()
        {
            if(needsDrug())
            {
                if (drugTimer-1 < 0)
                {
                    this.doDrugEffect();
                }
                else
                {
                    drugTimer--;
                }
            }
        }

        @Override
        public int getDrugTimer()
        {
            return this.drugTimer;
        }

        @Override
        public void setDrugTimer(int amount)
        {
            this.drugTimer = amount;
        }

        @Override
        public void addDrugTimer(int amount)
        {
            this.drugTimer+= amount;
        }

        @Override
        public void doDrugEffect()
        {
            if(!player.isCreative())
            {
                player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 40));
                player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 40));
                player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 40));
            }
        }

        @Override
        public ItemStack getCurrentAugment()
        {
            return CapHelper.getPlayerBodyCap(player).getAugments().getStackInSlot(currentActiveIndex);
        }

        @Override
        public void cycleActiveAugment()
        {
            currentActiveIndex = (currentActiveIndex+1)%CapHelper.getPlayerBodyCap(player).getAugmentCapacity();
            if(getCurrentAugment()==null)//Next slot is empty
            {
                int checkingIndex = (currentActiveIndex+1)%CapHelper.getPlayerBodyCap(player).getAugmentCapacity();
                while(checkingIndex!=currentActiveIndex)//Step though rest of augments until you come back to starting point
                {
                    if(CapHelper.getPlayerBodyCap(player).getAugments().getStackInSlot(checkingIndex)!=null)
                    {
                        currentActiveIndex = checkingIndex;
                        break;
                    }
                    checkingIndex = (checkingIndex+1)%CapHelper.getPlayerBodyCap(player).getAugmentCapacity();
                }
                //If found an augment, will change index to it. If not, assume there are no augments.
            }

            if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT)
                NetworkHandler.sendToServer(new PacketCycleAugment(player));
        }

        @Override
        public boolean isAugmentActive()
        {
            return CapHelper.getAugmentDataCap(getCurrentAugment()).isActive();
        }

        @Override
        public void setAugmentActive(boolean active)
        {
            CapHelper.getAugmentDataCap(getCurrentAugment()).setActive(active);
        }

        @Override
        public byte getPlayerTier()
        {
            return this.playerTier;
        }

        private NBTTagCompound writeNBT()
        {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setFloat("maxE",this.maxEnergy);
            tag.setFloat("curE",this.currentEnergy);
            tag.setFloat("recharge",this.rechargeRate);
            tag.setInteger("drugTimer",this.drugTimer);
            tag.setInteger("activeAugmentIndex",this.currentActiveIndex);
            tag.setByte("playerTier",this.playerTier);
            return tag;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            return writeNBT();
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt)
        {
            if(nbt.hasKey("maxE"))
            {
                this.maxEnergy = nbt.getFloat("maxE");
            }
            if(nbt.hasKey("curE"))
            {
                this.currentEnergy = nbt.getFloat("curE");
            }
            if(nbt.hasKey("recharge"))
            {
                this.rechargeRate = nbt.getFloat("recharge");
            }
            if(nbt.hasKey("drugTimer"))
                this.drugTimer = nbt.getInteger("drugTimer");
            if(nbt.hasKey("activeAugmentIndex"))
                this.currentActiveIndex = nbt.getInteger("activeAugmentIndex");
            if(nbt.hasKey("playerTier"))
                this.playerTier = nbt.getByte("playerTier");
        }
    }

    public static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {
        public static final ResourceLocation NAME = new ResourceLocation("augments","player_data");
        private final IPlayerDataProvider cap;

        public Provider(){this(null);}

        public Provider(EntityPlayer player){cap = new DefaultImpl(player);}


        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
        {
            return capability == AugmentsAPI.PLAYER_DATA_CAPABILITY;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
        {
            if(capability == AugmentsAPI.PLAYER_DATA_CAPABILITY)
                return AugmentsAPI.PLAYER_DATA_CAPABILITY.cast(cap);
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            return cap.serializeNBT();
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt)
        {
            cap.deserializeNBT(nbt);
        }
    }
    private PlayerDataImpl(){}
}
