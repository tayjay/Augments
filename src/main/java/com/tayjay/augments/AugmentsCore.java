package com.tayjay.augments;

import com.tayjay.augments.init.ModBlocks;
import com.tayjay.augments.init.ModItems;
import com.tayjay.augments.proxy.CommonProxy;
import com.tayjay.augments.util.LogHelper;
import cpw.mods.fml.common.Mod;
import com.tayjay.augments.lib.Reference;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

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

    /**
     * Create Sided proxy.
     */
    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS,serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LogHelper.info("Starting Pre-Augment.");
        proxy.preInit();
        ModItems.init();
        ModBlocks.init();
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
