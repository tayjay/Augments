package com.tayjay.augments.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * Created by tayjm_000 on 2016-01-17.
 */
public class RenderUtil
{

    private static Minecraft mc = Minecraft.getMinecraft();
    private static RenderItem itemRender = new RenderItem();
    private static FontRenderer fontRendererObj = mc.fontRenderer;
    private static RenderManager renderManager = RenderManager.instance;
    private static float zLevel = itemRender.zLevel;

    public static void drawItemStack(ItemStack itemStack, int x, int y, float scale, String p_146982_4_)
    {
        //todo: Dynamic Scaling, positioning, custom layer
        GL11.glPushMatrix();
        float defaultzLevel = itemRender.zLevel;

        GL11.glScalef(scale, scale, scale);
        zLevel = 10.0F;
        itemRender.zLevel = 10.0F;
        FontRenderer font = null;
        if (itemStack != null) font = itemStack.getItem().getFontRenderer(itemStack);
        if (font == null) font = fontRendererObj;
        itemRender.renderItemIntoGUI(font, mc.getTextureManager(), itemStack, x / (int) (scale), y / (int) (scale));
        zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
        itemRender.zLevel = defaultzLevel;
        GL11.glPopMatrix();
    }
    /**
     * Default code from Minecraft's GuiInventory to render the player on the screen
     * @param x
     * @param y
     * @param scale
     * @param yaw
     * @param pitch
     * @param entity
     */
    public static void drawEntityLiving(int x, int y, int scale, float yaw, float pitch, EntityLivingBase entity)
    {
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, 50.0F);
        GL11.glScalef((float)(-scale), (float)scale, (float)scale);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float f2 = entity.renderYawOffset;
        float f3 = entity.rotationYaw;
        float f4 = entity.rotationPitch;
        float f5 = entity.prevRotationYawHead;
        float f6 = entity.rotationYawHead;
        GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
        net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-((float)Math.atan((double)(pitch / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        entity.renderYawOffset = (float)Math.atan((double)(yaw / 40.0F)) * 20.0F;
        entity.rotationYaw = (float)Math.atan((double)(yaw / 40.0F)) * 40.0F;
        entity.rotationPitch = -((float)Math.atan((double)(pitch / 40.0F))) * 20.0F;
        entity.rotationYawHead = entity.rotationYaw;
        entity.prevRotationYawHead = entity.rotationYaw;
        GL11.glTranslatef(0.0F, entity.yOffset, 0.0F);
        RenderManager.instance.playerViewY = 180.0F;
        RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        entity.renderYawOffset = f2;
        entity.rotationYaw = f3;
        entity.rotationPitch = f4;
        entity.prevRotationYawHead = f5;
        entity.rotationYawHead = f6;
        GL11.glPopMatrix();
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    public static void renderAABBWithColour(AxisAlignedBB box, float r, float g, float b, float a, boolean fill, boolean throughWalls)
    {

        if(box==null)
            return;
        GL11.glPushMatrix();
        /*
        if(throughWalls)
        {
            GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        }
        */
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(3.0f);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);





        if (fill) {
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
            renderAABBWithColourPrivate(box,r,g,b,a/2,fill,throughWalls);
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_LINE);
            GL11.glPolygonOffset(-1.f,-1.f);
        }
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        renderAABBWithColourPrivate(box, r, g, b, a, fill, throughWalls);

        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);


        GL11.glPopMatrix();
    }

    private static void renderAABBWithColourPrivate(AxisAlignedBB box, float r,float g,float b, float a, boolean fill, boolean throughWalls)
    {
        Tessellator tessellator = Tessellator.instance;
        float colorR = r;
        float colorG = g;
        float colorB = b;
        float colorA = a;

        if(throughWalls)
        {
            GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        }

        tessellator.startDrawing(GL11.GL_QUADS);
        tessellator.setColorRGBA_F(colorR, colorG, colorB, colorA);
        tessellator.addVertex(box.minX, box.minY, box.minZ);
        tessellator.addVertex(box.maxX, box.minY, box.minZ);
        tessellator.addVertex(box.maxX, box.minY, box.maxZ);
        tessellator.addVertex(box.minX, box.minY, box.maxZ);
        tessellator.draw();

        tessellator.startDrawing(GL11.GL_QUADS);
        tessellator.setColorRGBA_F(colorR, colorG, colorB, colorA);
        tessellator.addVertex(box.minX, box.maxY, box.minZ);
        tessellator.addVertex(box.maxX, box.maxY, box.minZ);
        tessellator.addVertex(box.maxX, box.maxY, box.maxZ);
        tessellator.addVertex(box.minX, box.maxY, box.maxZ);
        tessellator.draw();

        tessellator.startDrawing(GL11.GL_QUADS);
        tessellator.setColorRGBA_F(colorR, colorG, colorB, colorA);
        tessellator.addVertex(box.minX, box.minY, box.maxZ);
        tessellator.addVertex(box.minX, box.maxY, box.maxZ);
        tessellator.addVertex(box.maxX, box.maxY, box.maxZ);
        tessellator.addVertex(box.maxX, box.minY, box.maxZ);
        tessellator.draw();

        tessellator.startDrawing(GL11.GL_QUADS);
        tessellator.setColorRGBA_F(colorR, colorG, colorB, colorA);
        tessellator.addVertex(box.minX, box.minY, box.minZ);
        tessellator.addVertex(box.minX, box.maxY, box.minZ);
        tessellator.addVertex(box.maxX, box.maxY, box.minZ);
        tessellator.addVertex(box.maxX, box.minY, box.minZ);
        tessellator.draw();

        tessellator.startDrawing(GL11.GL_QUADS);
        tessellator.setColorRGBA_F(colorR, colorG, colorB, colorA);
        tessellator.addVertex(box.minX, box.minY, box.minZ);
        tessellator.addVertex(box.minX,box.minY, box.maxZ);
        tessellator.addVertex(box.minX,box.maxY, box.maxZ);
        tessellator.addVertex(box.minX,box.maxY, box.minZ);
        tessellator.draw();

        tessellator.startDrawing(GL11.GL_QUADS);
        tessellator.setColorRGBA_F(colorR, colorG, colorB, colorA);
        tessellator.addVertex(box.maxX, box.minY, box.minZ);
        tessellator.addVertex(box.maxX,box.minY, box.maxZ);
        tessellator.addVertex(box.maxX,box.maxY, box.maxZ);
        tessellator.addVertex(box.maxX,box.maxY, box.minZ);
        tessellator.draw();
    }

    public static void renderOutline(AxisAlignedBB bb,boolean fill,boolean throughWalls) {

        if(bb==null)
            return;
        GL11.glPushMatrix();

        //System.out.println(bb);


        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(3.0f);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);

        if(throughWalls)
            GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        //GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);

        if (fill) { // TODO: depth-sort vertices for real transparency
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
            renderOutlineDraw(bb);
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_LINE);
            GL11.glPolygonOffset(-1.f,-1.f);
        }
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        renderOutlineDraw(bb);




        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);

        GL11.glPopMatrix();
    }

    private static void renderOutlineDraw(AxisAlignedBB bb)
    {

        Tessellator tessellator = Tessellator.instance;
        float colorR = 1.0F;
        float colorG = 0.0F;
        float colorB = 0.0F;
        float colorA = 0.5F;
        tessellator.startDrawing(GL11.GL_QUADS);
        tessellator.setColorRGBA_F(colorR, colorG, colorB, colorA);
        tessellator.addVertex(bb.minX, bb.minY, bb.minZ);
        tessellator.addVertex(bb.maxX, bb.minY, bb.minZ);
        tessellator.addVertex(bb.maxX, bb.minY, bb.maxZ);
        tessellator.addVertex(bb.minX, bb.minY, bb.maxZ);
        tessellator.draw();

        tessellator.startDrawing(GL11.GL_QUADS);
        tessellator.setColorRGBA_F(colorR, colorG, colorB, colorA);
        tessellator.addVertex(bb.minX, bb.maxY, bb.minZ);
        tessellator.addVertex(bb.maxX, bb.maxY, bb.minZ);
        tessellator.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        tessellator.addVertex(bb.minX, bb.maxY, bb.maxZ);
        tessellator.draw();

        tessellator.startDrawing(GL11.GL_QUADS);
        tessellator.setColorRGBA_F(colorR, colorG, colorB, colorA);
        tessellator.addVertex(bb.minX, bb.minY, bb.maxZ);
        tessellator.addVertex(bb.minX, bb.maxY, bb.maxZ);
        tessellator.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        tessellator.addVertex(bb.maxX, bb.minY, bb.maxZ);
        tessellator.draw();

        tessellator.startDrawing(GL11.GL_QUADS);
        tessellator.setColorRGBA_F(colorR, colorG, colorB, colorA);
        tessellator.addVertex(bb.minX, bb.minY, bb.minZ);
        tessellator.addVertex(bb.minX, bb.maxY, bb.minZ);
        tessellator.addVertex(bb.maxX, bb.maxY, bb.minZ);
        tessellator.addVertex(bb.maxX, bb.minY, bb.minZ);
        tessellator.draw();

        tessellator.startDrawing(GL11.GL_QUADS);
        tessellator.setColorRGBA_F(colorR, colorG, colorB, colorA);
        tessellator.addVertex(bb.minX, bb.minY, bb.minZ);
        tessellator.addVertex(bb.minX,bb.minY, bb.maxZ);
        tessellator.addVertex(bb.minX,bb.maxY, bb.maxZ);
        tessellator.addVertex(bb.minX,bb.maxY, bb.minZ);
        tessellator.draw();

        tessellator.startDrawing(GL11.GL_QUADS);
        tessellator.setColorRGBA_F(colorR, colorG, colorB, colorA);
        tessellator.addVertex(bb.maxX, bb.minY, bb.minZ);
        tessellator.addVertex(bb.maxX,bb.minY, bb.maxZ);
        tessellator.addVertex(bb.maxX,bb.maxY, bb.maxZ);
        tessellator.addVertex(bb.maxX,bb.maxY, bb.minZ);
        tessellator.draw();
    }
}
