package com.tayjay.augments.client.gui;

import com.tayjay.augments.Augments;
import com.tayjay.augments.inventory.ContainerAugments;
import com.tayjay.augments.inventory.ContainerPlayerParts;
import com.tayjay.augments.inventory.InventoryAugments;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.opengl.GL11;

/**
 * Created by tayjay on 2016-06-25.
 */
public class GuiBodyPartAugments extends GuiContainer
{
    private static final ResourceLocation texture = new ResourceLocation(Augments.modId.toLowerCase(),"textures/gui/bodyPartAugments.png");

    public GuiBodyPartAugments(InventoryPlayer inventoryPlayer, InventoryAugments inventoryAugments)
    {
        super(new ContainerAugments(inventoryPlayer,inventoryAugments));
        //TODO: May want to resize the gui
        this.xSize = 196;
        this.ySize = 177;
    }
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GL11.glColor4f(1f,1f,1f,1f);

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        this.drawTexturedModalRect((width-xSize)/2,(height-ySize)/2,0,0,xSize,ySize);


        ContainerAugments container = (ContainerAugments)inventorySlots;
        int posX = guiLeft+16;
        int posY = guiTop+17;
        itemRender.renderItemIntoGUI(container.inventoryAugments.AUGMENT_HOLDER, posX,posY);
        if(mouseX > posX && mouseX <=posX+16 && mouseY > posY && mouseY <=posY+16)
        {
            drawHoveringText(container.inventoryAugments.AUGMENT_HOLDER.getTooltip(Minecraft.getMinecraft().thePlayer,true),mouseX,mouseY);
        }

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        fontRendererObj.drawString("Augments",5,5,0);
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);


    }


}
