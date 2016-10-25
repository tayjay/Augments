package com.tayjay.augments.client.gui;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.tayjay.augments.Augments;
import com.tayjay.augments.api.item.IAugment;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.inventory.*;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by tayjay on 2016-10-16.
 */
public class GuiPlayerBody extends GuiContainer
{
    private static final ResourceLocation texture = new ResourceLocation(Augments.modId.toLowerCase(),"textures/gui/playerBodyParts.png");
    public GuiPlayerBody(InventoryPlayer invPlayer, InventoryPlayerParts invParts, InventoryPlayerAugments invAugs)
    {
        super(new ContainerPlayerBody(invPlayer,invParts,invAugs));
        //TODO: Change these to correct values of size
        this.xSize = 255;
        this.ySize = 230;
    }



    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GL11.glColor4f(1f,1f,1f,1f);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        this.drawTexturedModalRect((width-xSize)/2,(height-ySize)/2,0,0,xSize,ySize);

        if(getSlotUnderMouse() != null && getSlotUnderMouse() instanceof SlotBodyPart && GuiScreen.isShiftKeyDown())
        {
            //rotation=0;
            drawBodyPart(guiLeft + 210, guiTop + 190, 80, (float)(guiLeft + 210) - mouseX, (float)(guiTop + 120 - 50) - mouseY, this.mc.thePlayer, (SlotBodyPart) getSlotUnderMouse(),((SlotBodyPart) getSlotUnderMouse()).getValidType());
        }
        else
        {
            rotation=0;
            drawEntityOnScreen(guiLeft + 210, guiTop + 120, 40, (float) (guiLeft + 210) - mouseX, (float) (guiTop + 120 - 50) - mouseY, this.mc.thePlayer);
        }

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        int p = 0;
        int posX1 =5;
        int posY1 =28+p++ * 22;

        fontRendererObj.drawString("Head",posX1,posY1,0);posY1 =24+p++ * 20;
        fontRendererObj.drawString("Eyes",posX1,posY1,0);posY1 =24+p++ * 20;
        fontRendererObj.drawString("Torso",posX1,posY1,0);posY1 =24+p++ * 19;
        fontRendererObj.drawString("ArmLeft",posX1,posY1,0);posY1 =24+p++ * 19;
        fontRendererObj.drawString("ArmRight",posX1,posY1,0);posY1 =24+p++ * 19;
        fontRendererObj.drawString("LegLeft",posX1,posY1,0);posY1 =24+p++ * 19;
        fontRendererObj.drawString("LegRight",posX1,posY1,0);posY1 =24+p++ * 20;;posX1 = 90;

        fontRendererObj.drawString(Minecraft.getMinecraft().thePlayer.getDisplayNameString(),190,15,0);

        if(getSlotUnderMouse() != null && getSlotUnderMouse() instanceof SlotBodyPart && GuiScreen.isShiftKeyDown())
        {
            fontRendererObj.drawString(((SlotBodyPart) getSlotUnderMouse()).getValidType().toString(),190,35,Color.WHITE.getRGB());
        }





    }

    @Override
    protected void drawHoveringText(List<String> textLines, int x, int y, FontRenderer font)
    {
        if(getSlotUnderMouse() != null && getSlotUnderMouse() instanceof SlotBodyPart && GuiScreen.isShiftKeyDown())
        {
            //fontRendererObj.drawString(((SlotBodyPart) getSlotUnderMouse()).getValidType().toString(),190,35,Color.WHITE.getRGB());
        }
        else
        {
            super.drawHoveringText(textLines, x, y, font);
        }
    }

    /**
     * Draws an entity on the screen looking toward the cursor.
     * From GuiInventory draw player
     */
    public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent)
    {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 50.0F);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
        ent.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.doRenderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    int rotation = 0;
    public void drawBodyPart(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent, SlotBodyPart slot,PartType type)
    {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 50.0F);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        /*GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
        ent.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;*/
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        RenderLivingBase render = (RenderLivingBase) rendermanager.getEntityRenderObject(ent);
        ModelPlayer model = (ModelPlayer) render.getMainModel();
        //model.setInvisible(true);
        model.bipedHead.isHidden=true;
        model.bipedBody.isHidden=true;
        model.bipedLeftArm.isHidden=true;
        model.bipedRightArm.isHidden=true;
        model.bipedLeftLeg.isHidden=true;
        model.bipedRightLeg.isHidden=true;


        switch (type)
        {
            case HEAD:
                GlStateManager.translate(0, -0.3f, 0);
                model.bipedHead.isHidden = false;
                break;
            case EYES:
                //GL11.glRotatef(20,1,1,1);
                GlStateManager.translate(0, -0.3f, 0);
                model.bipedHead.isHidden = false;
                break;
            case TORSO:
                model.bipedBody.isHidden = false;
                break;
            case ARM:
                SlotBodyPart slotArm = (SlotBodyPart) inventorySlots.getSlot(slot.slotNumber+1);
                if(slotArm!=null && slotArm.getValidType()==type)
                {
                    GlStateManager.translate(-0.2f, 0, 0);
                    model.bipedLeftArm.isHidden = false;
                }
                else
                {
                    GlStateManager.translate(0.6f, 0, 0);
                    model.bipedRightArm.isHidden = false;
                }
                break;
            case LEG:
                try
                {
                    SlotBodyPart slotLeg = (SlotBodyPart) inventorySlots.getSlot(slot.slotNumber + 1);
                    if (slotLeg != null && slot.getValidType() == type)
                    {
                        GlStateManager.translate(0, 0.7f, 0);
                        model.bipedLeftLeg.isHidden = false;
                    } else
                    {
                        GlStateManager.translate(0, 0.7f, 0);
                        model.bipedRightLeg.isHidden = false;
                    }
                }catch (Exception e)//TODO: FIX, VERY LAZY
                {
                    GlStateManager.translate(0, 0.7f, 0);
                    model.bipedRightLeg.isHidden = false;
                }
                break;
        }
        GlStateManager.rotate(rotation++,0,1,0);
        //render.doRender(ent,0,0,0,0,Minecraft.getMinecraft().getRenderPartialTicks());
        rendermanager.renderEntityStatic(ent,Minecraft.getMinecraft().getRenderPartialTicks(), true);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        model.bipedHead.isHidden=false;
        model.bipedBody.isHidden=false;
        model.bipedLeftArm.isHidden=false;
        model.bipedRightArm.isHidden=false;
        model.bipedLeftLeg.isHidden=false;
        model.bipedRightLeg.isHidden=false;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
}
