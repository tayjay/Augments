package com.tayjay.augments.network.packets;

import com.tayjay.augments.Augments;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by tayjay on 2016-07-03.
 */
public class PacketOpenGui extends PacketRunnable<PacketOpenGui>
{
    private int guiId;

    public PacketOpenGui(){}


    public PacketOpenGui(int guiId)
    {
        this.guiId = guiId;
    }

    @Override
    public void handleServer(PacketOpenGui message,MessageContext ctx)
    {
        ctx.getServerHandler().playerEntity.openGui(Augments.instance,message.guiId,ctx.getServerHandler().playerEntity.worldObj,0,0,0);
    }

    @Override
    public void handleClient(PacketOpenGui message, MessageContext ctx)
    {

    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.guiId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.guiId);
    }
}
