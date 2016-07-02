package com.tayjay.augments.client.gui;

import com.tayjay.augments.Augments;
import com.tayjay.augments.inventory.ContainerPlayerParts;
import com.tayjay.augments.inventory.InventoryPlayerParts;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.opengl.GL11;

/**
 * Created by tayjay on 2016-06-24.
 */
public class GuiPlayerParts extends GuiContainer
{
    private static final ResourceLocation texture = new ResourceLocation(Augments.modId.toLowerCase(),"textures/gui/playerBodyParts.png");
    public GuiPlayerParts(InventoryPlayer invPlayer, InventoryPlayerParts invParts)
    {
        super(new ContainerPlayerParts(invPlayer,invParts));
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

        drawEntityOnScreen(guiLeft + 210, guiTop + 120, 40, (float)(guiLeft + 210) - mouseX, (float)(guiTop + 120 - 50) - mouseY, this.mc.thePlayer);
        ContainerPlayerParts container = (ContainerPlayerParts)inventorySlots;
        RenderHelper.enableGUIStandardItemLighting();
        for(int i = 0;i<container.inventory.getSlots();i++)
        {
            if(container.inventory.getStackInSlot(i)!=null)
            {
                IItemHandler items = CapHelper.getAugHolderCap(container.inventory.getStackInSlot(i)).getAugments();
                for (int j = 0; j < items.getSlots(); j++)
                {
                    if (items.getStackInSlot(j) != null)
                    {
                        int posX =guiLeft + 69 + j * 18;
                        int posY =guiTop + 5 + i * 18;

                        itemRender.renderItemIntoGUI(items.getStackInSlot(j), posX, posY);
                    }
                }
            }
        }
        RenderHelper.disableStandardItemLighting();

        for(int i = 0;i<container.inventory.getSlots();i++)
        {
            if(container.inventory.getStackInSlot(i)!=null)
            {
                IItemHandler items = CapHelper.getAugHolderCap(container.inventory.getStackInSlot(i)).getAugments();
                for (int j = 0; j < items.getSlots(); j++)
                {
                    if (items.getStackInSlot(j) != null)
                    {
                        int posX =guiLeft + 69 + j * 18;
                        int posY =guiTop + 5 + i * 18;
                        if(mouseX > posX && mouseX <=posX+16 && mouseY > posY && mouseY <=posY+16)
                        {
                            drawHoveringText(items.getStackInSlot(j).getTooltip(Minecraft.getMinecraft().thePlayer,true),mouseX,mouseY);
                        }
                    }
                }
            }
        }

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        int p = 0;
        int posX1 =5;
        int posY1 =10+p++ * 22;
        fontRendererObj.drawString("Head",posX1,posY1,0);posY1 =p++ * 22+5;
        fontRendererObj.drawString("Eyes",posX1,posY1,0);posY1 =p++ * 22+2;
        fontRendererObj.drawString("Torso",posX1,posY1,0);posY1 =p++ * 22-3;
        fontRendererObj.drawString("ArmLeft",posX1,posY1,0);posY1 =p++ * 22-6;
        fontRendererObj.drawString("ArmRight",posX1,posY1,0);posY1 =p++ * 22-10;
        fontRendererObj.drawString("LegLeft",posX1,posY1,0);posY1 =p++ * 22-14;
        fontRendererObj.drawString("LegRight",posX1,posY1,0);posY1 =p++ * 22-18;
        fontRendererObj.drawString("Power",posX1,posY1,0);
        fontRendererObj.drawString(Minecraft.getMinecraft().thePlayer.getDisplayNameString(),190,15,0);



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
}
