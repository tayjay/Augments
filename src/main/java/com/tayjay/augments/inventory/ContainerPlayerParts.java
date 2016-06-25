package com.tayjay.augments.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

/**
 * Created by tayjay on 2016-06-24.
 */
public class ContainerPlayerParts extends Container
{
    public final InventoryPlayerParts inventory;

    public ContainerPlayerParts(InventoryPlayer invPlayer, InventoryPlayerParts invBody)
    {
        this.inventory = invBody;

        //BodyPart Inventory
        /*
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 13; j++)
                this.addSlotToContainer(new SlotBodyPart(inventory, j + i * 13, 12 + j * 18, 5 + i * 18));
                */
        for(int i = 0 ; i<8;i++)
        {
            this.addSlotToContainer(new SlotBodyPart(inventory,i,20,5+i*18));
        }

        //Player Inventory
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 9; j++)
                this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 48 + j * 18, 152 + i * 18));

        //Player Hotbar
        for (int i = 0; i < 9; i++)
            this.addSlotToContainer(new Slot(invPlayer, i, 48 + i * 18, 210));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return playerIn.isCreative();
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

        if(index < inventory.getSlots())
        {
            if(!this.mergeItemStack(stack, inventory.getSlots(), this.inventorySlots.size(),true))
                return null;
            slot.onSlotChanged();
        }
        else if(!this.mergeItemStack(stack,0,inventory.getSlots(),true))
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
