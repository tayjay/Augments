package com.tayjay.augments.api.capabilities.impl;

import com.tayjay.augments.api.AugmentsAPI;
import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

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
        private final String TAG_NAME = "player_data";

        @Override
        public void sync(EntityPlayerMP player)
        {

        }

        @Override
        public EntityPlayer getPlayer()
        {
            return this.player;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            return null;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt)
        {

        }
    }

    public static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {
        public static final ResourceLocation NAME = new ResourceLocation("augments","player_data");
        private final IPlayerDataProvider cap = new DefaultImpl();


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
