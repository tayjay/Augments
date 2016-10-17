package com.tayjay.augments.api.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-06-30.
 * An augment that preforms an action when the activate key is pressed.
 */
@Deprecated
public interface IActivate
{
    /**
     * Do validation and active augment if it is currently selected.
     * @param augment
     * @param player
     */
    void activate(ItemStack augment, EntityPlayer player);

    boolean isActive(ItemStack augment,ItemStack bodyPart,EntityPlayer player);
}
