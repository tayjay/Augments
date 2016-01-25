package com.tayjay.augments.augment.interfaces;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Base Interface for augments to inherit from.
 * Created by tayjm_000 on 2016-01-18.
 */
public interface IAugment
{
    /**
     * Get the name of this Augment
     * @return  Augment Name
     */
    String getAugmentName();

    /**
     * Get this Augment
     * @return  this
     */
    IAugment getAugment();

    /**
     * Get the tier of this augment
     * @return  Augment's tier
     */
    byte getTier();

    /**
     * Code to be run when this augment is added<br/>
     * Here you can handle adding NBT data,
     * binding this to the entity,
     * running code to change entity attribues,
     * saving default values.
     */
    void onAdd(ItemStack stack, EntityLivingBase entity);

    /**
     * Code to be run when this augment is removed<br/>
     * Here you can handle
     * resetting NBT and default values,
     * running code to change entity back,
     * release this from any watchers it may be on.
     */
    void onRemove(ItemStack stack, EntityLivingBase entity);

    void writeAugmentToNBT(NBTTagCompound tag);

    void readAugmentFromNBT(NBTTagCompound tag);
}
