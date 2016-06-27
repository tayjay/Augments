package com.tayjay.augments.inventory;

import com.tayjay.augments.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by tayjay on 2016-06-26.
 * A slot that can be toggled visible and cannot be changed.
 */
public class SlotToggleLocked extends SlotItemHandler
{
    private boolean visible;
    public SlotToggleLocked(IItemHandler itemHandler, int index, int xPosition, int yPosition,boolean visible)
    {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return false;
    }

    public boolean isVisible()
    {
        return visible;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }

    @Override
    public void putStack(ItemStack stack)
    {
        try
        {
            super.putStack(stack);
        }catch (Exception e)
        {
            LogHelper.error("Error in putStack SlotToggleLocked!");
        }
    }

    @Override
    public ItemStack getStack()
    {
        if(visible)
            return super.getStack();
        return null;
    }

    @Override
    public boolean isHere(IInventory inv, int slotIn)
    {
        return visible;
    }

    @Override
    public int getItemStackLimit(ItemStack stack)
    {
        return 1;
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn)
    {
        return false;
    }

    @Override
    public boolean canBeHovered()
    {
        return visible;
    }

}
