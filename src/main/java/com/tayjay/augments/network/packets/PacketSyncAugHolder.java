package com.tayjay.augments.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by tayjay on 2016-06-25.
 * Will Find a use for this eventually.
 * All data for itemstacks are supposedly synced on their own.
 * //TODO: Fix this to sync the itemstack correctly if not automatic in forge.
 */
public class PacketSyncAugHolder extends PacketRunnable<PacketSyncAugHolder>
{
    private NBTTagCompound nbt;

    public PacketSyncAugHolder(){}



    public PacketSyncAugHolder(NBTTagCompound nbt)
    {
        this.nbt = nbt;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        nbt = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeTag(buf,this.nbt);
    }

    @Override
    public void handleServer(PacketSyncAugHolder message, MessageContext ctx)
    {

    }

    @Override
    public void handleClient(PacketSyncAugHolder message, MessageContext ctx)
    {

    }
}
