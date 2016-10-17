package com.tayjay.augments.api.render;

import com.tayjay.augments.api.item.IBodyPart;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.opengl.GL11;

/**
 * Created by tayjay on 2016-06-25.
 */
public class LayerAugments implements LayerRenderer<EntityPlayer>
{
    public RenderPlayer renderer;
    //public ModelRenderOBJ model;
    public LayerAugments(RenderPlayer renderIn)
    {
        renderer = renderIn;
    }

    private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");


    @Override
    public void doRenderLayer(EntityPlayer playerIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {

        if(playerIn.getActivePotionEffect(MobEffects.INVISIBILITY)!=null)
            return;
        GL11.glPushMatrix();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        GlStateManager.depthMask(true);
        //RenderHelper.disableStandardItemLighting();
        IItemHandler parts = CapHelper.getPlayerBodyCap(playerIn).getBodyParts();
        ItemStack stackCurrent;
        for(int i = 0;i<parts.getSlots();i++)
        {
            if(parts.getStackInSlot(i)!=null && parts.getStackInSlot(i).getItem() instanceof IBodyPart)
            {
                stackCurrent = parts.getStackInSlot(i);
                ((IBodyPart)stackCurrent.getItem()).renderOnPlayer(stackCurrent,playerIn,renderer);
            }
        }
        GlStateManager.depthMask(false);
        //RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();

    }

    @Override
    public boolean shouldCombineTextures()
    {
        return false;
    }
}
