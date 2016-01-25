package com.tayjay.augments.client.gui;

import com.tayjay.augments.lib.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

/**
 * Created by tayjm_000 on 2016-01-17.
 */
public class GuiA extends GuiContainer
{
    private final ResourceLocation guiTexture;
    private final IInventory inventory;
    public GuiA(Container container, String guiTextureName, IInventory inventory)
    {
        super(container);
        this.guiTexture = new ResourceLocation(Reference.MOD_ID_LOWER + ":textures/gui/" + guiTextureName + ".png");
        this.inventory = inventory;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        mc.getTextureManager().bindTexture(guiTexture);
        this.drawTexturedModalRect(guiLeft,guiTop,0,0,xSize,ySize);
    }


}
