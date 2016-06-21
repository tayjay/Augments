package com.tayjay.augments.augment.interfaces;

import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

/**
 * Created by tayjay on 2016-06-17.
 */
public interface ILivingAttack
{
    /**
     * To be ran on a living entity.
     * @param event
     */
    void onLivingAttack(LivingAttackEvent event);

    /**
     * To be ran on a player.
     * @param event
     */
    void onAttackEntityEvent(AttackEntityEvent event);
}
