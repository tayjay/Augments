package com.tayjay.augments.client.gui;

import com.tayjay.augments.Augments;
import com.tayjay.augments.inventory.ContainerPlayerParts;
import com.tayjay.augments.inventory.InventoryPlayerParts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by tayjay on 2016-06-24.
 */
public class GuiPlayerParts extends GuiContainer
{
    private static final ResourceLocation texture = new ResourceLocation(Augments.modId.toLowerCase(),"textures/gui/playerParts.png");
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
    }
}
