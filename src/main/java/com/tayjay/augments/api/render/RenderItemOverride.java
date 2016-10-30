package com.tayjay.augments.api.render;

import com.tayjay.augments.api.item.IBodyPart;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.opengl.GL11;

/**
 * Created by tayjay on 2016-10-29.
 */
public class RenderItemOverride extends RenderItem
{
    public RenderItemOverride(TextureManager p_i46552_1_, ModelManager p_i46552_2_, ItemColors p_i46552_3_)
    {
        super(p_i46552_1_, p_i46552_2_, p_i46552_3_);
    }

    @Override
    protected void renderItemModelIntoGUI(ItemStack stack, int x, int y, IBakedModel bakedmodel)
    {
        super.renderItemModelIntoGUI(stack, x, y, bakedmodel);
    }

    @Override
    public void renderItem(ItemStack stack, IBakedModel model)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
        if(stack!=null && stack.getItem() instanceof IBodyPart)
            renderBodyPart(stack,((IBodyPart) stack.getItem()).getPartType(stack),new RenderPlayer(Minecraft.getMinecraft().getRenderManager()));
        else
        {
            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            super.renderItem(stack, model);
        }
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

    public static void override(RenderItem original)
    {
        TextureManager textureManager = ReflectionHelper.getPrivateValue(RenderItem.class,original,"textureManager");
        ItemModelMesher itemModelMesher = ReflectionHelper.getPrivateValue(RenderItem.class,original,"itemModelMesher");
        ModelManager modelManager = ReflectionHelper.getPrivateValue(ItemModelMesher.class,itemModelMesher,"modelManager");
        ItemColors itemColors = ReflectionHelper.getPrivateValue(RenderItem.class,original,"itemColors");
        RenderItemOverride newRenderItem = new RenderItemOverride(textureManager,modelManager,itemColors);
        ReflectionHelper.setPrivateValue(Minecraft.class,Minecraft.getMinecraft(),newRenderItem,"renderItem");
        //ReflectionHelper.setPrivateValue(RenderItemOverride.class,newRenderItem,ReflectionHelper.getPrivateValue(RenderItem.class,original,"itemModelMesher"));
        //return newRenderItem;
    }
}
