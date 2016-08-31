package com.tayjay.augments.inventory;

import com.google.common.collect.Lists;
import com.tayjay.augments.api.AugmentsAPI;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tayjay on 2016-06-24.
 */
public class ContainerPlayerParts extends Container
{
    public final EntityPlayer player;
    public final InventoryPlayerParts inventory;
    //public HashMap<Integer,ArrayList<SlotToggleLocked>> bodySlotToAugmentSlots = new HashMap<Integer, ArrayList<SlotToggleLocked>>();
    public final int DEFAULT_AUGMENT_SLOTS = 5;

    public ContainerPlayerParts(InventoryPlayer invPlayer, InventoryPlayerParts invBody)
    {
        this.inventory = invBody;
        this.player = invPlayer.player;

        setupSlots(invPlayer,invBody);


    }

    private void setupSlots(InventoryPlayer invPlayer, InventoryPlayerParts invBody)
    {
        int i = 0;
        for(PartType type : PartType.values())
        {
            if(type == PartType.POWER)
            {
                this.addSlotToContainer(new SlotBodyPart(inventory, i, 150, 5 + (i - 1) * 18, type));
                continue;
            }
            this.addSlotToContainer(new SlotBodyPart(inventory,i,48,5+i++*18, type));
        }


        //Player Inventory
        for(int k = 0; k < 3; k++)
            for(int j = 0; j < 9; j++)
                this.addSlotToContainer(new Slot(invPlayer, j + k * 9 + 9, 48 + j * 18, 152 + k * 18));

        //Player Hotbar
        for (int k = 0; k < 9; k++)
            this.addSlotToContainer(new Slot(invPlayer, k, 48 + k * 18, 210));
    }



    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        //return playerIn.isCreative();TODO: Restrict use later
        return true;
    }

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        Slot slot = this.getSlot(index);

        if(slot == null || !slot.getHasStack())
            return null;

        ItemStack stack = slot.getStack();
        ItemStack newStack = stack.copy();

        if(index < inventory.getSlots())
        {
            if(!this.mergeItemStack(stack, inventory.getSlots(), this.inventorySlots.size(),true))
                return null;
            slot.onSlotChanged();
        }
        else if(!this.mergeItemStack(stack,0,inventory.getSlots(),true))
            return null;

        if(stack.stackSize == 0)
            slot.putStack(null);
        else
            slot.onSlotChanged();

        slot.onPickupFromSlot(playerIn,newStack);
        //reloadAugmentSlots();
        return newStack;
    }

    @Override
    public void detectAndSendChanges()
    {
        if(!player.worldObj.isRemote)
        {
            CapHelper.getPlayerPartsCap(player).sync((EntityPlayerMP) player);
            CapHelper.getPlayerDataCap(player).sync((EntityPlayerMP) player);
        }
        super.detectAndSendChanges();

    }

    @Nullable
    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player)
    {
        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }


}
