package com.tayjay.augments.capability;

import com.tayjay.augments.api.AugmentsAPI;
import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.item.IEnergySupply;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.network.packets.PacketSyncPlayerData;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

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
        private final String TAG_NAME = "player_aug_data";

        public DefaultImpl(){this(null);}

        public DefaultImpl(EntityPlayer player)
        {
            this.player = player;
        }


        @Override
        public void sync(EntityPlayerMP player)
        {
            NetworkHandler.INSTANCE.sendTo(new PacketSyncPlayerData(writeNBT(),player),player);
        }

        @Override
        public float getMaxEnergy()
        {
            /*
            if(CapHelper.getPlayerPartsCap(this.player).getPartsInv().getStackInSlot(7).getItem() == null)
            {
                if (CapHelper.getPlayerPartsCap(this.player).getPartsInv().getStackInSlot(7).getItem() instanceof IEnergySupply)
                {
                    IEnergySupply supply = (IEnergySupply) CapHelper.getPlayerPartsCap(this.player).getPartsInv().getStackInSlot(7).getItem();
                    maxEnergy = supply.maxEnergy(CapHelper.getPlayerPartsCap(this.player).getPartsInv().getStackInSlot(7));
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
            currentEnergy = (currentEnergy/10)*10;
            if(currentEnergy>=maxEnergy-0.05)
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
                this.currentEnergy-=energyIn;
                return true;
            }
            return false;

        }

        @Override
        public void rechargeTick()
        {
            addEnergy(getRechargeRate());
        }

        private NBTTagCompound writeNBT()
        {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setFloat("maxE",this.maxEnergy);
            tag.setFloat("curE",this.currentEnergy);
            tag.setFloat("recharge",this.rechargeRate);
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
