package com.tayjay.augments.capability;

import com.tayjay.augments.api.AugmentsAPI;
import com.tayjay.augments.api.capabilities.IAugDataProvider;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

/**
 * Created by tayjay on 2016-10-16.
 */
public class AugmentDataImpl
{
    public static void init()
    {
        CapabilityManager.INSTANCE.register(IAugDataProvider.class,
                new Capability.IStorage<IAugDataProvider>()
                {
                    @Override
                    public NBTBase writeNBT(Capability<IAugDataProvider> capability, IAugDataProvider instance, EnumFacing side)
                    {
                        return instance.serializeNBT();
                    }

                    @Override
                    public void readNBT(Capability<IAugDataProvider> capability, IAugDataProvider instance, EnumFacing side, NBTBase nbt)
                    {
                        if(nbt instanceof NBTTagCompound)
                            instance.deserializeNBT((NBTTagCompound)nbt);
                    }
                },DefaultImpl.class);
    }

    private static class DefaultImpl implements IAugDataProvider
    {
        private boolean active;
        private final String TAG_NAME = "augData";

        public DefaultImpl()
        {
            this(true);
        }

        public DefaultImpl(boolean active)
        {
            this.active = active;
        }

        @Override
        public boolean isActive()
        {
            return active;
        }

        @Override
        public void setActive(boolean active)
        {
            this.active = active;
        }

        private NBTTagCompound writeNBT()
        {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setBoolean("active",this.active);
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
            active = nbt.getBoolean("active");
        }
    }
    public static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {
        public static final ResourceLocation NAME = new ResourceLocation("augments","augment_data");

        IAugDataProvider cap;

        public Provider()
        {
            cap = new DefaultImpl();
        }

        public Provider(boolean active){cap = new DefaultImpl(active);}
        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
        {
            return capability == AugmentsAPI.AUGMENT_DATA_CAPABILITY;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
        {
            if(capability == AugmentsAPI.AUGMENT_DATA_CAPABILITY)
                return AugmentsAPI.AUGMENT_DATA_CAPABILITY.cast(cap);
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
