package com.tayjay.augments.network;

import com.tayjay.augments.properties.PlayerAugmentProperties;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by tayjm_000 on 2016-01-24.
 */
public class MessageSyncPlayerAugments extends MessageBase<MessageSyncPlayerAugments>
{
    private NBTTagCompound data;

    public MessageSyncPlayerAugments(){}

    public MessageSyncPlayerAugments(EntityPlayer player)
    {
        data = new NBTTagCompound();
        PlayerAugmentProperties.get(player).saveNBTData(data);
    }

    @Override
    public void handleServerSide(MessageSyncPlayerAugments message, EntityPlayer player)
    {
        //NOOP
    }

    @Override
    public void handleClientSide(MessageSyncPlayerAugments message, EntityPlayer player)
    {
        PlayerAugmentProperties.get(player).loadNBTData(data);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeTag(buf,data);
    }
}
