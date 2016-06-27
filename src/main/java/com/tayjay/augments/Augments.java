package com.tayjay.augments;

import com.tayjay.augments.api.capabilities.impl.AugmentHolderImpl;
import com.tayjay.augments.api.capabilities.impl.PlayerDataImpl;
import com.tayjay.augments.api.capabilities.impl.PlayerPartsImpl;
import com.tayjay.augments.client.AugmentsTab;
import com.tayjay.augments.event.AugmentEvents;
import com.tayjay.augments.event.PlayerEvents;
import com.tayjay.augments.handler.GuiHandler;
import com.tayjay.augments.init.ModBlocks;
import com.tayjay.augments.init.ModItems;
import com.tayjay.augments.network.commands.CommandPlayerParts;
import com.tayjay.augments.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * Created by tayjay on 2016-06-23.
 */
@Mod(modid = Augments.modId,name = Augments.name,version = Augments.version, acceptedMinecraftVersions = "[1.9.4]",guiFactory = Augments.guiFactory)
public class Augments
{
    public static final String modId = "augments";
    public static final String name = "Augments";
    public static final String version = "0.0.1";
    public static final String guiFactory = "com.tayjay.augments.client.gui.GuiFactory";

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

        PlayerPartsImpl.init();
        PlayerDataImpl.init();
        AugmentHolderImpl.init();

        MinecraftForge.EVENT_BUS.register(new PlayerEvents());
        MinecraftForge.EVENT_BUS.register(new AugmentEvents());

        NetworkRegistry.INSTANCE.registerGuiHandler(instance,new GuiHandler());


    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.initRenderOverride();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandPlayerParts());
    }
}
