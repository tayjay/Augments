package com.tayjay.augments.inventory;

import com.tayjay.augments.api.capabilities.IPlayerPartsProvider;
import com.tayjay.augments.api.item.IAugment;
import com.tayjay.augments.api.item.IBodyPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

/**
 * Created by tayjay on 2016-06-24.
 */
public class InventoryPlayerParts implements IItemHandlerModifiable
{

    public final EntityPlayer player;
    private final IItemHandlerModifiable compose;

    public InventoryPlayerParts(IPlayerPartsProvider provider, EntityPlayer player)
    {
        this.player = player;
        this.compose = (IItemHandlerModifiable)provider.getPartsInv();
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
        return compose.getStackInSlot(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
    {
        //TODO: Specify which slot body part can go into.
        if(!(stack.getItem() instanceof IBodyPart))
            return stack; //Can't put non BodyParts into inventory
        else
            return compose.insertItem(slot,stack,simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        //TODO: Make inventory only accessable if player is in Creative mode.
        return compose.extractItem(slot,amount,simulate);
    }
}
