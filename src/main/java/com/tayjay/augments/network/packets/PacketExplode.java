package com.tayjay.augments.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by tayjay on 2016-07-08.
 */
public class PacketExplode extends PacketRunnable<PacketExplode>
{
    private int entityId;
    private double x,y,z;
    private float strength;
    private boolean isSmoking;

    public PacketExplode(){}



    public PacketExplode(Entity entity, double x, double y, double z, float strength, boolean isSmoking)
    {
        this.entityId = entity.getEntityId();
        this.x = x;
        this.y = y;
        this.z = z;
        this.strength = strength;
        this.isSmoking = isSmoking;
    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityId = buf.readInt();
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.strength = buf.readFloat();
        this.isSmoking = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityId);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeFloat(this.strength);
        buf.writeBoolean(this.isSmoking);
    }

    @Override
    public Runnable getServerRunnable(final PacketExplode message, final MessageContext ctx)
    {
        return new Runnable()
        {
            @Override
            public void run()
            {
                Entity entity = ctx.getServerHandler().playerEntity.worldObj.getEntityByID(message.entityId);
                entity.worldObj.createExplosion(entity,message.x,message.y,message.z,message.strength,message.isSmoking);
            }
        };
    }

    @Override
    public Runnable getClientRunnable(PacketExplode message, MessageContext ctx)
    {
        return null;
    }
}
