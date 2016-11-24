package com.tayjay.augments.capability;

import com.tayjay.augments.Augments;
import com.tayjay.augments.api.AugmentsAPI;
import com.tayjay.augments.api.capabilities.IPlayerBodyProvider;
import com.tayjay.augments.api.item.IAugment;
import com.tayjay.augments.api.item.IBodyPart;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.network.packets.PacketSyncPlayerBody;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

/**
 * Created by tayjay on 2016-06-24.
 */
public final class PlayerBodyImpl
{
    public static void init()
    {
        CapabilityManager.INSTANCE.register(IPlayerBodyProvider.class,
                new Capability.IStorage<IPlayerBodyProvider>()
        {
            @Override
            public NBTBase writeNBT(Capability<IPlayerBodyProvider> capability, IPlayerBodyProvider instance, EnumFacing side)
            {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IPlayerBodyProvider> capability, IPlayerBodyProvider instance, EnumFacing side, NBTBase nbt)
            {
                if(nbt instanceof NBTTagCompound)
                    instance.deserializeNBT((NBTTagCompound)nbt);
            }
        },DefaultImpl.class);
    }

    private static class DefaultImpl implements IPlayerBodyProvider
    {
        private final int SLOTS_COUNT = PartType.values().length+2;
        private EntityPlayer player;
        private IItemHandler partInventory;
        private IItemHandler augmentInventory;
        private final String TAG_NAME_PARTS = "player_parts";
        private final String TAG_NAME_AUGS = "player_augs";

        public DefaultImpl(){this(null);}

        public DefaultImpl(EntityPlayer player)
        {
            this.player = player;
            partInventory = new ItemStackHandler(SLOTS_COUNT);
            augmentInventory = new ItemStackHandler(Augments.maxAllowedAugmentCapacity);
        }

        @Override
        public IItemHandler getBodyParts()
        {
            return partInventory;
        }

        @Override
        public ItemStack getStackByPart(PartType type)
        {
            if(type == PartType.ARM || type == PartType.LEG)
            {
                LogHelper.error("Tried to get PartType of sided bodypart without stating side, defaulting to right!");
                return getStackByPartSided(type,1);
            }
            else
                return partInventory.getStackInSlot(type.ordinal());
        }

        @Override
        public ItemStack getStackByPartSided(PartType type, int side)
        {
            if(side<0)
                side = 0;
            if(side > 1)
                side = 1;
            if(type==PartType.ARM)
            {
                return partInventory.getStackInSlot(PartType.ARM.ordinal()+side);
            }
            else if(type==PartType.LEG)
            {
                return partInventory.getStackInSlot(1+PartType.LEG.ordinal()+side);
            }
            else
            {
                LogHelper.debug("Tried to get non-sided bodypart with side, ignoring side.");
                return getStackByPart(type);
            }
        }

        @Override
        public IItemHandler getAugments()
        {
            return augmentInventory;
        }

        @Override
        public int getAugmentCapacity()
        {
            if(getStackByPart(PartType.TORSO)!=null)
                return Math.min(CapHelper.getPlayerDataCap(this.player).getPlayerTier()+3+((IBodyPart)getStackByPart(PartType.TORSO).getItem()).getTier(getStackByPart(PartType.TORSO)),augmentInventory.getSlots());
            else
                return Math.min(CapHelper.getPlayerDataCap(this.player).getPlayerTier()+3,augmentInventory.getSlots());
        }

        private NBTTagCompound writeNBT()
        {
            NBTTagCompound tag = new NBTTagCompound();
            NBTBase parts = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,partInventory,null);
            NBTBase augments = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,augmentInventory,null);
            tag.setTag(TAG_NAME_PARTS,parts);
            tag.setTag(TAG_NAME_AUGS,augments);
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
            if(nbt.hasKey(TAG_NAME_PARTS))
            {
                IItemHandler parts = new ItemStackHandler(SLOTS_COUNT);
                CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, parts, null, nbt.getTag(TAG_NAME_PARTS));
                partInventory = parts;
            }
            if(nbt.hasKey(TAG_NAME_AUGS))
            {
                IItemHandler augments = new ItemStackHandler(Augments.maxAllowedAugmentCapacity);
                CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,augments,null,nbt.getTag(TAG_NAME_AUGS));
                augmentInventory = augments;
            }
        }


        @Override
        public void sync(EntityPlayerMP player)
        {
            NetworkHandler.sendTo(new PacketSyncPlayerBody(writeNBT(),player),player);
        }

        @Override
        public void syncToOther(EntityPlayerMP player, EntityPlayerMP other)
        {
            NetworkHandler.sendTo(new PacketSyncPlayerBody(writeNBT(),player),other);
        }

        @Override
        public int getHash()
        {
            int hash = 7;
            for(int i = 0;i<SLOTS_COUNT;i++)
            {
                if(partInventory.getStackInSlot(i)!=null)
                    hash = hash + partInventory.getStackInSlot(i).toString().hashCode();
            }
            for(int i = 0;i<augmentInventory.getSlots();i++)
            {
                if(augmentInventory.getStackInSlot(i)!=null)
                    hash = hash + augmentInventory.getStackInSlot(i).toString().hashCode();
            }
            return hash;
        }


    }

    public static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {
        public static final ResourceLocation NAME = new ResourceLocation("augments","player_parts");

        private final IPlayerBodyProvider cap;

        public Provider(){this(null);}

        public Provider(EntityPlayer player){cap = new DefaultImpl(player);}

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
        {
            return capability == AugmentsAPI.PLAYER_BODY_CAPABILITY;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
        {
            if(capability == AugmentsAPI.PLAYER_BODY_CAPABILITY)
                return AugmentsAPI.PLAYER_BODY_CAPABILITY.cast(cap);
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
    private PlayerBodyImpl(){}


}
