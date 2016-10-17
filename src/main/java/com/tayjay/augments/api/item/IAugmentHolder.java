package com.tayjay.augments.api.item;

import com.tayjay.augments.api.capabilities.IAugHolderProvider;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-06-25.
 * Items implementing this will be assigned a IAugHolderProvider Capability to store augments.
 */
@Deprecated
public interface IAugmentHolder
{
    /**
     * Get how many augments this holder can carry
     * @param stack Holder stack
     * @return      Augment capacity
     */
    int getHolderSize(ItemStack stack);

    /**
     * Get the augment holder provider. This is an unused method as I use the CapHelper class to get augments instead.
     * In an API implementation this may be more useful however.
     * @param stack Holder stack
     * @return      AugmentHolder Capability
     */
    IAugHolderProvider getAugmentHolder(ItemStack stack);
}
