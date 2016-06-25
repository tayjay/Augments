package com.tayjay.augments.client.gui;

import com.tayjay.augments.Augments;
import com.tayjay.augments.handler.ConfigHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

/**
 * Created by tayjay on 2016-06-24.
 */
public class ModGuiConfig extends GuiConfig
{
    public ModGuiConfig(GuiScreen guiScreen)
    {
        super(guiScreen,new ConfigElement(ConfigHandler.configuration.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), Augments.modId,
                false,false,GuiConfig.getAbridgedConfigPath(ConfigHandler.configuration.toString()));

    }
}
