package com.tayjay.augments.network;

import com.tayjay.augments.Augments;
import com.tayjay.augments.network.packets.PacketSpawnDiamond;
import net.minecraftforge.fml.common.network.NetworkRegistry;
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
        INSTANCE.registerMessage(PacketSpawnDiamond.class,PacketSpawnDiamond.class,0, Side.SERVER);
    }



}
