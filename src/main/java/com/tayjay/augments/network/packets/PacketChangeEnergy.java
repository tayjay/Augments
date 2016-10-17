package com.tayjay.augments.network.packets;

import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.LogHelper;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by tayjay on 2016-06-30.
 */
public class PacketChangeEnergy extends PacketRunnable<PacketChangeEnergy>
{
    private float amount;
    private int type;

    public PacketChangeEnergy(){}



    public PacketChangeEnergy(float amount, EnergyType type)
    {
        this.amount = amount;
        this.type = type.ordinal();
    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.amount = buf.readFloat();
        this.type = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeFloat(amount);
        buf.writeInt(type);
    }

    @Override
    public void handleServer(PacketChangeEnergy message, MessageContext ctx)
    {
        switch (message.type)
        {
            case 1:
                CapHelper.getPlayerDataCap(ctx.getServerHandler().playerEntity).removeEnergy(message.amount);
                break;
            default:
                LogHelper.info("This PacketChangeEnergy has not been setup correctly with type "+message.type);
        }
    }

    @Override
    public void handleClient(PacketChangeEnergy message, MessageContext ctx)
    {
    }

    public enum EnergyType
    {
        MAX,CURRENT,RECHARGE
    }
}
