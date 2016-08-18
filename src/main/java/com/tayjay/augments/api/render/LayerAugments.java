package com.tayjay.augments.api.render;

import com.tayjay.augments.api.item.IBodyPart;
import com.tayjay.augments.event.PlayerEvents;
import com.tayjay.augments.init.ModItems;
import com.tayjay.augments.network.packets.PacketREQSyncParts;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.EntityUtil;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelElytra;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.opengl.GL11;

/**
 * Created by tayjay on 2016-06-25.
 */
public class LayerAugments implements LayerRenderer<EntityPlayer>
{
    public RenderPlayer renderer;
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
        //GL11.glScalef(1.001f,1.001f,1.001f);

        IItemHandler parts = CapHelper.getPlayerPartsCap(playerIn).getPartsInv();
        ItemStack stackCurrent = null;
        for(int i = 0;i<parts.getSlots();i++)
        {
            if(parts.getStackInSlot(i)!=null && parts.getStackInSlot(i).getItem() instanceof IBodyPart)
            {
                stackCurrent = parts.getStackInSlot(i);
                ((IBodyPart)stackCurrent.getItem()).renderOnPlayer(stackCurrent,playerIn,renderer);
                //LayerArmorBase.renderEnchantedGlint(renderer,playerIn, renderer.getMainModel(),limbSwing,limbSwingAmount,partialTicks,ageInTicks,netHeadYaw,headPitch,scale);

                if(false)
                {
                    boolean flag = playerIn.isInvisible();
                    GlStateManager.depthMask(!flag);
                    this.renderer.bindTexture(LIGHTNING_TEXTURE);
                    GlStateManager.matrixMode(5890);
                    GlStateManager.loadIdentity();
                    float f = (float)playerIn.ticksExisted + partialTicks;
                    GlStateManager.translate(f * 0.008F, f * 0.008F, 0.08F);
                    GlStateManager.matrixMode(5888);
                    GlStateManager.enableBlend();
                    float f1 = 0.5F;
                    GlStateManager.color(f1, f1, f1, 2F);
                    GlStateManager.disableLighting();
                    GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
                    this.renderer.getMainModel().setModelAttributes(this.renderer.getMainModel());
                    this.renderer.getMainModel().render(playerIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                    GlStateManager.matrixMode(5890);
                    GlStateManager.loadIdentity();
                    GlStateManager.matrixMode(5888);
                    GlStateManager.enableLighting();
                    GlStateManager.disableBlend();
                    GlStateManager.depthMask(flag);
                }
            }
        }
        GL11.glPopMatrix();

    }

    @Override
    public boolean shouldCombineTextures()
    {
        return false;
    }

    protected static final ResourceLocation ENCHANTED_ITEM_GLINT_RES = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    public static void renderEnchantedGlint(RenderLivingBase<?> p_188364_0_, EntityLivingBase p_188364_1_, ModelRenderer model)
    {
        GlStateManager.pushMatrix();
        float f = (float)p_188364_1_.ticksExisted;
        p_188364_0_.bindTexture(ENCHANTED_ITEM_GLINT_RES);
        GlStateManager.enableBlend();
        GlStateManager.depthFunc(514);
        GlStateManager.depthMask(false);
        float f1 = 0.5F;
        GlStateManager.color(f1, f1, f1, 1.0F);

        for (int i = 0; i < 2; ++i)
        {
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
            float f2 = 0.76F;
            GlStateManager.color(0.5F * f2, 0.25F * f2, 0.8F * f2, 1.0F);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float f3 = 0.33333334F;
            GlStateManager.scale(1,1,1);
            GlStateManager.rotate(30.0F - (float)i * 60.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(0.0F, f * (0.001F + (float)i * 0.003F) * 20.0F, 0.0F);
            GlStateManager.matrixMode(5888);
            model.render(0.0625f);
        }

        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        GlStateManager.enableLighting();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
