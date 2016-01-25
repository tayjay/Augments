package com.tayjay.augments.augment.interfaces;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Interface for Player specific augments
 * Created by tayjm_000 on 2016-01-18.
 */
public interface IPlayerAugment
{
    /**
     * Get the player attached to this augment
     * @return  Attached Player
     */
    EntityPlayer getPlayer();
}
