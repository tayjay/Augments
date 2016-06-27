package com.tayjay.augments.item;

import com.tayjay.augments.api.item.PartType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by tayjay on 2016-06-26.
 * Similar to ItemArmor implementation. Each body part is it's own object off this.
 */
public class ItemBodySet extends ItemBodyPart
{
    public ItemBodySet(String name, int tier, PartType type)
    {
        super(name, tier,type);
        this.type = type;
    }



    ModelBiped modelBiped = new ModelBiped();
    @Override
    public void renderOnPlayer(ItemStack stack, EntityPlayer playerIn, RenderPlayer renderPlayer)
    {
        ModelRenderer parent;
        ModelRenderer model;
        switch (getPartType(stack))
        {
            case HEAD:
                parent = renderPlayer.getMainModel().bipedHead;
                model = modelBiped.bipedHead;
                break;
            case EYES:
                parent = renderPlayer.getMainModel().bipedHead;
                model = modelBiped.bipedHead;
                break;
            case TORSO:
                parent = renderPlayer.getMainModel().bipedBody;
                model = modelBiped.bipedBody;
                break;
            case ARM_RIGHT:
                parent = renderPlayer.getMainModel().bipedRightArm;
                model = modelBiped.bipedRightArm;
                break;
            case ARM_LEFT:
                parent = renderPlayer.getMainModel().bipedLeftArm;
                model = modelBiped.bipedLeftArm;
                break;
            case LEG_RIGHT:
                parent = renderPlayer.getMainModel().bipedRightLeg;
                model = modelBiped.bipedRightLeg;
                break;
            case LEG_LEFT:
                parent = renderPlayer.getMainModel().bipedLeftLeg;
                model = modelBiped.bipedLeftLeg;
                break;
            default:
                parent = renderPlayer.getMainModel().bipedHeadwear;
                model = modelBiped.bipedHeadwear;
                break;
        }
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("augments", "textures/models/blankBiped.png"));

        alignModels(parent,model,playerIn.isSneaking());

        model.render(0.0625f);

        GL11.glPopMatrix();
    }
}
