package com.tayjay.augments.item.bodyParts;

import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.api.render.LayerAugments;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.GlStateManager;
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
    public ItemBodySet(String name, int tier,int armourValue, int storageSize, String textureName, PartType type)//todo: Set name?
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
        //renderPlayer.getMainModel().bipedRightArm.isHidden = false;
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
            //Without any major changes I found this method to determine which side the arm and leg stacks are on.
            //Since I removed the ARM_LEFT,ARM_RIGHT system
            case ARM:
                ItemStack leftArm = CapHelper.getPlayerBodyCap(playerIn).getStackByPartSided(PartType.ARM,0);
                if(leftArm!=null && leftArm.equals(stack))//If this stack is the one in the left arm slot
                {
                    parent = renderPlayer.getMainModel().bipedLeftArm;
                    model = smallArms ? modelAlex.bipedLeftArm : modelSteve.bipedLeftArm;
                }
                else//This stack is in the right arm slot by elimination
                {
                    parent = renderPlayer.getMainModel().bipedRightArm;
                    model = smallArms ? modelAlex.bipedRightArm : modelSteve.bipedRightArm;
                }
                break;
            case LEG:
                ItemStack leftLeg = CapHelper.getPlayerBodyCap(playerIn).getStackByPartSided(PartType.LEG,0);
                if(leftLeg !=null && leftLeg.equals(stack))
                {
                    parent = renderPlayer.getMainModel().bipedLeftLeg;
                    model = modelSteve.bipedLeftLeg;
                }
                else
                {
                    parent = renderPlayer.getMainModel().bipedRightLeg;
                    model = modelSteve.bipedRightLeg;
                }
                break;
            default:
                parent = renderPlayer.getMainModel().bipedHeadwear;
                model = modelSteve.bipedHeadwear;
                break;
        }

        /*if(CapHelper.getPlayerBodyCap(playerIn).getStackByPart(PartType.HEAD)!=null)
            renderPlayer.getMainModel().bipedRightArm = new ModelSkeleton().bipedRightArm;*/


        //GL11.glPushMatrix();
        GlStateManager.pushMatrix();

        Minecraft.getMinecraft().renderEngine.bindTexture(getTexture(stack,smallArms));

        alignModels(parent,model,playerIn.isSneaking());

        model.render(0.0625f);
        //LayerAugments.renderEnchantedGlint(renderPlayer,playerIn, model);

        GlStateManager.popMatrix();
        //GL11.glPopMatrix();
    }
}
