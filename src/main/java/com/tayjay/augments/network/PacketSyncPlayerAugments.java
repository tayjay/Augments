package com.tayjay.augments.network;

import com.tayjay.augments.AugmentsCore;
import com.tayjay.augments.handler.PlayerHandler;
import com.tayjay.augments.properties.PlayerAugmentProperties;
import com.tayjay.augments.util.ChatHelper;
import com.tayjay.augments.util.LogHelper;
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
    ItemStack augment=null;

    public PacketSyncPlayerAugments(){}

    public PacketSyncPlayerAugments(EntityPlayer player, int slot)
    {
        this.slot = slot;
        this.augment = PlayerHandler.getPlayerAugments(player).getStackInSlot(slot);
        this.playerId = player.getEntityId();
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        slot = buf.readByte();
        playerId = buf.readInt();
        augment = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeByte(slot);
        buf.writeInt(playerId);
        ByteBufUtils.writeItemStack(buf,augment);
    }

    @Override
    public IMessage onMessage(PacketSyncPlayerAugments message, MessageContext ctx)
    {
        World world = AugmentsCore.proxy.getClientWorld();
        if(world==null) return null; //Server side
        Entity p = world.getEntityByID(message.playerId);
        if(p instanceof EntityPlayer)
        {
            PlayerHandler.getPlayerAugments((EntityPlayer)p).inventory[message.slot] = message.augment;
        }
        return null;
    }
}
