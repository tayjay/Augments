package com.tayjay.augments.network;

import com.tayjay.augments.lib.Reference;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * Created by tayjm_000 on 2016-01-24.
 */
public class NetworkHandler
{
    public static SimpleNetworkWrapper INSTANCE;

    public static void init()
    {
        INSTANCE  = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

        //Register all packets here
        INSTANCE.registerMessage(MessageOpenGuiServer.class,MessageOpenGuiServer.class,0,Side.SERVER);
        INSTANCE.registerMessage(MessageSyncPlayerAugments.class,MessageSyncPlayerAugments.class,1,Side.CLIENT);
        INSTANCE.registerMessage(MessageSyncPlayerAugments.class,MessageSyncPlayerAugments.class,2,Side.SERVER);
        INSTANCE.registerMessage(MessageOpenAugmentInventory.class,MessageOpenAugmentInventory.class,3, Side.SERVER);
        INSTANCE.registerMessage(PacketSyncPlayerAugments.class,PacketSyncPlayerAugments.class,4,Side.CLIENT);
    }
}
