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
    /**
     * Get all itemstacks of augments in the holder
     * @return  ItemHandler containing Augment Stacks
     */
    IItemHandler getAugments();

    /**
     * Get capacity of augments held by this holder
     * @return  Augment capacity
     */
    int getSize();

    /**
     * Sync Holder to player
     * @param player
     */
    void sync(EntityPlayerMP player);
}
