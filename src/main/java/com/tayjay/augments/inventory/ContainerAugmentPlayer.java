package com.tayjay.augments.inventory;

import com.tayjay.augments.augment.interfaces.IAugment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjm_000 on 2016-01-24.
 */
public class ContainerAugmentPlayer extends Container
{
    private static final int ARMOR_START = InventoryAugmentPlayer.INV_SIZE, ARMOR_END = ARMOR_START+3,
            INV_START = ARMOR_END+1, INV_END = INV_START+26, HOTBAR_START = INV_END+1,
            HOTBAR_END = HOTBAR_START+8;
    public InventoryPlayer inv;

    public ContainerAugmentPlayer(EntityPlayer player, InventoryPlayer inventoryPlayer, InventoryAugmentPlayer inventoryCustom)
    {
        int i;
        this.inv = inventoryPlayer;

        // Add CUSTOM slots - we'll just add two for now, both of the same type.
        // Make a new Slot class for each different item type you want to add
        this.addSlotToContainer(new SlotAugment(inventoryCustom,inventoryPlayer.player, 0, 8, 8));
        this.addSlotToContainer(new SlotAugment(inventoryCustom,inventoryPlayer.player, 1, 8, 26));
        this.addSlotToContainer(new SlotAugment(inventoryCustom,inventoryPlayer.player, 2, 8, 44));
        this.addSlotToContainer(new SlotAugment(inventoryCustom,inventoryPlayer.player, 3, 8, 62));


        // Add vanilla PLAYER INVENTORY - just copied/pasted from vanilla classes
        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // Add ACTION BAR - just copied/pasted from vanilla classes
        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    /**
     * This should always return true, since custom inventory can be accessed from anywhere
     */
    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     * Basically the same as every other container I make, since I define the same constant indices for all of them
     */
    public ItemStack transferStackInSlot(EntityPlayer player, int par2)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            // Either armor slot or custom item slot was clicked
            if (par2 < INV_START)
            {
                // try to place in player inventory / action bar
                if (!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END + 1, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            // Item is in inventory / hotbar, try to place either in custom or armor slots
            else
            {
                // if item is our custom item
                if (itemstack1.getItem() instanceof IAugment)
                {
                    if (!this.mergeItemStack(itemstack1, 0, InventoryAugmentPlayer.INV_SIZE, false))
                    {
                        return null;
                    }
                }
                // if item is armor
                else if (itemstack1.getItem() instanceof ItemArmor)
                {
                    int type = ((ItemArmor) itemstack1.getItem()).armorType;
                    if (!this.mergeItemStack(itemstack1, ARMOR_START + type, ARMOR_START + type + 1, false))
                    {
                        return null;
                    }
                }
                // item in player's inventory, but not in action bar
                else if (par2 >= INV_START && par2 < HOTBAR_START)
                {
                    // place in action bar
                    if (!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_START + 1, false))
                    {
                        return null;
                    }
                }
                // item in action bar - place in player inventory
                else if (par2 >= HOTBAR_START && par2 < HOTBAR_END + 1)
                {
                    if (!this.mergeItemStack(itemstack1, INV_START, INV_END + 1, false))
                    {
                        return null;
                    }
                }
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack) null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }
}
