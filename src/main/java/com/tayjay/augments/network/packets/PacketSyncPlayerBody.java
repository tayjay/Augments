package com.tayjay.augments.network.packets;

import com.tayjay.augments.Augments;
import com.tayjay.augments.util.CapHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by tayjay on 2016-06-24.<br/>
 * Sync all parts on a player to the client.,br/>
 * This could be for Client player themselves or for another player to update what to render.
 */
public class PacketSyncPlayerBody extends PacketRunnable<PacketSyncPlayerBody>
{
    private int playerId;
    private NBTTagCompound nbt;

    public PacketSyncPlayerBody(){}

    /**
     * @param nbt       Tag of the parts on the server side player
     * @param player    Holder of the parts in the NBT
     */
    public PacketSyncPlayerBody(NBTTagCompound nbt, EntityPlayerMP player)
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
    public void handleServer(PacketSyncPlayerBody message, MessageContext ctx)
    {

    }

    @Override
    public void handleClient(PacketSyncPlayerBody message, MessageContext ctx)
    {
        if(message.playerId == Minecraft.getMinecraft().thePlayer.getEntityId())
            Augments.proxy.getClientPlayerParts().deserializeNBT(message.nbt);
        else
        {
            //TODO: CONSIDER REVISION! This may not work! May need another packet to sync data from another player.
            EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().theWorld.getEntityByID(message.playerId);
            CapHelper.getPlayerBodyCap(player).deserializeNBT(message.nbt);
        }
    }
}
