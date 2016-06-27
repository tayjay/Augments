package com.tayjay.augments.api.events;

import com.tayjay.augments.api.capabilities.IAugHolderProvider;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-06-25.
 * Items implementing this will be assigned a IAugHolderProvider Capability to store augments.
 */
public interface IAugmentHolder
{
    int getHolderSize(ItemStack stack);

    IAugHolderProvider getProvider(ItemStack stack);
}
