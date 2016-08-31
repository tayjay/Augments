package com.tayjay.augments.network.packets;

import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.LogHelper;
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
    private int partsHash;

    public PacketREQSyncParts(){}



    public PacketREQSyncParts(EntityPlayer other)
    {
        this.playerId = other.getEntityId();
        this.partsHash = CapHelper.getPlayerPartsCap(other) != null ? CapHelper.getPlayerPartsCap(other).getHash() : 0;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.playerId = buf.readInt();
        this.partsHash = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.playerId);
        buf.writeInt(this.partsHash);
    }

    @Override
    public void handleServer(PacketREQSyncParts message, MessageContext ctx)
    {
        EntityPlayerMP playerOther = (EntityPlayerMP) ctx.getServerHandler().playerEntity.worldObj.getEntityByID(message.playerId);
        int otherHash = CapHelper.getPlayerPartsCap(playerOther).getHash();
        if(otherHash!=message.partsHash)
        {
            LogHelper.info("Request for part sync confirmed for "+playerOther.getDisplayNameString());
            NetworkHandler.sendTo(new PacketSyncPlayerParts(CapHelper.getPlayerPartsCap(playerOther).serializeNBT(), playerOther), ctx.getServerHandler().playerEntity);
        }
    }

    @Override
    public void handleClient(PacketREQSyncParts message, MessageContext ctx)
    {

    }
}
