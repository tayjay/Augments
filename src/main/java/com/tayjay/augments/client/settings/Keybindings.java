package com.tayjay.augments.client.settings;


import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

/**
 * Created by tayjay on 2016-06-30.
 */
public enum Keybindings
{
    ACTIVATE("keys.augments.activate", Keyboard.KEY_G),
    OPEN_PLAYER_PARTS_GUI("keys.augments.openpartgui", Keyboard.KEY_I);

    private final KeyBinding keyBinding;

    Keybindings(String keyName, int defaultKeyCode)
    {
        keyBinding = new KeyBinding(keyName,defaultKeyCode,"key.augments.category");
    }

    public KeyBinding getKeybind(){return keyBinding;}

    public boolean isPressed() { return keyBinding.isPressed();}
}
