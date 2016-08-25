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
        IItemHandler parts = CapHelper.getPlayerPartsCap(playerIn).getPartsInv();
        ItemStack stackCurrent = null;
        for(int i = 0;i<parts.getSlots();i++)
        {
            if(parts.getStackInSlot(i)!=null && parts.getStackInSlot(i).getItem() instanceof IBodyPart)
            {
                stackCurrent = parts.getStackInSlot(i);
                ((IBodyPart)stackCurrent.getItem()).renderOnPlayer(stackCurrent,playerIn,renderer);
            }
        }
        GL11.glPopMatrix();

    }

    @Override
    public boolean shouldCombineTextures()
    {
        return false;
    }
}
