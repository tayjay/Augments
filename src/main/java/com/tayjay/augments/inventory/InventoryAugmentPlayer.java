package com.tayjay.augments.inventory;

import com.tayjay.augments.AugmentsCore;
import com.tayjay.augments.augment.interfaces.IAugment;
import com.tayjay.augments.network.MessageSyncPlayerAugments;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.network.PacketSyncPlayerAugments;
import com.tayjay.augments.util.LogHelper;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.lang.ref.WeakReference;

/**
 * Created by tayjm_000 on 2016-01-24.
 */
public class InventoryAugmentPlayer implements IInventory
{
    /** The name your custom inventory will display in the GUI, possibly just "Inventory" */
    private final String name = "Player Augments";

    /** The key used to store and retrieve the inventory from NBT */
    private final String tagName = "playerAugInvTag";

    /** Define the inventory size here for easy reference */
    // This is also the place to define which slot is which if you have different types,
    // for example SLOT_SHIELD = 0, SLOT_AMULET = 1;
    public static final int INV_SIZE = 4;

    /** Inventory's size must be same as number of slots you add to the Container class */
    public ItemStack[] inventory = new ItemStack[INV_SIZE];

    private Container eventHandler;

    public WeakReference<EntityPlayer> player;

    public InventoryAugmentPlayer()
    {
        // don't need anything here!
    }

    public InventoryAugmentPlayer(EntityPlayer player)
    {
        this.player = new WeakReference<EntityPlayer>(player);
    }

    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }

    public Container getEventHandler()
    {
        return eventHandler;
    }

    public void setEventHandler(Container eventHandler)
    {
        this.eventHandler = eventHandler;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        if (this.inventory[slot] != null) {
            ItemStack itemstack;

            if (this.inventory[slot].stackSize <= amount) {
                itemstack = this.inventory[slot];

                if (itemstack != null && itemstack.getItem() instanceof IAugment) {
                    ((IAugment) itemstack.getItem()).onAdd(itemstack,
                            player.get());
                }

                this.inventory[slot] = null;

                if (eventHandler != null)
                    this.eventHandler.onCraftMatrixChanged(this);
                syncSlotToClients(slot);
                return itemstack;
            } else {
                itemstack = this.inventory[slot].splitStack(amount);

                if (itemstack != null && itemstack.getItem() instanceof IAugment) {
                    ((IAugment) itemstack.getItem()).onRemove(itemstack,
                            player.get());
                }

                if (this.inventory[slot].stackSize == 0) {
                    this.inventory[slot] = null;
                }

                if (eventHandler != null)
                    this.eventHandler.onCraftMatrixChanged(this);
                syncSlotToClients(slot);
                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        ItemStack stack = getStackInSlot(slot);
        setInventorySlotContents(slot, null);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack)
    {
        this.inventory[slot] = itemstack;

        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
        {
            itemstack.stackSize = this.getInventoryStackLimit();
        }
        syncSlotToClients(slot);
        this.onInventoryChanged();
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


    /**
     * Our custom slots are similar to armor - only one item per slot
     */
    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public void markDirty()
    {

    }


    public void onInventoryChanged()
    {
        for (int i = 0; i < getSizeInventory(); ++i)
        {
            if (getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0) {
                inventory[i] = null;
            }
        }
    }



    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
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
     * This method doesn't seem to do what it claims to do, as
     * items can still be left-clicked and placed in the inventory
     * even when this returns false
     */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack)
    {
        // If you have different kinds of slots, then check them here:
        // if (slot == SLOT_SHIELD && itemstack.getItem() instanceof ItemShield) return true;

        // For now, only ItemUseMana items can be stored in these slots
        return itemstack.getItem() instanceof IAugment;
    }


    public void writeToNBT(NBTTagCompound compound)
    {
        NBTTagList items = new NBTTagList();

        for (int i = 0; i < getSizeInventory(); ++i)
        {
            if (getStackInSlot(i) != null)
            {
                NBTTagCompound item = new NBTTagCompound();
                item.setByte("Slot", (byte) i);
                getStackInSlot(i).writeToNBT(item);
                items.appendTag(item);
            }
        }

        // We're storing our items in a custom tag list using our 'tagName' from above
        // to prevent potential conflicts
        compound.setTag(tagName, items);
    }

    public void readFromNBT(NBTTagCompound compound) {
        // now you must include the NBTBase type ID when getting the list; NBTTagCompound's ID is 10
        NBTTagList items = compound.getTagList(tagName, compound.getId());
        for (int i = 0; i < items.tagCount(); ++i) {
            // tagAt(int) has changed to getCompoundTagAt(int)
            NBTTagCompound item = items.getCompoundTagAt(i);
            byte slot = item.getByte("Slot");
            if (slot >= 0 && slot < getSizeInventory()) {
                inventory[slot] = ItemStack.loadItemStackFromNBT(item);
            }
        }
    }

    public void syncSlotToClients(int slot)
    {
        try
        {
            if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
            {
                //LogHelper.info("Sending Augments from"+player.get()+" to all.");
                NetworkHandler.INSTANCE.sendToAll(new PacketSyncPlayerAugments(player.get(),slot));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
