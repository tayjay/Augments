package com.tayjay.augments.api.render;

import com.tayjay.augments.api.item.IBodyPart;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

/**
 * Created by tayjay on 2016-10-29.
 */
public class ItemRendererOverride extends ItemRenderer
{
    public ItemRendererOverride(Minecraft mcIn)
    {
        super(mcIn);
    }

    @Override
    public void renderItem(EntityLivingBase entityIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform)
    {
        if(heldStack!=null && heldStack.getItem() instanceof IBodyPart)
            renderBodyPart(heldStack,((IBodyPart) heldStack.getItem()).getPartType(heldStack),new RenderPlayer(Minecraft.getMinecraft().getRenderManager()));
        else
            super.renderItem(entityIn, heldStack, transform);
    }


    public void renderBodyPart(ItemStack stack, PartType type, RenderPlayer renderPlayer)
    {
        ModelRenderer model;
        ModelPlayer modelSteve = renderPlayer.getMainModel();
        boolean smallArms = RenderUtil.hasSmallArms(modelSteve);
        //Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("augments", "textures/models/blankBiped.png"));
        GlStateManager.pushMatrix();
        GL11.glPushMatrix();
        switch (type)
        {
            case HEAD:
                GlStateManager.translate(0,0.5,0);
                model = modelSteve.bipedHead;
                break;
            case EYES:
                Minecraft.getMinecraft().renderEngine.bindTexture(Minecraft.getMinecraft().thePlayer.getLocationSkin());
                GlStateManager.translate(0,0.5,0);
                //rotate = 180;
                model = modelSteve.bipedHead;
                break;
            case TORSO:

                model = modelSteve.bipedBody;
                break;
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


        GlStateManager.scale(1.1,1.1,1.1);
        //GlStateManager.enableAlpha();
        //GlStateManager.color(0,0,0,0.2f);

        //Minecraft.getMinecraft().renderEngine.bindTexture(Minecraft.getMinecraft().thePlayer.getLocationSkin());
        alignModels(renderPlayer.getMainModel().bipedBody,model,false);
        model.render(0.0625f);
        //GlStateManager.disableAlpha();
        //GlStateManager.color(1,1,1,0.2f);
        Minecraft.getMinecraft().renderEngine.bindTexture(((IBodyPart)stack.getItem()).getTexture(stack,false));
        model.render(0.0625f);
        //LayerAugments.renderEnchantedGlint(renderPlayer,playerIn, model);
        GL11.glPopMatrix();
        GlStateManager.popMatrix();
        //GL11.glPopMatrix();
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
}
