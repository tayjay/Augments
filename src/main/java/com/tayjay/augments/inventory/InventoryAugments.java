package com.tayjay.augments.inventory;

import com.tayjay.augments.Augments;
import com.tayjay.augments.api.capabilities.IAugHolderProvider;
import com.tayjay.augments.api.item.IAugment;
import com.tayjay.augments.handler.GuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

/**
 * Created by tayjay on 2016-06-25.
 */
public class InventoryAugments implements IItemHandlerModifiable
{
    public final ItemStack AUGMENT_HOLDER;
    private final IItemHandlerModifiable compose;

    public InventoryAugments(IAugHolderProvider provider, ItemStack stack)
    {
        AUGMENT_HOLDER = stack;
        this.compose = (IItemHandlerModifiable)provider.getAugments();
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack)
    {
        compose.setStackInSlot(slot,stack);
    }



    @Override
    public int getSlots()
    {
        return compose.getSlots();
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        try
        {
            return compose.getStackInSlot(slot);
        }catch (Exception e)
        {

        }
        return null;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
    {
        if(!(stack.getItem() instanceof IAugment))
            return stack; //Only Augments allowed
        else
            return compose.insertItem(slot,stack,simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        return compose.extractItem(slot,amount,simulate);
    }
}
