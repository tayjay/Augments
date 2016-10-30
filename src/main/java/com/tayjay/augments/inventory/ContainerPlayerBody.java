package com.tayjay.augments.inventory;

import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

/**
 * Created by tayjay on 2016-10-16.
 */
public class ContainerPlayerBody extends Container
{
    public final EntityPlayer player;
    public final InventoryPlayerParts inventory;
    public final InventoryPlayerAugments inventoryAugments;

    public ContainerPlayerBody(InventoryPlayer invPlayer, InventoryPlayerParts invBody, InventoryPlayerAugments invAugments)
    {
        this.inventory = invBody;
        this.inventoryAugments = invAugments;
        this.player = invPlayer.player;

        setupSlots(invPlayer,invBody,invAugments);
    }

    private void setupSlots(InventoryPlayer invPlayer, InventoryPlayerParts invBody,InventoryPlayerAugments invAugments)
    {
        //Add bodypart slots**************************
        /*int i = 0;
        for(PartType type : PartType.values())
        {
            this.addSlotToContainer(new SlotBodyPart(inventory,i,48,5+i++*18, type));
        }*/
        this.addSlotToContainer(new SlotBodyPart(inventory,0,48,5+1*18,PartType.HEAD));
        this.addSlotToContainer(new SlotBodyPart(inventory,1,48,5+2*18,PartType.EYES));
        this.addSlotToContainer(new SlotBodyPart(inventory,2,48,5+3*18,PartType.TORSO));
        this.addSlotToContainer(new SlotBodyPart(inventory,3,48,5+4*18,PartType.ARM));
        this.addSlotToContainer(new SlotBodyPart(inventory,4,48,5+5*18,PartType.ARM));
        this.addSlotToContainer(new SlotBodyPart(inventory,5,48,5+6*18,PartType.LEG));
        this.addSlotToContainer(new SlotBodyPart(inventory,6,48,5+7*18,PartType.LEG));

        /*//Add augment slots**********************************
        for(int j = 0;j<CapHelper.getPlayerBodyCap(player).getAugmentCapacity();j++)
            this.addSlotToContainer(new SlotAugment(inventoryAugments,j,100,18*(j+1)));*/

        for(int j = 0;j<inventoryAugments.getSlots();j++)
            this.addSlotToContainer(new SlotAugment(inventoryAugments,j,100,18*(j+1)));

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
        //Syncing for current active augment, consider removing
        if(!player.worldObj.isRemote)
        {
            CapHelper.getPlayerBodyCap(player).sync((EntityPlayerMP) player);
            //CapHelper.getPlayerDataCap(player).updateCurrentAugment();
            CapHelper.getPlayerDataCap(player).sync((EntityPlayerMP) player);


        }
        else
        {
            //CapHelper.getPlayerDataCap(player).updateCurrentAugment();
        }
        super.detectAndSendChanges();

    }



    @Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        IItemHandler augments = CapHelper.getPlayerBodyCap(playerIn).getAugments();
        int augmentCap = CapHelper.getPlayerBodyCap(playerIn).getAugmentCapacity();
        for(int i = augmentCap; i<CapHelper.getPlayerBodyCap(playerIn).getAugments().getSlots();i++)
        {
            if(augments.getStackInSlot(i)!=null)
            {
                playerIn.dropItem(augments.getStackInSlot(i), false).setPickupDelay(0);
                augments.extractItem(i,1,false);
            }
        }
    }
}
