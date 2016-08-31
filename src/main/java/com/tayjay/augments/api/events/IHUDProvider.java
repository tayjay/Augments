package com.tayjay.augments.api.events;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;

/**
 * Created by tayjay on 2016-06-25.
 * A Body Part that draws elements on the gui or in the world, such as the eyes.
 */
public interface IHUDProvider
{
    void drawWorldElements(ItemStack stack, RenderWorldLastEvent event);

    void drawHudElements(ItemStack stack, RenderGameOverlayEvent event);
}
