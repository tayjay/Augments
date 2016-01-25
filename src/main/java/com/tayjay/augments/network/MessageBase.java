package com.tayjay.augments.network;

import com.tayjay.augments.AugmentsCore;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by tayjm_000 on 2016-01-24.
 */
public abstract class MessageBase<REQ extends IMessage> implements IMessage, IMessageHandler<REQ,REQ>
{

    /**
     * Seperate the sides to handle the Message Packet
     * @param message
     * @param ctx
     * @return
     */
    @Override
    public REQ onMessage(REQ message, MessageContext ctx)
    {
        if(ctx.side == Side.SERVER)
        {
            handleServerSide(message, ctx.getServerHandler().playerEntity);
        }
        else
        {
            handleClientSide(message, AugmentsCore.proxy.getClientPlayer());
        }
        return null;
    }

    /**
     * Handle a packet on the Server side. Note this happens after the decoding is completed.
     * @param message
     * @param player
     */
    public abstract void handleServerSide(REQ message, EntityPlayer player);

    /**
     * Handle a packet on the Client Side. Note this happens after the decoding has completed.
     * @param message
     * @param player
     */
    public abstract void handleClientSide(REQ message, EntityPlayer player);


}
