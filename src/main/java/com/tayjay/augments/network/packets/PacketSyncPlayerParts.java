package com.tayjay.augments.network.packets;

import com.tayjay.augments.Augments;
import com.tayjay.augments.api.AugmentsAPI;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by tayjay on 2016-06-24.<br/>
 * Sync all parts on a player to the client.
 */
public class PacketSyncPlayerParts extends PacketRunnable<PacketSyncPlayerParts>
{
    private int playerId;
    private NBTTagCompound nbt;

    public PacketSyncPlayerParts(){}

    /**
     * @param nbt       Tag of the parts on the server side player
     * @param player    Holder of the parts in the NBT
     */
    public PacketSyncPlayerParts(NBTTagCompound nbt, EntityPlayerMP player)
    {
        this.playerId = player.getEntityId();
        this.nbt = nbt;
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
    public Runnable getRunnable(final PacketSyncPlayerParts message, MessageContext ctx)
    {
        return new Runnable()
        {
            @Override
            public void run()
            {
                if(message.playerId == Minecraft.getMinecraft().thePlayer.getEntityId())
                    Augments.proxy.getClientPlayerParts().deserializeNBT(message.nbt);
                else
                {
                    //TODO: CONSIDER REVISION! This may not work! May need another packet to sync data from another player.
                    EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().theWorld.getEntityByID(message.playerId);
                    player.getCapability(AugmentsAPI.PLAYER_PARTS_CAPABILITY,null).deserializeNBT(message.nbt);
                }
            }
        };
    }


}
