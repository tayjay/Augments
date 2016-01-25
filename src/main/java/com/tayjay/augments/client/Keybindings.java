package com.tayjay.augments.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

/**
 * Created by tayjm_000 on 2016-01-24.
 */
public enum Keybindings
{
    AUGMENT_INVENTORY("key.augments.augmentInventory", Keyboard.KEY_I);


    private final KeyBinding keyBinding;

    private Keybindings(String keyName, int defaultKeyCode)
    {
        keyBinding = new KeyBinding(keyName,defaultKeyCode,"key.categories.augments");
    }

    public KeyBinding getKeybind()
    {
        return keyBinding;
    }

    public boolean isPressed()
    {
        return keyBinding.isPressed();
    }

}
