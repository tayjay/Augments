package com.tayjay.augments.api.capabilities.impl;

import com.tayjay.augments.api.AugmentsAPI;
import com.tayjay.augments.api.capabilities.IPlayerPartsProvider;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.network.packets.PacketSyncPlayerParts;
import net.minecraft.entity.player.EntityPlayerMP;
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
public final class PlayerPartsImpl
{
    public static void init()
    {
        CapabilityManager.INSTANCE.register(IPlayerPartsProvider.class,
                new Capability.IStorage<IPlayerPartsProvider>()
        {
            @Override
            public NBTBase writeNBT(Capability<IPlayerPartsProvider> capability, IPlayerPartsProvider instance, EnumFacing side)
            {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IPlayerPartsProvider> capability, IPlayerPartsProvider instance, EnumFacing side, NBTBase nbt)
            {
                if(nbt instanceof NBTTagCompound)
                    instance.deserializeNBT((NBTTagCompound)nbt);
            }
        },DefaultImpl.class);
    }

    private static class DefaultImpl implements IPlayerPartsProvider
    {
        private final int SLOTS_COUNT = 8;
        private IItemHandler partInventory = new ItemStackHandler(SLOTS_COUNT);
        private final String TAG_NAME = "player_parts";

        @Override
        public IItemHandler getPartsInv()
        {
            return partInventory;
        }

        private NBTTagCompound writeNBT()
        {
            NBTTagCompound tag = new NBTTagCompound();
            NBTBase inv = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,partInventory,null);
            tag.setTag(TAG_NAME,inv);
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
            if(nbt.hasKey(TAG_NAME))
            {
                IItemHandler inv = new ItemStackHandler(SLOTS_COUNT);
                CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,inv,null,nbt.getTag(TAG_NAME));
                partInventory = inv;
            }
        }


        @Override
        public void sync(EntityPlayerMP player)
        {
            NetworkHandler.INSTANCE.sendTo(new PacketSyncPlayerParts(writeNBT(),player),player);
        }

        @Override
        public void syncToOther(EntityPlayerMP player, EntityPlayerMP other)
        {
            NetworkHandler.INSTANCE.sendTo(new PacketSyncPlayerParts(writeNBT(),player),other);
        }


    }

    public static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {
        public static final ResourceLocation NAME = new ResourceLocation("augments","player_parts");

        private final IPlayerPartsProvider cap = new DefaultImpl();


        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
        {
            return capability == AugmentsAPI.PLAYER_PARTS_CAPABILITY;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
        {
            if(capability == AugmentsAPI.PLAYER_PARTS_CAPABILITY)
                return AugmentsAPI.PLAYER_PARTS_CAPABILITY.cast(cap);
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
    private PlayerPartsImpl(){}
}
