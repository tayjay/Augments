package com.tayjay.augments.api.render;

import com.tayjay.augments.api.item.IBodyPart;
import com.tayjay.augments.event.PlayerEvents;
import com.tayjay.augments.network.packets.PacketREQSyncParts;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
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

    @Override
    public void doRenderLayer(EntityPlayer playerIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {

        if(playerIn.getActivePotionEffect(MobEffects.INVISIBILITY)!=null)
            return;
        GL11.glPushMatrix();
        //GL11.glScalef(1.001f,1.001f,1.001f);
        if(playerIn instanceof EntityOtherPlayerMP &&!PlayerEvents.toREQSync.containsKey(playerIn.getEntityId()))
            PlayerEvents.toREQSync.put(playerIn.getEntityId(),new PacketREQSyncParts((EntityOtherPlayerMP)playerIn));
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
