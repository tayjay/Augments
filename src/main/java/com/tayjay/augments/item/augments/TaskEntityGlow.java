package com.tayjay.augments.item.augments;

import com.sun.scenario.DelayedRunnable;
import com.tayjay.augments.api.events.IRenderTask;
import com.tayjay.augments.event.AugmentEvents;
import com.tayjay.augments.util.ReflectHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tayjay on 2016-06-30.
 */
public class TaskEntityGlow implements IRenderTask<RenderWorldLastEvent>
{
    private List<Entity> entities;
    private int delay;
    private long startTime;
    public TaskEntityGlow(List<Entity> entities, int duration)
    {
        this.entities = entities;
        this.delay = duration;
        this.startTime = Minecraft.getMinecraft().theWorld.getTotalWorldTime();
    }

    public void activate()
    {
        AugmentEvents.addExtraWorldRender(this);
    }

    @Override
    public long maxAge()
    {
        return delay;
    }

    @Override
    public long startTime()
    {
        return startTime;
    }

    @Override
    public boolean shouldRemove()
    {
        return Minecraft.getMinecraft().theWorld.getTotalWorldTime()-startTime > maxAge();//TODO: Handle time going backwards
    }

    @Override
    public void render(RenderWorldLastEvent event)
    {
        GL11.glPushMatrix();
        //GlStateManager.pushAttrib();
        //GlStateManager.depthFunc(519);
        GlStateManager.disableDepth();
        RenderHelper.disableStandardItemLighting();
        //GlStateManager.disableFog();
        Minecraft.getMinecraft().getRenderManager().setRenderOutlines(true);
        for(Entity e : entities)
        {
            if(e instanceof EntityLivingBase)
            {
                GL11.glColor4d(1,0,0,0.5);
                Minecraft.getMinecraft().getRenderManager().renderEntityStatic(e,event.getPartialTicks(),true);
            }
        }
        Minecraft.getMinecraft().getRenderManager().setRenderOutlines(false);
        GlStateManager.enableDepth();
        //RenderHelper.enableStandardItemLighting();
        //GlStateManager.depthMask(false);
        //GlStateManager.depthFunc(515);
        //GlStateManager.enableFog();
        //GlStateManager.popAttrib();
        GL11.glPopMatrix();
    }
}
