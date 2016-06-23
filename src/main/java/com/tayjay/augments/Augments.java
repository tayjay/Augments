package com.tayjay.augments;

import com.tayjay.augments.client.AugmentsTab;
import com.tayjay.augments.init.ModBlocks;
import com.tayjay.augments.init.ModItems;
import com.tayjay.augments.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by tayjay on 2016-06-23.
 */
@Mod(modid = Augments.modId,name = Augments.name,version = Augments.version, acceptedMinecraftVersions = "[1.9.4]")
public class Augments
{
    public static final String modId = "augments";
    public static final String name = "Augments";
    public static final String version = "0.0.1";

    @Mod.Instance(modId)
    public static Augments instance;

    @SidedProxy(serverSide = "com.tayjay.augments.proxy.CommonProxy",clientSide = "com.tayjay.augments.proxy.ClientProxy")
    public static CommonProxy proxy;

    public static final AugmentsTab creativeTab = new AugmentsTab();


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ModBlocks.init();
        ModItems.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
