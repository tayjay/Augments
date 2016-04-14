package com.tayjay.augments.augment.interfaces;

import cpw.mods.fml.common.eventhandler.Event;
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

    /**
     * Can this augment be added to this Entity.<br>
     * Check the Entity's augments and type of Entity it is.
     * @param stack
     * @param addingTo
     * @return
     */
    boolean canAdd(ItemStack stack, EntityLivingBase addingTo);

    /**
     * Code to be run on any kind of event. Generic event handler. May replace in future<br>
     *     This could be PlayerTick, LivingUpdate, DeathEvent, LivingHurt, etc.<br>
     *         To handle certain events use "instanceof" function.
     * @param itemStack Itemstack this augment is in.
     * @param event Event to be handled
     */
    void onEvent(ItemStack itemStack, Event event);
    //todo: Maybe Replace with specialized methods

}
