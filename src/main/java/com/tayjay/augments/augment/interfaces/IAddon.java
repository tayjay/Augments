package com.tayjay.augments.augment.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by tayjm_000 on 2016-04-04.
 */
public interface IAddon
{
    String getAddonName();
    NBTTagCompound writeToNBT(ItemStack stack);
    IAddon readFromNBT(NBTTagCompound tag);
}
