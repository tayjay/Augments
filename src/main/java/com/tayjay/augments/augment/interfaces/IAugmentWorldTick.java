package com.tayjay.augments.augment.interfaces;

import cpw.mods.fml.common.gameevent.TickEvent;

/**
 * Defines that this Augment will run something on World Tick.<br/>
 * Created by tayjm_000 on 2016-01-18.
 */
public interface IAugmentWorldTick
{
    /**
     * Holds events to be executed on world tick
     * @param event Event that world is ticking in
     */
    void doTick(TickEvent.WorldTickEvent event);
}
