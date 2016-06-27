package com.tayjay.augments.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * Created by tayjay on 2016-06-25.
 */
public class ContainerAugments extends Container
{
    public final InventoryAugments inventoryAugments;

    public ContainerAugments(InventoryPlayer invPlayer, InventoryAugments invAugments)
    {
        this.inventoryAugments = invAugments;

        for(int i = 0 ; i<invAugments.getSlots();i++)
        {
            this.addSlotToContainer(new SlotAugment(inventoryAugments,i,50+i*18,65));
        }
        //Player Inventory
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 9; j++)
                this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 19 + j * 18, 100 + i * 18));

        //Player Hotbar
        for (int i = 0; i < 9; i++)
            this.addSlotToContainer(new Slot(invPlayer, i, 19 + i * 18, 158));

    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        Slot slot = this.getSlot(index);

        if(slot == null || !slot.getHasStack())
            return null;

        ItemStack stack = slot.getStack();
        ItemStack newStack = stack.copy();

        if(index < inventoryAugments.getSlots())
        {
            if(!this.mergeItemStack(stack, inventoryAugments.getSlots(), this.inventorySlots.size(),true))
                return null;
            slot.onSlotChanged();
        }
        else if(!this.mergeItemStack(stack,0,inventoryAugments.getSlots(),true))
            return null;

        if(stack.stackSize == 0)
            slot.putStack(null);
        else
            slot.onSlotChanged();

        slot.onPickupFromSlot(playerIn,newStack);
        return newStack;
    }

    @Nullable
    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player)
    {
        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }
}
