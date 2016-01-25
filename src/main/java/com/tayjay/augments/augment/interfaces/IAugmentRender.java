package com.tayjay.augments.augment.interfaces;

import net.minecraftforge.client.event.RenderLivingEvent;

/**
 * Defines augment to cause rendering on attached entity
 * Created by tayjm_000 on 2016-01-18.
 */
public interface IAugmentRender
{
    /**
     * Perform specific render operations
     * @param event     Event to render in
     */
    void doRender(RenderLivingEvent event);
}
