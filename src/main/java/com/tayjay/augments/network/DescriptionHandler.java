package com.tayjay.augments.network;

import com.tayjay.augments.AugmentsCore;
import com.tayjay.augments.lib.Reference;
import com.tayjay.augments.tileentity.TileEntityAug;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by tayjm_000 on 2016-04-05.
 * Handle updates from TileEntities
 */
@ChannelHandler.Sharable
public class DescriptionHandler extends SimpleChannelInboundHandler<FMLProxyPacket>
{
    public static final String CHANNEL = Reference.MOD_ID+" Description";

    /**
     * Create a new channel to carry a DescriptionHandler Packet
     */
    static {
        NetworkRegistry.INSTANCE.newChannel(CHANNEL, new DescriptionHandler());
    }

    public static void init()
    {
        //Does nothing here. Used to catch an exception
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FMLProxyPacket msg) throws Exception
    {
        ByteBuf buf = msg.payload();
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        TileEntity te = AugmentsCore.proxy.getClientPlayer().worldObj.getTileEntity(x,y,z);
        if(te instanceof TileEntityAug)
        {
            ((TileEntityAug)te).readFromPacket(buf);
        }
    }
}
