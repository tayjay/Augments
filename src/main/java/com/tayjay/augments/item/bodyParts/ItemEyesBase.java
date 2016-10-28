package com.tayjay.augments.item.bodyParts;

import com.tayjay.augments.Augments;
import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.events.IHUDProvider;
import com.tayjay.augments.api.item.IAugment;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * Created by tayjay on 2016-07-16.
 */
public class ItemEyesBase extends ItemBodyPart implements IHUDProvider
{
    public ItemEyesBase(String name, int tier, int storageSize, String texture)
    {
        super(name, tier, 0, storageSize, texture,texture, PartType.EYES);
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack,boolean hasSmallArms)
    {
        return new ResourceLocation("augments", "textures/models/"+name+".png");
    }

    ModelPlayer model = new ModelPlayer(0.1f,false);
    //ModelRenderOBJ obj;

    @Override
    public void renderOnPlayer(ItemStack stack, EntityPlayer playerIn, RenderPlayer renderPlayer)
    {
        //obj = new ModelRenderOBJ(renderPlayer.getMainModel(),new ResourceLocation(Augments.modId+":models/block/cube.obj"),new ResourceLocation(Augments.modId+":textures/block/modeltest.png"));
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(getTexture(stack,false));

        alignModels(renderPlayer.getMainModel().bipedHead,model.bipedHead,playerIn.isSneaking());

        //obj.render(1f);
        model.bipedHead.render(0.0625f);
        GL11.glPopMatrix();
    }

    @Override
    public void drawWorldElements(ItemStack stack, RenderWorldLastEvent event)
    {
        //drawWorldFromAugments(stack,event);


    }

    @Override
    public void drawHudElements(ItemStack stack, RenderGameOverlayEvent event)
    {
        Minecraft mc = Minecraft.getMinecraft();
        if(event.getType() == RenderGameOverlayEvent.ElementType.DEBUG || mc.gameSettings.showDebugInfo)
            return;
        if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT)
        {
            IItemHandler parts = CapHelper.getPlayerBodyCap(mc.thePlayer).getBodyParts();

            drawEnergyCells(stack,parts, event);

            drawActiveAugment();

            /*
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
            */
            if(Augments.drugDependant)
            {
                int drugTimer = CapHelper.getPlayerDataCap(Minecraft.getMinecraft().thePlayer).getDrugTimer();
                mc.fontRendererObj.drawString("Drug Timer: " + drugTimer, 10, 100, Color.GREEN.getRGB());
            }
        }
    }


    ResourceLocation batteryBack = new ResourceLocation(Augments.modId.toLowerCase(),"textures/items/batteryIconBackSmall.png");

    private void drawEnergyCells(ItemStack stack,IItemHandler parts, RenderGameOverlayEvent event)
    {

        GL11.glPushMatrix();

        GL11.glPushMatrix();

        GlStateManager.color(1,1,1,1);
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(Augments.modId.toLowerCase(),"textures/items/hudBack.png"));
        Gui.drawModalRectWithCustomSizedTexture(0,0,0,0,64,64,64,64);
        GL11.glPopMatrix();
        //Render Max Energy
        float max = CapHelper.getPlayerDataCap(Minecraft.getMinecraft().thePlayer).getMaxEnergy();
        int cells = (int) Math.floor(max);
        int posX = (event.getResolution().getScaledWidth() /2) + (cells / 2) * 1;

        for (int i = 0; i < cells; i++)
        {
            Minecraft.getMinecraft().renderEngine.bindTexture(batteryBack);
            Gui.drawModalRectWithCustomSizedTexture((i * 17) + 1, 9, 0, 0, 16, 16,16,16);
        }

        //Render Current Energy
        posX = (event.getResolution().getScaledWidth() /2) + (cells / 2) * 1;
        float cur = CapHelper.getPlayerDataCap(Minecraft.getMinecraft().thePlayer).getCurrentEnergy();
        int cells1 = (int) Math.floor(cur);
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("augments","textures/items/batteryIconFrontSmall.png"));
        for (int i = 0; i < cells; i++)
        {
            if(i<=cells1)
                Gui.drawModalRectWithCustomSizedTexture((i * 17) + 1, 9, 0, 0, Math.min((int)(16f*(cur-i)),16), 16,16f,16f);
        }
        posX = (event.getResolution().getScaledWidth() /2) ;
        Minecraft.getMinecraft().fontRendererObj.drawString(String.format("%.2f", cur) + "/" + String.format("%.0f", max), posX, 20, 0);
        GL11.glPopMatrix();

    }

    private void drawActiveAugment()
    {
        Minecraft mc = Minecraft.getMinecraft();
        IPlayerDataProvider playerDataProvider = CapHelper.getPlayerDataCap(Minecraft.getMinecraft().thePlayer);
        ItemStack activeAugment = playerDataProvider.getCurrentAugment();

        if(activeAugment!=null)
        {

            if(!((IAugment)activeAugment.getItem()).isActive(activeAugment,mc.thePlayer))
            {
                mc.fontRendererObj.drawString(activeAugment.getDisplayName(),16,0, Color.BLUE.getRGB());
            }
            else if(!((IAugment)activeAugment.getItem()).validate(activeAugment, mc.thePlayer) && ((IAugment)activeAugment.getItem()).isActive(activeAugment, mc.thePlayer))
            {
                mc.fontRendererObj.drawString(activeAugment.getDisplayName(),16,0, Color.YELLOW.getRGB());
            }
            else if(((IAugment)activeAugment.getItem()).validate(activeAugment, mc.thePlayer))
            {
                mc.fontRendererObj.drawString(activeAugment.getDisplayName(),16,0, Color.GREEN.getRGB());
            }
            else
            {
                mc.fontRendererObj.drawString(activeAugment.getDisplayName(),16,0,Color.RED.getRGB());
            }


            GL11.glPushMatrix();
            GL11.glScaled(0.6,0.6,0.6);
            RenderHelper.enableGUIStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(activeAugment, 0, 0);
            GL11.glPopMatrix();
        }
    }

}
