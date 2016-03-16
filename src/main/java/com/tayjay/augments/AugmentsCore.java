package com.tayjay.augments;

import com.tayjay.augments.augment.handlers.ClientAugmentHandler;
import com.tayjay.augments.augment.handlers.ServerAugmentHandler;
import com.tayjay.augments.client.KeyInputHandler;
import com.tayjay.augments.client.Keybindings;
import com.tayjay.augments.command.CommandSync;
import com.tayjay.augments.handler.EventHandlerEntity;
import com.tayjay.augments.handler.EventHandlerNetwork;
import com.tayjay.augments.handler.GuiHandler;
import com.tayjay.augments.init.ModBlocks;
import com.tayjay.augments.init.ModItems;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.properties.PropertyHandler;
import com.tayjay.augments.proxy.CommonProxy;
import com.tayjay.augments.util.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import com.tayjay.augments.lib.Reference;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraftforge.common.MinecraftForge;
import scala.collection.parallel.ParIterableLike;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tayjm_000 on 2016-01-16.
 * Main Mod class
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS)
public class AugmentsCore
{
    /**
     * Mod Instance for Forge
     */
    @Mod.Instance
    public static AugmentsCore instance;

    public EventHandlerEntity entityEventHandler;
    public EventHandlerNetwork entityEventNetwork;

    /**
     * Create Sided proxy.
     */
    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS,serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandSync());
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LogHelper.info("Starting Pre-Augment.");
        proxy.preInit();
        ModItems.init();
        ModBlocks.init();

        //Register the GUIHandler for the mod.
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        MinecraftForge.EVENT_BUS.register(new PropertyHandler());
        FMLCommonHandler.instance().bus().register(new PropertyHandler());

        entityEventHandler = new EventHandlerEntity();
        entityEventNetwork = new EventHandlerNetwork();

        MinecraftForge.EVENT_BUS.register(entityEventHandler);
        MinecraftForge.EVENT_BUS.register(entityEventNetwork);


        NetworkHandler.init();



    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        LogHelper.info("Augmenting your game.");
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        LogHelper.info("Finishing Augments");
        proxy.postInit();
    }
}
