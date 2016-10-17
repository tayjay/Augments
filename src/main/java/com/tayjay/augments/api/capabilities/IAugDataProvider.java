package com.tayjay.augments.api.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * Created by tayjay on 2016-06-26.
 * Store any special data about an Augment's itemstack.
 */
public interface IAugDataProvider extends INBTSerializable<NBTTagCompound>
{
    /**
     * Save whether the attached augment should do it's action
     * @return
     */
    boolean isActive();

    void setActive(boolean active);
}
