package com.tayjay.augments.client.gui;

import com.tayjay.augments.inventory.ContainerBodyPart;
import com.tayjay.augments.inventory.InventoryAugmentPlayer;
import com.tayjay.augments.inventory.InventoryBodyPart;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

/**
 * Created by tayjay on 2016-05-27.
 */
public class GuiInventoryBodyPart extends GuiContainer
{
    /** x and y size of the inventory window in pixels. Defined as float, passed as int
     * These are used for drawing the player model. */
    private float xSize_lo;
    private float ySize_lo;

    /** ResourceLocation takes 2 parameters: ModId, path to texture at the location:
     * "src/minecraft/assets/modid/"
     *
     * I have provided a sample texture file that works with this tutorial. Download it
     * from Forge_Tutorials/textures/gui/
     */
    private static final ResourceLocation iconLocation = new ResourceLocation("augments", "textures/gui/bodyPart.png");

    /** The inventory to render on screen */
    private final InventoryBodyPart inventory;

    public GuiInventoryBodyPart(Container containerBodyPart)
    {
        super(containerBodyPart);
        this.inventory = ((ContainerBodyPart)containerBodyPart).inventory;
    }

    public GuiInventoryBodyPart(EntityPlayer player, InventoryPlayer inventoryPlayer, InventoryBodyPart inventoryCustom)
    {
        this(new ContainerBodyPart(player,inventoryPlayer,inventoryCustom));
    }

    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
        this.xSize_lo = (float)p_73863_1_;
        this.ySize_lo = (float)p_73863_2_;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        String s = this.inventory.getInventoryName();
        this.fontRendererObj.drawString(s,this.xSize/2 - this.fontRendererObj.getStringWidth(s)/2,0,4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"),26, this.ySize-96+4,4210752);

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(iconLocation);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }
}
