package com.tayjay.augments.capability;

import com.tayjay.augments.api.AugmentsAPI;
import com.tayjay.augments.api.capabilities.IAugHolderProvider;
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
 * Created by tayjay on 2016-06-25.
 */
public class AugmentHolderImpl
{
    public static void init()
    {
        CapabilityManager.INSTANCE.register(IAugHolderProvider.class,
                new Capability.IStorage<IAugHolderProvider>()
                {
                    @Override
                    public NBTBase writeNBT(Capability<IAugHolderProvider> capability, IAugHolderProvider instance, EnumFacing side)
                    {
                        return instance.serializeNBT();
                    }

                    @Override
                    public void readNBT(Capability<IAugHolderProvider> capability, IAugHolderProvider instance, EnumFacing side, NBTBase nbt)
                    {
                        if(nbt instanceof NBTTagCompound)
                            instance.deserializeNBT((NBTTagCompound)nbt);
                    }
                },DefaultImpl.class);
    }

    private static class DefaultImpl implements IAugHolderProvider
    {
        private IItemHandler augmentsInventory;
        private final String TAG_NAME = "augments";

        public DefaultImpl()
        {
            this(0);
        }

        public DefaultImpl(int size)
        {
            this.augmentsInventory = new ItemStackHandler(size);
        }
        @Override
        public IItemHandler getAugments()
        {
            return augmentsInventory;
        }

        @Override
        public int getSize()
        {
            return augmentsInventory.getSlots();
        }

        @Override
        public void sync(EntityPlayerMP player)
        {

        }

        private NBTTagCompound writeNBT()
        {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("size",this.augmentsInventory.getSlots());
            NBTBase inv = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,augmentsInventory,null);
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
                IItemHandler inv = new ItemStackHandler(nbt.getInteger("size"));
                CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,inv,null,nbt.getTag(TAG_NAME));
                augmentsInventory = inv;
            }
        }
    }
    public static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {
        public static final ResourceLocation NAME = new ResourceLocation("augments","augment_storage");

        private final IAugHolderProvider cap;

        public Provider()
        {
            this(0);
        }

        public Provider(int size)
        {
            cap = new DefaultImpl(size);
        }

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
        {
            return capability == AugmentsAPI.AUGMENT_HOLDER_CAPABILITY;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
        {
            if(capability == AugmentsAPI.AUGMENT_HOLDER_CAPABILITY)
                return AugmentsAPI.AUGMENT_HOLDER_CAPABILITY.cast(cap);
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
}
