package com.tayjay.augments.client.renderer;

import com.tayjay.augments.entity.EntityCyborg;
import com.tayjay.augments.lib.Reference;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by tayjm_000 on 2016-03-26.
 */
public class RenderCyborg extends RenderLiving
{
    private static final ResourceLocation defaultTexture = new ResourceLocation("textures/entity/EntityZombie.png");

    private static final ResourceLocation[] textures = {new ResourceLocation(Reference.MOD_ID+":textures/entity/EntityCyborgTier1.png"),
                                                        new ResourceLocation(Reference.MOD_ID+":textures/entity/EntityCyborgTier2.png"),
                                                        new ResourceLocation(Reference.MOD_ID+":textures/entity/EntityCyborgTier3.png")};

    public RenderCyborg(ModelBase p_i1262_1_, float p_i1262_2_)
    {
        super(p_i1262_1_, p_i1262_2_);
    }


    protected ResourceLocation getEntityTexture(EntityCyborg entity)
    {
        int tier;
        if((tier = (int)entity.getDataWatcher().getWatchableObjectByte(27))>0)
            return textures[tier-1];
        else
            return defaultTexture;
    }

    @Override
    public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);

    }



    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntityCyborg)entity);
    }
}
