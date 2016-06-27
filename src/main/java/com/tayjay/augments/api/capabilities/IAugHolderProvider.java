package com.tayjay.augments.api.capabilities;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

/**
 * Created by tayjay on 2016-06-25.
 */
public interface IAugHolderProvider extends INBTSerializable<NBTTagCompound>
{
    IItemHandler getAugments();

    int getSize();

    void sync(EntityPlayerMP player);
}
