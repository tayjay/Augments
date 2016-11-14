package com.tayjay.augments.item.bodyParts;

import com.tayjay.augments.Augments;
import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.events.IHUDProvider;
import com.tayjay.augments.api.item.IAugment;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.client.model.ModelRenderOBJ;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.ItemNBTHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by tayjay on 2016-07-16.
 */
public class ItemEyesBase extends ItemBodyPart implements IHUDProvider
{
    public ItemEyesBase(String name, int tier, String texture,boolean attachedToBody)
    {
        super(name, tier, 0, texture,texture, PartType.EYES);
        this.isAttached = attachedToBody;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        super.addInformation(stack, playerIn, tooltip, advanced);
        if(ItemNBTHelper.getCompound(stack,"offsetX",true)==null)
            ItemNBTHelper.setFloat(stack,"offsetX",0);
        if(ItemNBTHelper.getCompound(stack,"offsetY",true)==null)
        {
            ItemNBTHelper.setFloat(stack, "offsetY", 0);

        }
        tooltip.add("OffsetY: "+ItemNBTHelper.getFloat(stack,"offsetY",0));

    }

    @Override
    public ResourceLocation getTexture(ItemStack stack,boolean hasSmallArms)
    {
        return new ResourceLocation("augments", "textures/models/"+name+".png");
    }

    ModelPlayer model = new ModelPlayer(0.1f,false);
    boolean isAttached;
    //ModelRenderOBJ obj;

    @Override
    public void renderOnPlayer(ItemStack stack, EntityPlayer playerIn, RenderPlayer renderPlayer)
    {
        ModelRenderer modelHead = new ModelRenderer(renderPlayer.getMainModel(), 0, (int) ItemNBTHelper.getFloat(stack,"offsetY",0));
        modelHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.25f);
        GlStateManager.pushMatrix();
        //obj = new ModelRenderOBJ(renderPlayer.getMainModel(),new ResourceLocation(Augments.modId+":models/block/cube.obj"),new ResourceLocation(Augments.modId+":textures/block/modeltest.png"));
        //obj.render(1);
        Minecraft.getMinecraft().renderEngine.bindTexture(getTexture(stack,false));

        //renderPlayer.getMainModel().bipedHead.addChild(model.bipedHead);

        //alignModels(renderPlayer.getMainModel().bipedHead,model.bipedHead,playerIn.isSneaking());
        //alignModels(renderPlayer.getMainModel().bipedHeadwear,model.bipedHeadwear,playerIn.isSneaking());

        //ItemNBTHelper.setFloat(stack,"offsetY",1f);

        //applyOffset(stack,renderPlayer.getMainModel().bipedHead,model.bipedHead);

        alignModels(renderPlayer.getMainModel().bipedHead,modelHead,playerIn.isSneaking());
        alignModels(renderPlayer.getMainModel().bipedHeadwear,modelHead,playerIn.isSneaking());
        if(isAttached)
            modelHead.render(0.0625f);
        else
            model.bipedHeadwear.render(0.0625f);

        GlStateManager.popMatrix();

    }

    private void applyOffset(ItemStack stack,ModelRenderer original, ModelRenderer model)
    {
        model.cubeList.clear();
        model = original.setTextureOffset(2,2);
        float offset = ItemNBTHelper.getFloat(stack,"offsetY",0);
        model.setTextureOffset(0, (int) offset+3);
        model.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.25f);
        /*GL11.glMatrixMode(GL_TEXTURE);
        GL11.glPushMatrix();
        GL11.glTranslatef( 0.5f , 0.5f , 0 );*/

        /*GL11.glMatrixMode(GL_MODELVIEW);
        GL11.glTexCoordPointer( 2, GL_BYTE, 0, nokTexCoords);
        GL11.glVertexPointer( 3, GL_BYTE, 0, backgroundVertices);
        GL11.glBindTexture( GL_TEXTURE_2D, iLoadingTexObjects[2]);
        GL11.glDrawElements( GL_TRIANGLES, 2 * 3, GL_UNSIGNED_BYTE, triangles );
        GL11.glMatrixMode(GL_TEXTURE);
        GL11.glPopMatrix();

        //GlStateManager.translate(ItemNBTHelper.getFloat(stack,"offsetX",0),ItemNBTHelper.getFloat(stack,"offsetY",0),0);
        //float offsetY = (float) ( original.rotateAngleX<0 ? Math.min(-0.1,Math.cos(original.rotateAngleX)) : Math.max(0.05,-Math.sin(original.rotateAngleX)));
        //model.offsetY += ItemNBTHelper.getFloat(stack,"offsetY",0);
        /*model.setTextureSize(100,100);
        model.setTextureOffset(0, (int) Math.floor(ItemNBTHelper.getFloat(stack,"offsetY",0)));
        ModelBase baseModel = ReflectionHelper.getPrivateValue(ModelRenderer.class,model,"baseModel");
        baseModel.textureHeight = 10;*/

        //model.offsetX += ItemNBTHelper.getFloat(stack,"offsetX",0);
        //model.rotationPointY -= ItemNBTHelper.getFloat(stack,"offsetY",0);
        //model.rotationPointZ += ItemNBTHelper.getFloat(stack,"offsetY",0);
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

        glPushMatrix();

        glPushMatrix();

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


            glPushMatrix();
            GL11.glScaled(0.6,0.6,0.6);
            RenderHelper.enableGUIStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(activeAugment, 0, 0);
            GL11.glPopMatrix();
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if(ItemNBTHelper.getFloat(itemStackIn,"offsetY",0)<4)
        {
            ItemNBTHelper.setFloat(itemStackIn,"offsetY",ItemNBTHelper.getFloat(itemStackIn,"offsetY",0f)+1);
        }
        else
        {
            ItemNBTHelper.setFloat(itemStackIn,"offsetY",-4f);
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }
}
