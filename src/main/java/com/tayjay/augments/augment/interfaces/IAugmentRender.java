package com.tayjay.augments.augment.interfaces;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;

/**
 * Defines augment to cause rendering on attached entity
 * Created by tayjm_000 on 2016-01-18.
 */
public interface IAugmentRender
{
    /**
     * Perform specific render operations
     * @param e     Event to render in (Cast to proper type)
     */
    void doRender(ItemStack s,Event e); //todo: Make this available for all render events



    enum RenderType
    {
        WORLD,WORLD_LAST,PLAYER,PLAYER_SPECIALS,LIVING,HAND
    }
}
