package com.tayjay.augments.network.packets;

import com.tayjay.augments.util.CapHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by tayjay on 2016-08-31.
 */
public class PacketCycleAugment extends PacketRunnable<PacketCycleAugment>
{
    int playerID;
    public PacketCycleAugment(){}

    public PacketCycleAugment(EntityPlayer player)
    {
        this.playerID = player.getEntityId();
    }
    @Override
    public void handleServer(PacketCycleAugment message, MessageContext ctx)
    {
        CapHelper.getPlayerDataCap(ctx.getServerHandler().playerEntity).cycleActiveAugment();
    }

    @Override
    public void handleClient(PacketCycleAugment message, MessageContext ctx)
    {

    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.playerID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.playerID);
    }
}
