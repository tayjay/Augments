package com.tayjay.augments.inventory;

import com.tayjay.augments.api.capabilities.IPlayerBodyProvider;
import com.tayjay.augments.api.events.IActivate;
import com.tayjay.augments.api.item.IAugment;
import com.tayjay.augments.api.item.IBodyPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

/**
 * Created by tayjay on 2016-10-15.
 */
public class InventoryPlayerAugments implements IItemHandlerModifiable
{

    public final EntityPlayer player;
    private final IItemHandlerModifiable compose;
    private final IPlayerBodyProvider provider;

    public InventoryPlayerAugments(IPlayerBodyProvider provider, EntityPlayer player)
    {
        this.player = player;
        this.compose = (IItemHandlerModifiable) provider.getAugments();
        this.provider = provider;
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack)
    {
        compose.setStackInSlot(slot, stack);
    }

    @Override
    public int getSlots()
    {
        return compose.getSlots();
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        if (compose.getStackInSlot(slot) != null)
            return compose.getStackInSlot(slot);
        return null;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
    {
        if (!(stack.getItem() instanceof IAugment))
            return stack;
        else
        {
            return compose.insertItem(slot, stack, simulate);
        }
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        //TODO: Make inventory only accessable if player is in Creative mode.
        return compose.extractItem(slot, amount, simulate);
    }
}




