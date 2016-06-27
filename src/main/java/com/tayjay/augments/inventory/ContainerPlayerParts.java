package com.tayjay.augments.inventory;

import com.google.common.collect.Lists;
import com.tayjay.augments.api.AugmentsAPI;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
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
    public HashMap<Integer, InventoryAugments> augments = new HashMap<Integer, InventoryAugments>();
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
        augments.clear();
        for(int i=0;i<invBody.getSlots();i++)
        {
            ItemStack current = inventory.getStackInSlot(i);
            if(current!=null && CapHelper.hasAugHolderCap(current))
                augments.put(i,new InventoryAugments(CapHelper.getAugHolderCap(current),current));
        }

        int i = 0;
        this.addSlotToContainer(new SlotBodyPart(inventory,i,48,5+i++*18, PartType.HEAD));
        //TODO: BRAINSSS!!!
        this.addSlotToContainer(new SlotBodyPart(inventory,i,48,5+i++*18,PartType.EYES));
        this.addSlotToContainer(new SlotBodyPart(inventory,i,48,5+i++*18,PartType.TORSO));
        this.addSlotToContainer(new SlotBodyPart(inventory,i,48,5+i++*18,PartType.ARM_LEFT));
        this.addSlotToContainer(new SlotBodyPart(inventory,i,48,5+i++*18,PartType.ARM_RIGHT));
        this.addSlotToContainer(new SlotBodyPart(inventory,i,48,5+i++*18,PartType.LEG_LEFT));
        this.addSlotToContainer(new SlotBodyPart(inventory,i,48,5+i++*18,PartType.LEG_RIGHT));
        this.addSlotToContainer(new SlotBodyPart(inventory,i,48,5+i++*18,PartType.POWER));


        //Player Inventory
        for(int k = 0; k < 3; k++)
            for(int j = 0; j < 9; j++)
                this.addSlotToContainer(new Slot(invPlayer, j + k * 9 + 9, 48 + j * 18, 152 + k * 18));

        //Player Hotbar
        for (int k = 0; k < 9; k++)
            this.addSlotToContainer(new Slot(invPlayer, k, 48 + k * 18, 210));

        //reloadAugmentSlots();
    }


    /*
    public void reloadAugmentSlots()
    {
        augments.clear();

        for(int i=0;i<inventory.getSlots();i++)
        {
            ItemStack current = inventory.getStackInSlot(i);
            if(current!=null && CapHelper.hasAugHolderCap(current))
                augments.put(i,new InventoryAugments(CapHelper.getAugHolderCap(current),current));
        }

        for(ArrayList<SlotToggleLocked> list : bodySlotToAugmentSlots.values())
        {
            for(SlotToggleLocked slot : list)
            {
                slot.setVisible(false);
                //slot.putStack(null);
            }
        }

        for(int i = 0 ; i<inventory.getSlots();i++)//Go to each BodyPart Slot
        {
            if(inventory.getStackInSlot(i)!=null)//If there is a body part
            {
                for (int j = 0; j < augments.get(i).getSlots(); j++)//Go through its augments
                {
                    try
                    {
                        if(bodySlotToAugmentSlots.get(i)!=null && bodySlotToAugmentSlots.get(i).get(j)!=null)
                        {
                            SlotToggleLocked slot = bodySlotToAugmentSlots.get(i).get(j);//Find the slot
                            if (augments.get(i).getStackInSlot(j) != null)//If there is an augment
                            {
                                slot.setVisible(true);
                                //slot.putStack(augments.get(i).getStackInSlot(j));
                            } else//If no augment
                            {
                                slot.setVisible(false);
                                //slot.putStack((ItemStack) null);
                            }
                        }
                    }catch(IndexOutOfBoundsException e){
                        LogHelper.error("IndexOutOfBounds in ContainerPlayerParts!");}



                }
            }
            else//No bodypart
            {
                if(augments.get(i)!=null)
                {
                    for (int j = 0; j < augments.get(i).getSlots(); j++)//Set all augments invisible
                    {
                        if (bodySlotToAugmentSlots.get(i) != null && bodySlotToAugmentSlots.get(i).get(j) != null)
                        {
                            bodySlotToAugmentSlots.get(i).get(j).setVisible(false);
                            //bodySlotToAugmentSlots.get(i).get(j).putStack((ItemStack) null);
                        }
                    }
                }
            }
        }
    }
    */



    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return playerIn.isCreative();
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
        //reloadAugmentSlots();
        super.detectAndSendChanges();

    }

    @Nullable
    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player)
    {
        //reloadAugmentSlots();
        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }
}
