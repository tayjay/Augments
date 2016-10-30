package com.tayjay.augments.client.gui;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.tayjay.augments.Augments;
import com.tayjay.augments.api.item.IAugment;
import com.tayjay.augments.api.item.IBodyPart;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.inventory.*;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
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
    private EntityPlayer player;
    private static final ResourceLocation texture = new ResourceLocation(Augments.modId.toLowerCase(),"textures/gui/playerBodyParts.png");
    public GuiPlayerBody(InventoryPlayer invPlayer, InventoryPlayerParts invParts, InventoryPlayerAugments invAugs)
    {
        super(new ContainerPlayerBody(invPlayer,invParts,invAugs));
        //TODO: Change these to correct values of size
        this.xSize = 255;
        this.ySize = 230;
        player = Minecraft.getMinecraft().thePlayer;
    }



    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GL11.glColor4f(1f,1f,1f,1f);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        this.drawTexturedModalRect((width-xSize)/2,(height-ySize)/2,0,0,xSize,ySize);

        if(getSlotUnderMouse() != null && getSlotUnderMouse() instanceof SlotBodyPart && GuiScreen.isShiftKeyDown()&&getSlotUnderMouse().getHasStack())
        {
            //rotation=0;
            //drawBodyPart(guiLeft + 210, guiTop + 80, 60, (float)(guiLeft + 210) - mouseX, (float)(guiTop + 120 - 50) - mouseY, this.mc.thePlayer, (SlotBodyPart) getSlotUnderMouse(),((SlotBodyPart) getSlotUnderMouse()).getValidType());
            renderBodyPart(guiLeft + 210, guiTop + 80, 60,getSlotUnderMouse().getStack(),((SlotBodyPart) getSlotUnderMouse()).getValidType(),new RenderPlayer(Minecraft.getMinecraft().getRenderManager(),RenderUtil.hasSmallArms(Minecraft.getMinecraft().thePlayer)));
        }
        else
        {
            rotation=0;
            drawEntityOnScreen(guiLeft + 210, guiTop + 120, 40, (float) (guiLeft + 210) - mouseX, (float) (guiTop + 120 - 50) - mouseY, this.mc.thePlayer);
        }

        GlStateManager.pushMatrix();
        //double scale = 0.07;
        //GlStateManager.scale(scale,scale,scale);
        //GlStateManager.translate(100,100,0);

        GlStateManager.color(1,1,1,1);
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("augments", "textures/gui/slotEmpty.png"));
        for(int i =0;i<CapHelper.getPlayerBodyCap(player).getAugmentCapacity();i++)
        {
            drawModalRectWithCustomSizedTexture(guiLeft+99,i*18+17+guiTop,0,0,18,18,18,18);
        }
        GlStateManager.popMatrix();

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

        if(getSlotUnderMouse() != null && getSlotUnderMouse() instanceof SlotBodyPart && GuiScreen.isShiftKeyDown()&&((SlotBodyPart) getSlotUnderMouse()).getHasStack())
        {
            fontRendererObj.drawString(((SlotBodyPart) getSlotUnderMouse()).getValidType().toString(),190,35,Color.WHITE.getRGB());
        }

        fontRendererObj.drawString("Augments",99,8,Color.RED.getRGB(),false);





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
        //GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        //GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = 0;
        ent.rotationYaw = 0;
        ent.rotationPitch = 0;
        ent.rotationYawHead = 0;
        ent.prevRotationYawHead = 0;
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


        /*switch (type)
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

                    SlotBodyPart slotLeft = (SlotBodyPart) inventorySlots.getSlot(slot.slotNumber - 1);
                    if (slotLeft != null && slotLeft.getValidType() == type)
                    {
                        GlStateManager.translate(0, 0.7f, 0);
                        model.bipedRightLeg.isHidden = false;
                    } else
                    {
                        GlStateManager.translate(0, 0.7f, 0);
                        model.bipedLeftLeg.isHidden = false;
                    }

                break;
        }*/
        GlStateManager.rotate(rotation++,0,1,0);
        //render.doRender(ent,0,0,0,0,Minecraft.getMinecraft().getRenderPartialTicks());
        //rendermanager.renderEntityStatic(ent,Minecraft.getMinecraft().getRenderPartialTicks(), true);
        if(slot.getStack()!=null)
        {
            ((IBodyPart) slot.getStack().getItem()).renderOnPlayer(slot.getStack(), Minecraft.getMinecraft().thePlayer, new RenderPlayer(Minecraft.getMinecraft().getRenderManager()));

        }
        rendermanager.setRenderShadow(true);
        /*ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;*/
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

    //todo: Make this a static method somewhere else. It's used alot
    public void renderBodyPart(int posX,int posY,int scale,ItemStack stack, PartType type, RenderPlayer renderPlayer)
    {
        ModelRenderer model;
        ModelPlayer modelSteve = renderPlayer.getMainModel();
        boolean smallArms = RenderUtil.hasSmallArms(modelSteve);
        if(stack==null)
            return;
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 500.0F);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotate(rotation++,0,1,0);
        switch (type)
        {
            case HEAD:

                model = modelSteve.bipedHead;
                break;
            case EYES:

                model = modelSteve.bipedHead;
                break;
            case TORSO:

                model = modelSteve.bipedBody;
                break;
            //Without any major changes I found this method to determine which side the arm and leg stacks are on.
            //Since I removed the ARM_LEFT,ARM_RIGHT system
            case ARM:
                ItemStack leftArm = CapHelper.getPlayerBodyCap(Minecraft.getMinecraft().thePlayer).getStackByPartSided(PartType.ARM,0);
                if(leftArm!=null && leftArm.equals(stack))//If this stack is the one in the left arm slot
                {

                    model = modelSteve.bipedLeftArm;
                }
                else//This stack is in the right arm slot by elimination
                {

                    model = modelSteve.bipedRightArm;
                }
                break;
            case LEG:
                ItemStack leftLeg = CapHelper.getPlayerBodyCap(Minecraft.getMinecraft().thePlayer).getStackByPartSided(PartType.LEG,0);
                if(leftLeg !=null && leftLeg.equals(stack))
                {

                    model = modelSteve.bipedLeftLeg;
                }
                else
                {

                    model = modelSteve.bipedRightLeg;
                }
                break;
            default:

                model = modelSteve.bipedHeadwear;
                break;
        }

        /*if(CapHelper.getPlayerBodyCap(playerIn).getStackByPart(PartType.HEAD)!=null)
            renderPlayer.getMainModel().bipedRightArm = new ModelSkeleton().bipedRightArm;*/


        //GL11.glPushMatrix();


        //GlStateManager.scale(2,2,2);
        //GlStateManager.enableAlpha();
        GlStateManager.color(1,1,1,0.2f);
        Minecraft.getMinecraft().renderEngine.bindTexture(Minecraft.getMinecraft().thePlayer.getLocationSkin());
        alignModels(renderPlayer.getMainModel().bipedBody,model,false);
        model.render(0.0625f);
        //GlStateManager.disableAlpha();
        Minecraft.getMinecraft().renderEngine.bindTexture(((IBodyPart)stack.getItem()).getTexture(stack,false));
        model.render(0.0625f);
        //LayerAugments.renderEnchantedGlint(renderPlayer,playerIn, model);

        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    protected static void alignModels(ModelRenderer original, ModelRenderer moving, boolean isSneaking)
    {
        moving.rotateAngleX =   original.rotateAngleX;
        moving.rotateAngleY =   original.rotateAngleY;
        moving.rotateAngleZ =   original.rotateAngleZ;
        moving.offsetX =        original.offsetX;
        moving.offsetY =        isSneaking? original.offsetY+0.2f : original.offsetY;
        moving.offsetZ =        original.offsetZ;
        moving.rotationPointX = original.rotationPointX;
        moving.rotationPointY = original.rotationPointY;
        moving.rotationPointZ = original.rotationPointZ;
        moving.isHidden = original.isHidden;
        moving.mirror = original.mirror;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {

        super.drawScreen(mouseX, mouseY, partialTicks);

    }
}
