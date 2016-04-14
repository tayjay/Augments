package com.tayjay.augments.client.gui;

import com.tayjay.augments.inventory.ContainerAugmentPlayer;
import com.tayjay.augments.inventory.InventoryAugmentPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.Collection;

/**
 * Created by tayjm_000 on 2016-01-24.
 */
public class GuiInventoryAugmentPlayer extends GuiContainer
{
    /** x size of the inventory window in pixels. Defined as float, passed as int */
    private float xSize_lo;

    /** y size of the inventory window in pixels. Defined as float, passed as int. */
    private float ySize_lo;

    /** Normally I use '(ModInfo.MOD_ID, "textures/...")', but it can be done this way as well */
    private static final ResourceLocation iconLocation = new ResourceLocation("augments:textures/gui/playerAugmentInv2.png");//playerAugmentInventory.png

    /** Could use IInventory type to be more generic, but this way will save an import... */
    private final InventoryAugmentPlayer inventory;
    private final EntityPlayer player;

    public GuiInventoryAugmentPlayer(EntityPlayer player, InventoryPlayer inventoryPlayer, InventoryAugmentPlayer inventoryCustom)
    {
        super(new ContainerAugmentPlayer(player, inventoryPlayer, inventoryCustom));
        this.inventory = inventoryCustom;
        this.player = player;

        this.width = 512;
        this.height = 512;
        // if you need the player for something later on, store it in a local variable here as well
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float f)
    {
        super.drawScreen(mouseX, mouseY, f);

        xSize_lo = mouseX;
        ySize_lo = mouseY;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        fontRendererObj.drawString(player.getCommandSenderName(),85,5,4210752);
        fontRendererObj.drawString("Health: "+player.getHealth()+"/"+player.getMaxHealth(),85,20,4210752);
        fontRendererObj.drawString(player.getEntityAttribute(SharedMonsterAttributes.attackDamage).getBaseValue() + " Base attack", 85, 35, 4210752);
        Collection<AttributeModifier> itemModifiers = null;
        if(player.getCurrentEquippedItem()!=null)
            itemModifiers = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem().getAttributeModifiers(player.getCurrentEquippedItem()).get(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
        if(itemModifiers!=null&&itemModifiers.iterator().hasNext())
            fontRendererObj.drawString(itemModifiers.iterator().next().getAmount()+" Weapon Damage",85,50,4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(iconLocation);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        drawPlayerModel(guiLeft + 51, guiTop + 75, 30, guiLeft + 51 - xSize_lo, guiTop + 25 - ySize_lo, mc.thePlayer);

    }

    /**
     * Copied straight out of vanilla - renders the player model on screen
     */
    public static void drawPlayerModel(int x, int y, int scale, float yaw, float pitch, EntityLivingBase entity) {
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 50.0F);
        GL11.glScalef(-scale, scale, scale);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float f2 = entity.renderYawOffset;
        float f3 = entity.rotationYaw;
        float f4 = entity.rotationPitch;
        float f5 = entity.prevRotationYawHead;
        float f6 = entity.rotationYawHead;
        GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-((float) Math.atan(pitch / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
        entity.renderYawOffset = (float) Math.atan(yaw / 40.0F) * 20.0F;
        entity.rotationYaw = (float) Math.atan(yaw / 40.0F) * 40.0F;
        entity.rotationPitch = -((float) Math.atan(pitch / 40.0F)) * 20.0F;
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
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
}
