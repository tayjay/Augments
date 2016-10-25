package com.tayjay.augments.inventory;

import com.tayjay.augments.api.item.IBodyPart;
import com.tayjay.augments.api.item.PartType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by tayjay on 2016-06-24.
 */
public class SlotBodyPart extends SlotItemHandler
{
    PartType validType;
    public SlotBodyPart(IItemHandler itemHandler, int index, int xPosition, int yPosition, PartType validType)
    {
        super(itemHandler, index, xPosition, yPosition);
        this.validType = validType;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return stack.getItem() instanceof IBodyPart && ((IBodyPart) stack.getItem()).getPartType(stack)==this.validType;
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

    public PartType getValidType()
    {
        return validType;
    }

    /*public boolean isFirstOfType()
    {
        if(validType!=PartType.ARM && validType!=PartType.LEG)
            return true;
        InventoryPlayerParts inv = (InventoryPlayerParts) inventory;
        inv.
    }*/
}
