package com.tayjay.augments.inventory;

import com.tayjay.augments.augment.interfaces.IAugment;
import com.tayjay.augments.augment.interfaces.IBodyPart;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.network.PacketSyncPlayerAugments;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.lang.ref.WeakReference;

/**
 * Created by TayJay on 2016-01-24.<br/>
 * Stores all the body parts of the player.<br/>
 * As well as the augments in those body parts.
 */
public class InventoryAugmentPlayer implements IInventory
{
    /** The name your custom inventory will display in the GUI, possibly just "Inventory" */
    private final String name = "Player Augments";

    /** The key used to store and retrieve the inventory from NBT */
    private final String tagName = "playerAugInvTag";

    /** Define the inventory size here for easy reference */

    public static final int BODY_SIZE = 9; //Number of body parts for the player.
    public static final int SLOT_EYES = 1;
    public static final int INV_SIZE = BODY_SIZE;


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
                InventoryBodyPart bodyInv = new InventoryBodyPart(itemstack);
                for(ItemStack augStack : bodyInv.getAugmentStacks())
                {
                    if(augStack !=null && augStack.getItem() instanceof IAugment)
                    {
                        ((IAugment) augStack.getItem()).onAdd(augStack,
                                player.get());
                    }
                }
                this.inventory[slot] = null;

                if (eventHandler != null)
                    this.eventHandler.onCraftMatrixChanged(this);
                syncSlotToClients(slot);
                markDirty();
                return itemstack;
            } else {
                itemstack = this.inventory[slot].splitStack(amount);

                InventoryBodyPart bodyInv = new InventoryBodyPart(itemstack);
                for(ItemStack augStack : bodyInv.getAugmentStacks())
                {
                    if(augStack !=null && augStack.getItem() instanceof IAugment)
                    {
                        ((IAugment) augStack.getItem()).onRemove(augStack,
                                player.get());
                    }
                }

                if (this.inventory[slot].stackSize == 0) {
                    this.inventory[slot] = null;
                }

                if (eventHandler != null)
                    this.eventHandler.onCraftMatrixChanged(this);
                //syncSlotToClients(slot);
                markDirty();
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
        //syncSlotToClients(slot);
        markDirty();
        //this.onInventoryChanged();
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
        for(int i = 0; i <  getSizeInventory();++i)
        {
            if(getStackInSlot(i)!=null && getStackInSlot(i).stackSize == 0)
            {
                inventory[i] = null;
            }
            if(getStackInSlot(i)!=null && getStackInSlot(i).getItem() instanceof IBodyPart) //Should be by definition but just to be safe.
            {
                InventoryBodyPart inv = new InventoryBodyPart(getStackInSlot(i));
                //inv.getAugmentsBasic();
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
        return itemstack.getItem() instanceof IBodyPart;
        // TODO: 2016-05-27 Check for type of bodypart added(Eyes,torso...)
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
