package com.tayjay.augments.api.events;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-06-30.
 */
public interface IActivate
{

    KeyBinding getKey();
    void activate(ItemStack augment,ItemStack bodyPart, EntityPlayer player);
}
