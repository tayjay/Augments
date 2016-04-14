package com.tayjay.augments.augment.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;

/**
 * Created by tayjm_000 on 2016-03-26.
 * An augment extending this will allow it to display elements on the player's screen.
 */
public interface IHUDProvider
{
    /**
     * Display elements on the GUI overlay. 2d elements, text, meters and the such
     * @param stack
     * @param event
     */
    void displayGameOverlay(ItemStack stack, RenderGameOverlayEvent event);

    /**
     * Display things in the world. 3d elements.
     * @param stack
     * @param event
     */
    void displayInWorldElements(ItemStack stack, RenderWorldLastEvent event);
}
