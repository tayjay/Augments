package com.tayjay.augments.client;


import com.tayjay.augments.handler.GuiHandler;
import com.tayjay.augments.network.MessageOpenAugmentInventory;
import com.tayjay.augments.network.MessageOpenGuiServer;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.proxy.ClientProxy;
import com.tayjay.augments.util.LogHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

/**
 * Created by tayjm_000 on 2016-01-24.
 */
public class KeyInputHandler
{
    private Keybindings getPressedKey()
    {
        for(Keybindings key : Keybindings.values())
        {
            if(key.isPressed()) return key;
        }
        return null;
    }

    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.KeyInputEvent event)
    {
        Keybindings key = getPressedKey();
        if(key != null)
        {
            switch (key)
            {
                case AUGMENT_INVENTORY:
                    LogHelper.info("Opening Inventory."+GuiHandler.GuiIDs.INVENTORY_AUGMENT_PLAYER.ordinal());
                    NetworkHandler.INSTANCE.sendToServer(new MessageOpenGuiServer(GuiHandler.GuiIDs.INVENTORY_AUGMENT_PLAYER.ordinal()));
                    break;
            }
        }
    }
}
