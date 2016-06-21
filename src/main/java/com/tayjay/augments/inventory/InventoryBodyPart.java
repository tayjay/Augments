package com.tayjay.augments.inventory;

import com.tayjay.augments.augment.interfaces.IAugment;
import com.tayjay.augments.lib.Tags;
import com.tayjay.augments.util.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;

/**
 * Created by tayjay on 2016-05-27.
 */
public class InventoryBodyPart implements IInventory
{
    private String name = "Inventory Body Part";
    public final ItemStack invItem;
    public static final int INV_MAX_SIZE = 5;
    public int invSize;
    private ItemStack[] inventory;

    public InventoryBodyPart(ItemStack stack)
    {
        invItem = stack;
        invSize = NBTHelper.getInt(invItem, Tags.BodyPart.INVENTORY_SIZE);
        invSize = invSize <= INV_MAX_SIZE ? invSize : INV_MAX_SIZE;
        inventory = new ItemStack[invSize];
        if(stack!=null)
            readFromNBT(stack.getTagCompound());
        else
            readFromNBT(new NBTTagCompound());
    }

    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        ItemStack stack = getStackInSlot(slot);
        if(stack != null)
        {
            if(stack.stackSize > amount)
            {
                stack = stack.splitStack(amount);
                // Don't forget this line or your inventory will not be saved!
                markDirty();
            }
            else
            {
                // this method also calls onInventoryChanged, so we don't need to call it again
                setInventorySlotContents(slot, null);
            }
        }
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        ItemStack stack = getStackInSlot(slot);
        setInventorySlotContents(slot, null);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        inventory[slot] = stack;

        if (stack != null && stack.stackSize > getInventoryStackLimit())
        {
            stack.stackSize = getInventoryStackLimit();
        }

        // Don't forget this line or your inventory will not be saved!
        markDirty();
    }

    @Override
    public String getInventoryName()
    {
        return name;
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return true;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public void markDirty()
    {
        for(int i = 0; i <  getSizeInventory();++i)
        {
            if(getStackInSlot(i)!=null && getStackInSlot(i).stackSize == 0)
            {
                inventory[i] = null;
            }
        }
        writeToNBT(invItem.getTagCompound());
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return true;
    }

    @Override
    public void openInventory()
    {

    }

    @Override
    public void closeInventory()
    {

    }

    /**
     * This Inventory will only hold Augments
     * @param slot
     * @param stack
     * @return
     */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        return stack.getItem() instanceof IAugment && ((IAugment)invItem.getItem()).canAdd(stack,invItem);
    }

    /**
     * A custom method to read our inventory from an ItemStack's NBT compound
     */
    public void readFromNBT(NBTTagCompound compound)
    {
        // Gets the custom taglist we wrote to this compound, if any
        // 1.7.2+ change to compound.getTagList("ItemInventory", Constants.NBT.TAG_COMPOUND);
        NBTTagList items = compound.getTagList(Tags.BodyPart.INVENTORY_TAG, Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < items.tagCount(); ++i)
        {
            // 1.7.2+ change to items.getCompoundTagAt(i)
            NBTTagCompound item = items.getCompoundTagAt(i);
            int slot = item.getInteger("Slot");

            // Just double-checking that the saved slot index is within our inventory array bounds
            if (slot >= 0 && slot < getSizeInventory()) {
                inventory[slot] = ItemStack.loadItemStackFromNBT(item);
            }
        }
    }

    /**
     * A custom method to write our inventory to an ItemStack's NBT compound
     */
    public void writeToNBT(NBTTagCompound tagcompound)
    {
        // Create a new NBT Tag List to store itemstacks as NBT Tags
        NBTTagList items = new NBTTagList();

        for (int i = 0; i < getSizeInventory(); ++i)
        {
            // Only write stacks that contain items
            if (getStackInSlot(i) != null)
            {
                // Make a new NBT Tag Compound to write the itemstack and slot index to
                NBTTagCompound item = new NBTTagCompound();
                item.setInteger("Slot", i);
                // Writes the itemstack in slot(i) to the Tag Compound we just made
                getStackInSlot(i).writeToNBT(item);

                // add the tag compound to our tag list
                items.appendTag(item);
            }
        }
        // Add the TagList to the ItemStack's Tag Compound with the name "inventory"
        tagcompound.setTag(Tags.BodyPart.INVENTORY_TAG, items);
    }

    public IAugment[] getAugmentsBasic()
    {
        ArrayList<IAugment> list = new ArrayList<IAugment>(inventory.length);
        for(ItemStack stack : inventory)
        {
            if(stack !=null)
                list.add((IAugment)stack.getItem());
        }
        return (IAugment[])list.toArray();
    }

    public ItemStack[] getAugmentStacks()
    {
        return inventory;
    }
}
