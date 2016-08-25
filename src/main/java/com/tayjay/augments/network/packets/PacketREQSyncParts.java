package com.tayjay.augments.network.packets;

import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.util.CapHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by tayjay on 2016-06-26.
 */
public class PacketREQSyncParts extends PacketRunnable<PacketREQSyncParts>
{
    private int playerId;

    public PacketREQSyncParts(){}



    public PacketREQSyncParts(EntityPlayer other)
    {
        this.playerId = other.getEntityId();
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.playerId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.playerId);
    }

    @Override
    public void handleServer(PacketREQSyncParts message, MessageContext ctx)
    {
        EntityPlayerMP playerOther = (EntityPlayerMP) ctx.getServerHandler().playerEntity.worldObj.getEntityByID(message.playerId);
        NetworkHandler.sendTo(new PacketSyncPlayerParts(CapHelper.getPlayerPartsCap(playerOther).serializeNBT(),playerOther),ctx.getServerHandler().playerEntity);
    }

    @Override
    public void handleClient(PacketREQSyncParts message, MessageContext ctx)
    {

    }
}
