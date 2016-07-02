package com.tayjay.augments.api.item;

import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-06-26.
 * An Item implementing this will allow it to store power for the player to use.
 */
public interface IEnergySupply //TODO: Change name to charge? Makes more sense I suppose.
{
    int maxEnergy(ItemStack stack);

    int currentEnergy(ItemStack stack);
}
