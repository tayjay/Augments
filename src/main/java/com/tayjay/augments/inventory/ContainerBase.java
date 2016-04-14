package com.tayjay.augments.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjm_000 on 2016-04-05.
 */
public abstract class ContainerBase extends Container
{
    /**
     * Add the player's inventory to the GUI.
     *
     * @param playerInventory   The Player's Inventory
     * @param x                 X location on GUI
     * @param y                 Y location on GUI
     */
    protected void addPlayerSlots(InventoryPlayer playerInventory, int x, int y){
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, x + j * 18, y + i * 18));
            }
        }

        for(int i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(playerInventory, i, x + i * 18, y + 58));
        }
    }
    /*
    protected void addPlayerSlots(InventoryPlayer playerInventory, int x, int y)
{
    //Player's Inventory 3x9
    for (int i = 0; i < 3; ++i)
    {
        for (int j = 0; j < 9; ++j)
        {
            this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, x + j * 18, y + i * 18));
        }
    }

    //Player's Hotbar 1x9
    for (int i = 0; i < 9; ++i)
    {
        this.addSlotToContainer(new Slot(playerInventory, x + i, x + i * 18, y + 58));
    }
}
*/

    //JUST PUT HERE TO TEST STUFF OUT
    /**
     * merges provided ItemStack with the first avaliable one in the container/player inventory
     */
    protected boolean mergeItemStack(ItemStack itemStack, int startIndex, int endIndex, boolean isPlayerInv)
    {
        boolean flag1 = false;
        int k = startIndex;

        if (isPlayerInv)
        {
            k = endIndex - 1;
        }

        Slot slot;
        ItemStack itemstack1;

        if (itemStack.isStackable())
        {
            while (itemStack.stackSize > 0 && (!isPlayerInv && k < endIndex || isPlayerInv && k >= startIndex))
            {
                slot = (Slot)this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 != null && itemstack1.getItem() == itemStack.getItem() && (!itemStack.getHasSubtypes() || itemStack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(itemStack, itemstack1))
                {
                    int l = itemstack1.stackSize + itemStack.stackSize;

                    if (l <= itemStack.getMaxStackSize())
                    {
                        itemStack.stackSize = 0;
                        itemstack1.stackSize = l;
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                    else if (itemstack1.stackSize < itemStack.getMaxStackSize())
                    {
                        itemStack.stackSize -= itemStack.getMaxStackSize() - itemstack1.stackSize;
                        itemstack1.stackSize = itemStack.getMaxStackSize();
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                }

                if (isPlayerInv)
                {
                    --k;
                }
                else
                {
                    ++k;
                }
            }
        }

        if (itemStack.stackSize > 0)
        {
            if (isPlayerInv)
            {
                k = endIndex - 1;
            }
            else
            {
                k = startIndex;
            }

            while (!isPlayerInv && k < endIndex || isPlayerInv && k >= startIndex)
            {
                slot = (Slot)this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 == null)
                {
                    slot.putStack(itemStack.copy());
                    slot.onSlotChanged();
                    itemStack.stackSize = 0;
                    flag1 = true;
                    break;
                }

                if (isPlayerInv)
                {
                    --k;
                }
                else
                {
                    ++k;
                }
            }
        }

        return flag1;
    }
}
