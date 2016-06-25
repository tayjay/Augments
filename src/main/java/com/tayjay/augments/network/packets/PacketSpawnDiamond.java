package com.tayjay.augments.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by tayjay on 2016-06-23.
 */
public class PacketSpawnDiamond extends PacketRunnable<PacketSpawnDiamond>
{
    public int x,y,z,amount;

    public PacketSpawnDiamond(){}

    public PacketSpawnDiamond(World world, int x, int y, int z, int amount)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.amount = amount;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        //super.toBytes(buf);
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeInt(this.amount);
    }

    @Override
    public Runnable getRunnable(PacketSpawnDiamond message, MessageContext ctx)
    {
        return new TaskSpawnDiamond(message,ctx);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        //super.fromBytes(buf);
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.amount = buf.readInt();
    }
}
