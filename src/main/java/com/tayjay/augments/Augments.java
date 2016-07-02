package com.tayjay.augments;

import com.tayjay.augments.capability.AugmentHolderImpl;
import com.tayjay.augments.capability.PlayerDataImpl;
import com.tayjay.augments.capability.PlayerPartsImpl;
import com.tayjay.augments.client.AugmentsTab;
import com.tayjay.augments.event.AugmentEvents;
import com.tayjay.augments.event.PlayerEvents;
import com.tayjay.augments.handler.GuiHandler;
import com.tayjay.augments.init.ModBlocks;
import com.tayjay.augments.init.ModItems;
import com.tayjay.augments.network.commands.CommandAugments;
import com.tayjay.augments.network.commands.CommandPlayerParts;
import com.tayjay.augments.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
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
    public static final String version = "0.0.1.0";//MAJORMOD.MAJORAPI.MINOR.PATCH
    public static final String guiFactory = "com.tayjay.augments.client.gui.GuiFactory";

    @Mod.Instance(modId)
    public static Augments instance;

    @SidedProxy(serverSide = "com.tayjay.augments.proxy.CommonProxy",clientSide = "com.tayjay.augments.proxy.ClientProxy")
    public static CommonProxy proxy;

    public static final AugmentsTab creativeTab = new AugmentsTab();

    public static int renderType;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        this.renderType = config.get(Configuration.CATEGORY_CLIENT,"renderType",1,"How should the augments be rendered on the player.\n" +
                "0:Not rendered\n" +
                "1:Override RenderPlayer(Recommended but may conflict with other mods\n" +
                "2:Layer over player model.",0,2).getInt();
        config.save();
        ModBlocks.init();
        ModItems.init();

        PlayerPartsImpl.init();
        PlayerDataImpl.init();
        AugmentHolderImpl.init();
        proxy.preInit();


    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new PlayerEvents());
        MinecraftForge.EVENT_BUS.register(new AugmentEvents());

        NetworkRegistry.INSTANCE.registerGuiHandler(instance,new GuiHandler());

        proxy.initRenderOverride();
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandAugments());
    }
}
