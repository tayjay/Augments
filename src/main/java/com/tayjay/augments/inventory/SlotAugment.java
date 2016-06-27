package com.tayjay.augments.inventory;

import com.tayjay.augments.api.item.IAugment;
import com.tayjay.augments.client.gui.GuiPlayerParts;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by tayjay on 2016-06-25.
 */
public class SlotAugment extends SlotItemHandler
{
    public SlotAugment(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        //TODO: Also check that the Body Part being added to is right type!
        return stack.getItem() instanceof IAugment;
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn)
    {
        return !(Minecraft.getMinecraft().currentScreen instanceof GuiPlayerParts);
    }

    @Override
    public int getItemStackLimit(ItemStack stack)
    {
        return 1;
    }
}
