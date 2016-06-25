package com.tayjay.augments.inventory;

import com.tayjay.augments.api.item.IBodyPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by tayjay on 2016-06-24.
 */
public class SlotBodyPart extends SlotItemHandler
{
    public SlotBodyPart(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        //TODO: Also check for which slot it is in. Better safe than sorry.
        return stack.getItem() instanceof IBodyPart;
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn)
    {
        return true;
    }

    @Override
    public int getItemStackLimit(ItemStack stack)
    {
        return 1;
    }


}
