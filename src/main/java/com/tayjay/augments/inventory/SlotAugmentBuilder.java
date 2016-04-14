package com.tayjay.augments.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjm_000 on 2016-04-05.
 */
public class SlotAugmentBuilder extends Slot
{
    public SlotAugmentBuilder(IInventory inventory, int inventoryIndex, int x, int y)
    {
        super(inventory, inventoryIndex, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack)
    {
        return this.inventory.isItemValidForSlot(getSlotIndex(),itemStack);
    }
}
