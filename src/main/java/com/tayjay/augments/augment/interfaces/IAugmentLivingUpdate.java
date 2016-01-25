package com.tayjay.augments.augment.interfaces;

import net.minecraftforge.event.entity.living.LivingEvent;

/**
 * This augment will do something on the LivingUpdateEvent.<br/>
 * This is used for running events where an entity needs to tick.<br/>
 * Created by tayjm_000 on 2016-01-18.
 */
public interface IAugmentLivingUpdate
{
    /**
     * Code to be ran when the attached entity ticks.
     * @param event Event that entity is updating in
     */
    void doTick(LivingEvent.LivingUpdateEvent event);
}
