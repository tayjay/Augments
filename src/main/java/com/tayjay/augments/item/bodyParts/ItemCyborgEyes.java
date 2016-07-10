package com.tayjay.augments.item.bodyParts;

import com.tayjay.augments.Augments;
import com.tayjay.augments.api.events.IActivate;
import com.tayjay.augments.api.events.IHUDProvider;
import com.tayjay.augments.api.item.IAugment;
import com.tayjay.augments.api.item.IEnergySupply;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;

/**
 * Created by tayjay on 2016-06-24.
 */
public class ItemCyborgEyes extends ItemBodyPart implements IHUDProvider
{
    public ItemCyborgEyes(String name,int tier)
    {
        super(name,tier,1,3,"cyborgEyes","cyborgEyes", PartType.EYES);
        this.setMaxStackSize(1);
    }

    ModelPlayer model = new ModelPlayer(0.1f,false);

    @Override
    public void renderOnPlayer(ItemStack stack, EntityPlayer playerIn, RenderPlayer renderPlayer)
    {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(getTexture(stack,false));

        alignModels(renderPlayer.getMainModel().bipedHead,model.bipedHead,playerIn.isSneaking());

        model.bipedHead.render(0.0625f);
        GL11.glPopMatrix();
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack,boolean hasSmallArms)
    {
        return new ResourceLocation("augments", "textures/models/eyesPlayer.png");
    }

    @Override
    public void drawWorldElements(ItemStack stack, RenderWorldLastEvent event)
    {
        //drawWorldFromAugments(stack,event);


    }

    /*
    private void drawWorldFromAugments(ItemStack stack, RenderWorldLastEvent event)
    {
        IItemHandler augments = CapHelper.getAugHolderCap(stack).getAugments();
        for (int i = 0; i < augments.getSlots(); i++)
        {
            if (augments.getStackInSlot(i) != null && augments.getStackInSlot(i).getItem() instanceof IHUDProvider)
            {
                ((IHUDProvider) augments.getStackInSlot(i).getItem()).drawWorldElements(stack,event);
            }
        }
    }
    */

    @Override
    public void drawHudElements(ItemStack stack, RenderGameOverlayEvent event)
    {
        Minecraft mc = Minecraft.getMinecraft();
        if(event.getType() == RenderGameOverlayEvent.ElementType.DEBUG || mc.gameSettings.showDebugInfo)
            return;
        if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT)
        {
            IItemHandler parts = CapHelper.getPlayerPartsCap(mc.thePlayer).getPartsInv();
            String rendering = "";
            /*
            for(int i = 0 ; i<parts.getSlots();i++)
            {
                if(parts.getStackInSlot(i)== null)
                    rendering = "null";
                else
                    rendering = parts.getStackInSlot(i).getDisplayName();
                mc.fontRendererObj.drawString(rendering,10,12*i,0);
            }
            */
            mc.fontRendererObj.drawString("",0,0,1); //TODO: Find out why cells don't render right if there isn't a call to drawString before it
            drawEnergyCells(stack,parts, event);
            //drawHUDFromAugments(stack,event);

            for(int i = 0;i<parts.getSlots();i++)
            {
                if(parts.getStackInSlot(i)!=null)
                {
                    IItemHandler augments = CapHelper.getAugHolderCap(parts.getStackInSlot(i)).getAugments();
                    for(int j = 0;j<augments.getSlots();j++)
                    {
                        if(augments.getStackInSlot(j)!=null && augments.getStackInSlot(j).getItem() instanceof IActivate)
                        {

                            if(((IAugment)augments.getStackInSlot(j).getItem()).validate(augments.getStackInSlot(j),parts.getStackInSlot(i),Minecraft.getMinecraft().thePlayer))
                            {
                                mc.fontRendererObj.drawString(augments.getStackInSlot(j).getDisplayName(),16,0, Color.GREEN.getRGB());
                            }
                            else
                            {
                                mc.fontRendererObj.drawString(augments.getStackInSlot(j).getDisplayName(),16,0,Color.RED.getRGB());
                            }


                            GL11.glPushMatrix();
                            GL11.glScaled(0.6,0.6,0.6);
                            RenderHelper.enableGUIStandardItemLighting();
                            Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(augments.getStackInSlot(j), 0, 0);
                            GL11.glPopMatrix();
                        }
                    }
                }
            }
        }
    }

    /*
    private void drawHUDFromAugments(ItemStack stack, RenderGameOverlayEvent event)
    {
        IItemHandler augments = CapHelper.getAugHolderCap(stack).getAugments();
        for (int i = 0; i < augments.getSlots(); i++)
        {
            if (augments.getStackInSlot(i) != null && augments.getStackInSlot(i).getItem() instanceof IHUDProvider)
            {
                ((IHUDProvider) augments.getStackInSlot(i).getItem()).drawHudElements(stack,event);
            }
        }
    }
    */

    ResourceLocation batteryBack = new ResourceLocation(Augments.modId.toLowerCase(),"textures/items/batteryIconBackSmall.png");

    private void drawEnergyCells(ItemStack stack,IItemHandler parts, RenderGameOverlayEvent event)
    {
        //if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT)
        {
        //if(parts.getStackInSlot(7) !=null)
        {
            GL11.glPushMatrix();

            GL11.glPushMatrix();
            //GL11.glColor4d(1,1,1,1);
            GlStateManager.color(1,1,1,1);
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(Augments.modId.toLowerCase(),"textures/items/hudBack.png"));
            drawTexturedModalRect(0,0,0,0,64,64,4f);
            GL11.glPopMatrix();
            //Render Max Energy
            //GL11.glColor4d(1,0,0,0.7);
            float max = CapHelper.getPlayerDataCap(Minecraft.getMinecraft().thePlayer).getMaxEnergy();
            int cells = (int) Math.floor(max);
            int posX = (event.getResolution().getScaledWidth() /2) + (cells / 2) * 1;
            //GlStateManager.scale(0.1, 0.1, 0.1);
            //GlStateManager.enableRescaleNormal();
            for (int i = 0; i < cells; i++)
            {
                //Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(new ItemStack(Items.NETHER_STAR), Minecraft.getMinecraft().displayWidth / 2 + (i * 18), 20);
                //Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(new ItemStack(Items.DIAMOND), posX + (i * 14) + 1, 0);
                Minecraft.getMinecraft().renderEngine.bindTexture(batteryBack);

                drawTexturedModalRect(0 + (i * 17) + 1, 9, 0, 0, 16, 16,16f);

            }
           //GlStateManager.scale(10, 10, 10);
            //Render Current Energy
            //GL11.glColor4d(0,1,0,1);
            posX = (event.getResolution().getScaledWidth() /2) + (cells / 2) * 1;
            float cur = CapHelper.getPlayerDataCap(Minecraft.getMinecraft().thePlayer).getCurrentEnergy();
            int cells1 = (int) Math.floor(cur);

            //GlStateManager.scale(0.1, 0.1, 0.1);
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("augments","textures/items/batteryIconFrontSmall.png"));
            for (int i = 0; i < cells; i++)
            {
                //Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(new ItemStack(Items.NETHER_STAR), Minecraft.getMinecraft().displayWidth / 2 + (i * 18), 20);
                //Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(new ItemStack(Items.NETHER_STAR), posX + (i * 14), 0);


                if(i<=cells1)
                    drawTexturedModalRect(0 + (i * 17) + 1, 9, 0, 0, Math.min((int)(16f*(cur-i)),16), 16,16f);



            }
            //GlStateManager.scale(10, 10, 10);
            posX = (event.getResolution().getScaledWidth() /2) ;

            Minecraft.getMinecraft().fontRendererObj.drawString(String.format("%.2f", cur) + "/" + String.format("%.0f", max), posX, 20, 0);
            //Minecraft.getMinecraft().fontRendererObj.drawString("Why are there no cells being drawn?", 0, 0, 0);


            GL11.glPopMatrix();
        }
        }
    }

    int zLevel = 1;
    /**
     * Draws a textured rectangle at the current z-value.
     */
    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height,float scale)
    {
        float f = 0.00390625F*scale;
        float f1 = 0.00390625F*scale;

        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos((double)(x + 0), (double)(y + height), (double)this.zLevel).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + height) * f1)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(y + height), (double)this.zLevel).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + height) * f1)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(y + 0), (double)this.zLevel).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + 0) * f1)).endVertex();
        vertexbuffer.pos((double)(x + 0), (double)(y + 0), (double)this.zLevel).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + 0) * f1)).endVertex();
        tessellator.draw();
    }

    /**
     * Draws a rectangle with a vertical gradient between the specified colors (ARGB format). Args : x1, y1, x2, y2,
     * topColor, bottomColor
     */
    public void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor)
    {
        float f = (float)(startColor >> 24 & 255) / 255.0F;
        float f1 = (float)(startColor >> 16 & 255) / 255.0F;
        float f2 = (float)(startColor >> 8 & 255) / 255.0F;
        float f3 = (float)(startColor & 255) / 255.0F;
        float f4 = (float)(endColor >> 24 & 255) / 255.0F;
        float f5 = (float)(endColor >> 16 & 255) / 255.0F;
        float f6 = (float)(endColor >> 8 & 255) / 255.0F;
        float f7 = (float)(endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vertexbuffer.pos((double)right, (double)top, (double)this.zLevel).color(f1, f2, f3, f).endVertex();
        vertexbuffer.pos((double)left, (double)top, (double)this.zLevel).color(f1, f2, f3, f).endVertex();
        vertexbuffer.pos((double)left, (double)bottom, (double)this.zLevel).color(f5, f6, f7, f4).endVertex();
        vertexbuffer.pos((double)right, (double)bottom, (double)this.zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
}
