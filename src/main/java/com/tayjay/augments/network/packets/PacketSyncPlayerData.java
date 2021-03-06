package com.tayjay.augments.network.packets;

import com.tayjay.augments.Augments;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import scala.collection.parallel.ParIterableLike;

/**
 * Created by tayjay on 2016-06-29.
 */
public class PacketSyncPlayerData extends PacketRunnable<PacketSyncPlayerData>
{
    private int playerId;
    private NBTTagCompound nbt;

    public PacketSyncPlayerData(){}



    public PacketSyncPlayerData(NBTTagCompound tag, EntityPlayer player)
    {
        this.playerId = player.getEntityId();
        this.nbt = tag;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.playerId = buf.readInt();
        this.nbt = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.playerId);
        ByteBufUtils.writeTag(buf,this.nbt);
    }

    @Override
    public void handleServer(PacketSyncPlayerData message, MessageContext ctx)
    {

    }

    @Override
    public void handleClient(PacketSyncPlayerData message, MessageContext ctx)
    {
        if(message.playerId == Minecraft.getMinecraft().thePlayer.getEntityId())
            Augments.proxy.getClientPlayerData().deserializeNBT(message.nbt);
    }
}
