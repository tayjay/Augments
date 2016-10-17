package com.tayjay.augments.api.events;

import com.tayjay.augments.item.augments.ItemAugment;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;

/**
 * Created by tayjay on 2016-10-16.
 */
public interface IPickupXP
{
    void onPickupXP(ItemStack augment, PlayerPickupXpEvent event);
}
