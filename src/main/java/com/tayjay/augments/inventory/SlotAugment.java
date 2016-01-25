package com.tayjay.augments.inventory;

import com.tayjay.augments.augment.AugmentPotionEffect;
import com.tayjay.augments.augment.event.AugmentChangeEvent;
import com.tayjay.augments.augment.interfaces.IAugment;
import com.tayjay.augments.augment.interfaces.IPlayerAugment;
import com.tayjay.augments.properties.PlayerAugmentProperties;
import com.tayjay.augments.util.ChatHelper;
import com.tayjay.augments.util.LogHelper;
import cpw.mods.fml.common.eventhandler.EventBus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by tayjm_000 on 2016-01-24.
 */
public class SlotAugment extends Slot
{
    EntityPlayer player;
    ItemStack stackOld = null;
    public SlotAugment(IInventory inventory,EntityPlayer p, int slotIndex, int x, int y)
    {
        super(inventory, slotIndex, x, y);
        this.player = p;
        if(PlayerAugmentProperties.get(p).inventory.getStackInSlot(slotIndex)!=null)
            stackOld = PlayerAugmentProperties.get(p).inventory.getStackInSlot(slotIndex).copy();
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots
     * (and now also not always true for our custom inventory slots)
     */
    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return stack.getItem() instanceof IAugment;
    }

    @Override
    public void onSlotChanged()
    {
        super.onSlotChanged();
        MinecraftForge.EVENT_BUS.post(new SlotChangeEvent(this));
        if(getStack()!=null)
        {
            stackOld = getStack();

            AugmentPotionEffect augment = (AugmentPotionEffect) getStack().getItem();
            MinecraftForge.EVENT_BUS.post(new AugmentChangeEvent.Add(player, augment));
            augment.onAdd(getStack(),player);
        }
        else if(stackOld!=null)
        {
            AugmentPotionEffect augment = (AugmentPotionEffect) stackOld.getItem();
            MinecraftForge.EVENT_BUS.post(new AugmentChangeEvent.Add(player,augment));
            augment.onRemove(stackOld,player);
            stackOld=null;
        }
    }
}
