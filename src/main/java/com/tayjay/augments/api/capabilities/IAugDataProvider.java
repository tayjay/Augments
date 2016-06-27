package com.tayjay.augments.api.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

/**
 * Created by tayjay on 2016-06-26.
 * Store any special data about an Augment's itemstack.
 */
public interface IAugDataProvider extends ICapabilitySerializable<NBTTagCompound>
{
}
