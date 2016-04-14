package com.tayjay.augments.init;

import com.tayjay.augments.client.model.ModelCyborg;
import com.tayjay.augments.client.renderer.RenderCyborg;
import com.tayjay.augments.entity.EntityCyborg;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.model.ModelBiped;

/**
 * Created by tayjm_000 on 2016-03-26.
 */
public class EntityRenders
{
    public static void init()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityCyborg.class,new RenderCyborg(new ModelCyborg(),0.5F));
    }
}
