package com.tayjay.augments.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * Created by tayjay on 2016-11-22.
 * Subscribe events that will happen on client only, rendering and the like.
 */
public class ClientEvents
{
    ArrayList<BlockPos> blockPosToRender = new ArrayList<BlockPos>();
    long lastPing = 0;
    EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
    //How many ticks to render for. todo: Make unique timing to each blockPos
    final long RENDER_TIME = 200L;

    @SubscribeEvent
    public void renderBlockPos(RenderWorldLastEvent event)
    {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (!blockPosToRender.isEmpty()&&Minecraft.getMinecraft().theWorld!=null&&player!=null)
        {
            GlStateManager.pushAttrib();
            GlStateManager.pushMatrix();

            //See through walls
            GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glLineWidth(3.0f);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_CULL_FACE);

            for (BlockPos block : blockPosToRender)
            {
                if(block!=null)
                {
                    double x = block.getX() - (player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks());
                    double y = block.getY() - (player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks());
                    double z = block.getZ() - (player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks());

                    RenderGlobal.drawBoundingBox(x, y, z, x + 1, y + 1, z + 1, 1, 1, 1, 1);

                    RenderGlobal.renderFilledBox(x, y, z, x + 1, y + 1, z + 1, 0, 0, 1, 0.2f);
                }

            }
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_BLEND);

            GlStateManager.popMatrix();
            GlStateManager.popAttrib();
        }
    }

    @SubscribeEvent
    public void clientTickEvent(TickEvent.ClientTickEvent event)
    {
        //Hander reset of blockPos after certain time
        if(Minecraft.getMinecraft().theWorld!=null)
        {
            if (Math.abs(Minecraft.getMinecraft().theWorld.getTotalWorldTime() - lastPing) >= RENDER_TIME && !blockPosToRender.isEmpty())//Stop rendering after 10 seconds
            {
                blockPosToRender.clear();
            }
        }
    }

    public void addBlockToRender(BlockPos blockPos)
    {
        if(Minecraft.getMinecraft().theWorld!=null)
        {
            blockPosToRender.add(blockPos);
            lastPing = Minecraft.getMinecraft().theWorld.getTotalWorldTime();
        }

    }

    public void clearBlockRender()
    {
        blockPosToRender.clear();
    }
}
