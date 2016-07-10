package com.tayjay.augments.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by tayjay on 2016-06-23.
 */
public abstract class PacketRunnable<REQ extends IMessage> implements IMessage, IMessageHandler<REQ,IMessage>
{
    public PacketRunnable(){}

    @Override
    public REQ onMessage(REQ message, MessageContext ctx)
    {
        if(ctx.side == Side.CLIENT)
            Minecraft.getMinecraft().addScheduledTask(getClientRunnable(message,ctx));
        else
            ctx.getServerHandler().playerEntity.getServerWorld().addScheduledTask(getServerRunnable(message,ctx));
        return null;
    }

    /*
    @Deprecated
    public abstract Runnable getRunnable(final REQ message,final MessageContext ctx);
    */

    public abstract Runnable getServerRunnable(final REQ message,final MessageContext ctx);

    public abstract Runnable getClientRunnable(final REQ message,final MessageContext ctx);
}
