package com.tayjay.augments.augment.interfaces;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Base Interface for augments to inherit from.<br/>
 * This will cause them to act like items in the way that they are simply reference for the stack to access.
 * Created by tayjm_000 on 2016-01-18.
 */
public interface IAugment
{

    String getUnlocalizedName();

    String getName();

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



    /**
     * Can this augment be added to this BodyPart Inventory.<br>
     * Check the BodyPart's augments and type of BodyPart it is.
     * @param augment
     * @param bodyPart
     * @return
     */
    boolean canAdd(ItemStack augment, ItemStack bodyPart);

}
