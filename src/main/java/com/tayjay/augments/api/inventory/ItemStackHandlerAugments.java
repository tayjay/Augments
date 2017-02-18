package com.tayjay.augments.api.inventory;

import com.tayjay.augments.api.item.IAugment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Created by tayjay on 2017-02-10.
 */
public class ItemStackHandlerAugments extends ItemStackHandler
{
    EntityPlayer player;
    public ItemStackHandlerAugments(int size,EntityPlayer player)
    {
        super(size);
        this.player = player;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
    {
        System.out.println("Inserting " + stack + " into ItemStackHandler on "+this.player.getName());
        if (stack.getItem() instanceof IAugment &&!simulate)
        {
            //((IAugment) stack.getItem()).onAddItem(stack,this.player);
        }
        return super.insertItem(slot, stack, simulate);
    }



    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        System.out.println("Extracting " + stacks[slot] + " from ItemStackHandler on "+this.player.getName());
        if (getStackInSlot(slot).getItem() instanceof IAugment && !simulate)
        {
            //((IAugment) getStackInSlot(slot).getItem()).onRemoveItem(getStackInSlot(slot),this.player);
        }
        return super.extractItem(slot, amount, simulate);
    }
}
