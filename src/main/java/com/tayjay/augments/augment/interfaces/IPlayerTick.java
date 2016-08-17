package com.tayjay.augments.augment.interfaces;

import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Defines that this Augment will run something on Player Tick
 * Created by tayjm_000 on 2016-01-18.
 */
public interface IPlayerTick
{
    /**
     * Holds actions to be executed on player tick
     * @param augment ItemStack this augment is in
     * @param player Player augment is in
     */
    void onTick(ItemStack augment, EntityPlayer player);
}