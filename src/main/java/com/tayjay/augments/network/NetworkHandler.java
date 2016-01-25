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
        INSTANCE.registerMessage(MessageSyncPlayerAugments.class,MessageSyncPlayerAugments.class,0,Side.CLIENT);
        INSTANCE.registerMessage(MessageOpenAugmentInventory.class,MessageOpenAugmentInventory.class,1, Side.SERVER);
    }
}
