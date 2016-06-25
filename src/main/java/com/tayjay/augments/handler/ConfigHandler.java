package com.tayjay.augments.handler;

import com.tayjay.augments.Augments;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

/**
 * Created by tayjay on 2016-06-24.
 */
public class ConfigHandler
{
    public static Configuration configuration;
    public static boolean testValue = false;

    public static void init(File configFile)
    {
        if (configuration == null)
        {
            //Create the configuration object from the given configuration file.
            configuration = new Configuration(configFile);
            loadConfig();
        }
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent event)
    {
        if (event.getModID().equalsIgnoreCase(Augments.modId))
        {
            //resync configs
            loadConfig();
        }
    }

    private static void loadConfig()
    {
        testValue = configuration.getBoolean("configValue", Configuration.CATEGORY_GENERAL, false, "This is an example config value.");

        if (configuration.hasChanged())
        {
            configuration.save();
        }
    }
}
