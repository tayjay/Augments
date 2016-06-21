package com.tayjay.augments.tileentity;

import com.tayjay.augments.util.ChatHelper;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * Created by tayjm_000 on 2016-04-05.
 */
public class TileEntityAugmentBuilder extends TileEntityAug implements ISidedInventory
{
    private final int TEMPLATE_SLOT =0,
                        DNA_SLOT = 1,
                            BASE_SLOT = 2,
                                PRIMARY_SLOT = 3,
                                    SECONDARY_SLOT = 4,
                                        TRINARY_SLOT = 5,
                                            ADDON_SLOT = 6,
                                                OUTPUT_SLOT = 7;
    private final int INVENTORY_SIZE = 7;
    private ItemStack[] inventory = new ItemStack[INVENTORY_SIZE];
    @Override
    protected void writeToPacket(ByteBuf buf)
    {
        if(inventory!=null)
        {
            for(ItemStack stack : inventory)
            {
                ByteBufUtils.writeItemStack(buf,stack);
            }
        }
    }

    @Override
    public void readFromPacket(ByteBuf buf)
    {
        if(inventory!=null)
        {
            for(int i = 0;i<inventory.length;i++)
            {
                inventory[i] = ByteBufUtils.readItemStack(buf);
            }
            worldObj.markBlockRangeForRenderUpdate(xCoord,yCoord,zCoord,xCoord,yCoord,zCoord);
        }
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int p_94128_1_)
    {
        return new int[0];
    }

    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        switch (slot)
        {
            case TEMPLATE_SLOT:
                break;
            case DNA_SLOT:
                break;
            case BASE_SLOT:
                break;
            case PRIMARY_SLOT:
                break;
            case SECONDARY_SLOT:
                break;
            case TRINARY_SLOT:
                break;
            case ADDON_SLOT:
                break;
            case OUTPUT_SLOT:
                return false;
        }
        return false;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemStack, int side)
    {
        return isItemValidForSlot(slot,itemStack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemStack, int side)
    {
        return true;
    }

    @Override
    public int getSizeInventory()
    {
        return INVENTORY_SIZE;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        if (this.inventory[slot] != null)
        {
            ItemStack itemstack;

            if (this.inventory[slot].stackSize <= amount)
            {
                itemstack = this.inventory[slot];
                setInventorySlotContents(slot, null);
                //this.camoStacks[slot] = null;
                this.markDirty();
                return itemstack;
            } else
            {
                itemstack = this.inventory[slot].splitStack(amount);

                if (this.inventory[slot].stackSize == 0)
                {
                    setInventorySlotContents(slot, null);
                }

                this.markDirty();
                return itemstack;
            }
        } else
        {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_)
    {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        inventory[slot] = stack;

        if(stack != null && stack.stackSize>this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }
        this.markDirty();
        worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
    }

    @Override
    public String getInventoryName()
    {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory()
    {
        ChatHelper.send(this+" has been clicked.");
    }

    @Override
    public void closeInventory()
    {

    }



    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        inventory = new ItemStack[INVENTORY_SIZE];
        NBTTagList inventoryTag = tag.getTagList("inventory",10);
        for(int i = 0; i <inventoryTag.tagCount(); i++)
        {
            NBTTagCompound t = inventoryTag.getCompoundTagAt(i);
            int index = t.getByte("index");
            if(index>=0&& index<inventory.length)
            {
                inventory[index] = ItemStack.loadItemStackFromNBT(t);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        NBTTagList inventoryTag = new NBTTagList();
        for(int i = 0; i< inventory.length;i++)
        {
            ItemStack stack = inventory[i];
            if (stack!=null)
            {
                NBTTagCompound t = new NBTTagCompound();
                stack.writeToNBT(t);
                t.setByte("index",(byte)i);
                inventoryTag.appendTag(t);
            }
        }
        tag.setTag("inventory",inventoryTag);
    }
}
