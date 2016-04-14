package com.tayjay.augments.augment.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by tayjm_000 on 2016-04-04.
 */
public interface IComponent
{
    String getComponentName();
    NBTTagCompound writeToNBT(ItemStack stack);
    IComponent readFromNBT(NBTTagCompound tag);
}
