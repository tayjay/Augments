package com.tayjay.augments;

import com.tayjay.augments.capability.AugmentDataImpl;
import com.tayjay.augments.capability.AugmentHolderImpl;
import com.tayjay.augments.capability.PlayerDataImpl;
import com.tayjay.augments.capability.PlayerBodyImpl;
import com.tayjay.augments.client.AugmentsTab;
import com.tayjay.augments.event.AugmentEvents;
import com.tayjay.augments.event.DrugEvents;
import com.tayjay.augments.event.PlayerEvents;
import com.tayjay.augments.handler.GuiHandler;
import com.tayjay.augments.init.ModBlocks;
import com.tayjay.augments.init.ModItems;
import com.tayjay.augments.network.commands.CommandAugments;
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
@Mod(modid = Augments.modId,name = Augments.name,version = Augments.version,guiFactory = Augments.guiFactory)
public class Augments
{
    public static final String modId = "augments";
    public static final String name = "Augments";
    public static final String version = "0.1.2.0";//MAJORMOD.MAJORAPI.MINOR.PATCH
    public static final String guiFactory = "com.tayjay.augments.client.GuiFactory";

    @Mod.Instance(modId)
    public static Augments instance;

    @SidedProxy(serverSide = "com.tayjay.augments.proxy.CommonProxy",clientSide = "com.tayjay.augments.proxy.ClientProxy")
    public static CommonProxy proxy;

    public static final AugmentsTab creativeTab = new AugmentsTab();

    public static int renderType;
    public static boolean drugDependant;
    public static int maxAllowedEnergy;
    public static int maxAllowedAugmentCapacity;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        initConfig(event);

        ModBlocks.init();
        ModItems.init();

        PlayerBodyImpl.init();
        PlayerDataImpl.init();
        //AugmentHolderImpl.init();
        AugmentDataImpl.init();
        proxy.preInit();


    }

    private void initConfig(FMLPreInitializationEvent event)
    {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        renderType = config.get(Configuration.CATEGORY_CLIENT,"renderType",2,"How should the augments be rendered on the player?\n" +
                "0:Not rendered\n" +
                "1:Layer over player model\n"+
                "2:Override RenderPlayer(Recommended but may conflict with other mods)" ,0,2).getInt();
        drugDependant = config.get(Configuration.CATEGORY_GENERAL,"drugDependant",true,"Is the player required to take anti-rejection drugs in order to use the augments?").getBoolean();
        maxAllowedEnergy = config.get(Configuration.CATEGORY_GENERAL,"maximumEnergy",5,"Maximum amount of energy charges a player is allowed to hold",0,6).getInt();
        maxAllowedAugmentCapacity = config.get(Configuration.CATEGORY_GENERAL,"augmentCapacity",5,"Most Augments that a player can hold",0,6).getInt();
        config.save();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new PlayerEvents());
        MinecraftForge.EVENT_BUS.register(new AugmentEvents());
        if(drugDependant)
            MinecraftForge.EVENT_BUS.register(new DrugEvents());

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
