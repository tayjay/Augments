package com.tayjay.augments.inventory;

import com.tayjay.augments.Augments;
import com.tayjay.augments.api.item.IAugment;
import com.tayjay.augments.client.gui.GuiPlayerParts;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by tayjay on 2016-06-25.
 */
public class SlotAugment extends SlotItemHandler
{
    InventoryPlayerAugments invAugments;
    public SlotAugment(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
        invAugments = (InventoryPlayerAugments)itemHandler;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        if(getSlotIndex()>= Augments.proxy.getClientPlayerParts().getAugmentCapacity())
            return false;
        return stack.getItem() instanceof IAugment;//Also check for within max number of augments
    }

    @Override
    public boolean isHere(IInventory inv, int slotIn)
    {
        //Hide slots that aren't accessible at player's augment cap.
        return slotIn<CapHelper.getPlayerBodyCap(Minecraft.getMinecraft().thePlayer).getAugmentCapacity();
    }

    @Override
    public boolean canBeHovered()
    {
        return getSlotIndex()<CapHelper.getPlayerBodyCap(Minecraft.getMinecraft().thePlayer).getAugmentCapacity();
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
