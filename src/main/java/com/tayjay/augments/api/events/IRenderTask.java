package com.tayjay.augments.api.events;

import net.minecraft.client.renderer.entity.Render;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Created by tayjay on 2016-06-30.
 */
public interface IRenderTask<T extends Event>
{
    long maxAge();
    long startTime();
    boolean shouldRemove();
    void render(T event);
}
