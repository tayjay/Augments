package com.tayjay.augments.network;

import com.tayjay.augments.AugmentsMod;
import com.tayjay.augments.handler.PlayerHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by tayjm_000 on 2016-02-06.
 */
public class PacketSyncPlayerAugments implements IMessage, IMessageHandler<PacketSyncPlayerAugments,IMessage>
{
    int slot;
    int playerId;
    ItemStack partStack =null;

    public PacketSyncPlayerAugments(){}

    public PacketSyncPlayerAugments(EntityPlayer player, int slot)
    {
        this.slot = slot;
        this.partStack = PlayerHandler.getPlayerAugmentInventory(player).getStackInSlot(slot);
        this.playerId = player.getEntityId();
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        slot = buf.readByte();
        playerId = buf.readInt();
        partStack = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeByte(slot);
        buf.writeInt(playerId);
        ByteBufUtils.writeItemStack(buf, partStack);
    }

    @Override
    public IMessage onMessage(PacketSyncPlayerAugments message, MessageContext ctx)
    {
        World world = AugmentsMod.proxy.getClientWorld();
        if(world==null) return null; //Server side
        Entity p = world.getEntityByID(message.playerId);
        if(p instanceof EntityPlayer)
        {
            PlayerHandler.getPlayerAugmentInventory((EntityPlayer)p).inventory[message.slot] = message.partStack;
        }
        return null;
    }
}
