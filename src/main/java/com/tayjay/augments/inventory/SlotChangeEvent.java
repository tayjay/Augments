package com.tayjay.augments.inventory;

import com.tayjay.augments.util.LogHelper;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjm_000 on 2016-01-24.
 */
public class SlotChangeEvent extends Event
{
    Slot slot;
    ItemStack stack;

    public SlotChangeEvent(Slot s)
    {
        this.slot = s;

        this.stack = s.getStack();
        if(slot!=null && stack!=null)
            LogHelper.info("Firing SlotChangeEvent! "+stack.getDisplayName());
    }

    public ItemStack getStack()
    {
        return stack;
    }

    public Slot getSlot()
    {
        return slot;
    }
}
