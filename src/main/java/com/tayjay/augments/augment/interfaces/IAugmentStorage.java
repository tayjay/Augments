package com.tayjay.augments.augment.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by tayjm_000 on 2016-04-08.
 * An augment implementing this interface will be able to hold an inventory of Augments.<br/>
 * Rather than store all augments on the player. Each augment will hold it's own.<br/>
 * Example: Torso can contain augments for energy and special abilities.<br/>
 */
public interface IAugmentStorage
{
    IAugment[] getStoredAugments(ItemStack stack);

    void addAugmentToStorage(ItemStack stack,IAugment augment);

    void removeAugmentFromStorage(ItemStack stack,IAugment augment);

    int storedAugmentCount(ItemStack stack);

    void readStoredAugmentsFromNBT(NBTTagCompound tag);

    void writeStoredAugmentsToNBT(NBTTagCompound tag);
}
