package com.tayjay.augments.item.bodyParts;

import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.api.render.LayerAugments;
import com.tayjay.augments.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

/**
 * Created by tayjay on 2016-06-26.
 * Similar to ItemArmor implementation. Each body part is it's own object off this.
 */
public class ItemBodySet extends ItemBodyPart
{
    public ItemBodySet(String name, int tier,int armourValue, int storageSize, String textureName, PartType type)
    {
        super(name, tier,armourValue,storageSize,textureName,type);
    }

    public ItemBodySet(String name,int tier,int armourValue, int storageSize, String texture,String textureS, PartType type)
    {
        super(name,tier,armourValue,storageSize,texture,textureS,type);
    }

    @Override
    public void renderOnPlayer(ItemStack stack, EntityPlayer playerIn, RenderPlayer renderPlayer)
    {
        ModelRenderer parent;
        ModelRenderer model;
        boolean smallArms = RenderUtil.hasSmallArms(renderPlayer.getMainModel());
        switch (getPartType(stack))
        {
            case HEAD:
                parent = renderPlayer.getMainModel().bipedHead;
                model = modelSteve.bipedHead;
                break;
            case EYES:
                parent = renderPlayer.getMainModel().bipedHead;
                model = modelSteve.bipedHead;
                break;
            case TORSO:
                parent = renderPlayer.getMainModel().bipedBody;
                model = modelSteve.bipedBody;
                break;
            case ARM_RIGHT:
                parent = renderPlayer.getMainModel().bipedRightArm;
                model = smallArms ? modelAlex.bipedRightArm : modelSteve.bipedRightArm;
                break;
            case ARM_LEFT:
                parent = renderPlayer.getMainModel().bipedLeftArm;
                model = smallArms ? modelAlex.bipedLeftArm : modelSteve.bipedLeftArm;
                break;
            case LEG_RIGHT:
                parent = renderPlayer.getMainModel().bipedRightLeg;
                model = modelSteve.bipedRightLeg;
                break;
            case LEG_LEFT:
                parent = renderPlayer.getMainModel().bipedLeftLeg;
                model = modelSteve.bipedLeftLeg;
                break;
            default:
                parent = renderPlayer.getMainModel().bipedHeadwear;
                model = modelSteve.bipedHeadwear;
                break;
        }
        GL11.glPushMatrix();

        Minecraft.getMinecraft().renderEngine.bindTexture(getTexture(stack,smallArms));

        alignModels(parent,model,playerIn.isSneaking());

        model.render(0.0625f);
        //LayerAugments.renderEnchantedGlint(renderPlayer,playerIn, model);


        GL11.glPopMatrix();
    }
}
