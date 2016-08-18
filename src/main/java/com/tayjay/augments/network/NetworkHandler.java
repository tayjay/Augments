package com.tayjay.augments.network;

import com.tayjay.augments.Augments;
import com.tayjay.augments.network.packets.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by tayjay on 2016-06-23.
 */
public class NetworkHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Augments.modId);

    static
    {
        int desc = 0;//Discriminator
        //Server Packets
        INSTANCE.registerMessage(PacketOpenGui.class,PacketOpenGui.class,desc++, Side.SERVER);
        INSTANCE.registerMessage(PacketREQSyncParts.class, PacketREQSyncParts.class,desc++,Side.SERVER);
        INSTANCE.registerMessage(PacketChangeEnergy.class,PacketChangeEnergy.class,desc++,Side.SERVER);

        INSTANCE.registerMessage(PacketExplode.class,PacketExplode.class,desc++,Side.SERVER);

        //Client Packets
        INSTANCE.registerMessage(PacketSyncPlayerParts.class,PacketSyncPlayerParts.class,desc++,Side.CLIENT);
        INSTANCE.registerMessage(PacketSyncPlayerData.class,PacketSyncPlayerData.class,desc++,Side.CLIENT);
    }

    public static void sendToAll(IMessage msg)
    {
        INSTANCE.sendToAll(msg);
    }

    public static void sendTo(IMessage msg, EntityPlayerMP player)
    {
        INSTANCE.sendTo(msg,player);
    }

    public static void sendToAllAround(IMessage msg, EntityPlayerMP player, double range)
    {
        sendToAllAround(msg, player.dimension, player.posX, player.posY, player.posZ, range);
    }

    public static void sendToAllAround(IMessage msg, int dim, double x, double y, double z, double range)
    {
        INSTANCE.sendToAllAround(msg, new NetworkRegistry.TargetPoint(dim, x, y, z, range));
    }

    public static void sendToServer(IMessage msg)
    {
        INSTANCE.sendToServer(msg);
    }



}
