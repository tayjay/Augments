package com.tayjay.augments.network.packets;

import com.tayjay.augments.api.item.IAugment;
import com.tayjay.augments.util.CapHelper;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by tayjay on 2016-10-16.
 */
public class PacketActivateAugment extends PacketRunnable<PacketActivateAugment>//todo: CHANGE TO NOT GET AUGMENT FROM PLAYER CAP
{
    @Override
    public void handleServer(PacketActivateAugment message, MessageContext ctx)
    {
        ((IAugment) CapHelper.getPlayerDataCap(ctx.getServerHandler().playerEntity).getCurrentAugment().getItem()).activate(CapHelper.getPlayerDataCap(ctx.getServerHandler().playerEntity).getCurrentAugment(),ctx.getServerHandler().playerEntity);
    }

    @Override
    public void handleClient(PacketActivateAugment message, MessageContext ctx)
    {

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
