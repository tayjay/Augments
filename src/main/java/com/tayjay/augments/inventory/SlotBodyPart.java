package com.tayjay.augments.inventory;

import com.tayjay.augments.augment.interfaces.IBodyPart;
import com.tayjay.augments.handler.PlayerHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjm_000 on 2016-05-12.
 */
public class SlotBodyPart extends Slot
{
    EntityPlayer player;
    ItemStack stackOld;
    InventoryAugmentPlayer inv;

    public SlotBodyPart(IInventory inventory,EntityPlayer p, int slotIndex, int x, int y)
    {
        super(inventory, slotIndex, x, y);
        this.player = p;
        if(PlayerHandler.getPlayerAugmentInventory(p).getStackInSlot(slotIndex) !=null)
            stackOld = PlayerHandler.getPlayerAugmentInventory(p).getStackInSlot(slotIndex).copy();
        inv = (InventoryAugmentPlayer) inventory;
    }

    @Override
    public boolean isItemValid(ItemStack itemStack)
    {
        return itemStack.getItem() instanceof IBodyPart;
    }

}
