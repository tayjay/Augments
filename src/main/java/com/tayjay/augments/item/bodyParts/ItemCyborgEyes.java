package com.tayjay.augments.item.bodyParts;

import com.tayjay.augments.Augments;
import com.tayjay.augments.api.events.IHUDProvider;
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

import java.util.List;

/**
 * Created by tayjay on 2016-06-24.
 */
public class ItemCyborgEyes extends ItemBodyPart implements IHUDProvider
{
    public ItemCyborgEyes(String name,int tier)
    {
        super(name,tier,"cyborgEyes","cyborgEyes", PartType.EYES);
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
        if(event.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS)
        {
            IItemHandler parts = CapHelper.getPlayerPartsCap(mc.thePlayer).getPartsInv();
            String rendering = "";
            for(int i = 0 ; i<parts.getSlots();i++)
            {
                if(parts.getStackInSlot(i)== null)
                    rendering = "null";
                else
                    rendering = parts.getStackInSlot(i).getDisplayName();
                mc.fontRendererObj.drawString(rendering,10,12*i,0);
            }
            drawEnergyCells(stack,parts, event);
            //drawHUDFromAugments(stack,event);
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

    ResourceLocation batteryBack = new ResourceLocation(Augments.modId.toLowerCase(),"textures/items/batteryIconBack.png");

    private void drawEnergyCells(ItemStack stack,IItemHandler parts, RenderGameOverlayEvent event)
    {
        if(event.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS)
        {
        if(parts.getStackInSlot(7) !=null)
        {
            GL11.glPushMatrix();
            ItemStack energyCell = parts.getStackInSlot(7);//EnergyCell slot. TODO: WATCH OUT FOR MAGIC NUMBERS!!!
            //Render Max Energy
            //GL11.glColor4d(1,0,0,0.7);
            float max = CapHelper.getPlayerDataCap(Minecraft.getMinecraft().thePlayer).getMaxEnergy();
            int cells = (int) Math.floor(max);
            int posX = (event.getResolution().getScaledWidth() /2) + (cells / 2) * 256;
            GlStateManager.scale(0.1, 0.1, 0.1);
            //GlStateManager.rotate(32,0,0,1); //TODO: Unturn this.
            for (int i = 0; i < cells; i++)
            {
                //Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(new ItemStack(Items.NETHER_STAR), Minecraft.getMinecraft().displayWidth / 2 + (i * 18), 20);
                //Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(new ItemStack(Items.DIAMOND), posX + (i * 14) + 1, 0);
                Minecraft.getMinecraft().renderEngine.bindTexture(batteryBack);

                drawTexturedModalRect(posX + (i * 258) + 16, 0, 0, 0, 256, 256);

            }
            GlStateManager.scale(10, 10, 10);
            //Render Current Energy
            //GL11.glColor4d(0,1,0,1);
            posX = (event.getResolution().getScaledWidth() /2) + (cells / 2) * 256;
            float cur = CapHelper.getPlayerDataCap(Minecraft.getMinecraft().thePlayer).getCurrentEnergy();
            cells = (int) Math.floor(cur);

            GlStateManager.scale(0.1, 0.1, 0.1);
            for (int i = 0; i < cells; i++)
            {
                //Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(new ItemStack(Items.NETHER_STAR), Minecraft.getMinecraft().displayWidth / 2 + (i * 18), 20);
                //Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(new ItemStack(Items.NETHER_STAR), posX + (i * 14), 0);
                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("augments","textures/items/batteryIconFront.png"));

                drawTexturedModalRect(posX + (i * 258) + 16, 0, 0, 0, 256, 256);

            }
            GlStateManager.scale(10, 10, 10);
            posX = (event.getResolution().getScaledWidth() /2) - (cells / 2) * 12;
            Minecraft.getMinecraft().fontRendererObj.drawString(String.format("%.2f", cur) + "/" + String.format("%.0f", max), posX, 20, 0);
            //Minecraft.getMinecraft().fontRendererObj.drawString("Why are there no cells being drawn?", 0, 0, 0);


            GL11.glPopMatrix();
        }
        }
    }

    int zLevel = 0;
    /**
     * Draws a textured rectangle at the current z-value.
     */
    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos((double)(x + 0), (double)(y + height), (double)this.zLevel).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + height) * f1)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(y + height), (double)this.zLevel).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + height) * f1)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(y + 0), (double)this.zLevel).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + 0) * f1)).endVertex();
        vertexbuffer.pos((double)(x + 0), (double)(y + 0), (double)this.zLevel).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + 0) * f1)).endVertex();
        tessellator.draw();
    }
}
