package com.tayjay.augments.network;

import com.tayjay.augments.AugmentsCore;
import com.tayjay.augments.handler.GuiHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by tayjm_000 on 2016-01-24.
 */
public class MessageOpenAugmentInventory extends MessageBase<MessageOpenAugmentInventory>
{
    @Override
    public void handleServerSide(MessageOpenAugmentInventory message, EntityPlayer player)
    {
        player.openGui(AugmentsCore.instance, GuiHandler.GuiIDs.INVENTORY_AUGMENT_PLAYER.ordinal(), player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
    }

    @Override
    public void handleClientSide(MessageOpenAugmentInventory message, EntityPlayer player)
    {
        //NOOP
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {

    }

    @Override
    public void toBytes(ByteBuf buf)
    {

    }
}
