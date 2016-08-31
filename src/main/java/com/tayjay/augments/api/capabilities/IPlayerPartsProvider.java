package com.tayjay.augments.api.capabilities;

import com.sun.istack.internal.NotNull;
import com.tayjay.augments.api.item.PartType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

/**
 * Created by tayjay on 2016-06-24.
 * Holder the Body Parts attached to the player.
 * This is seperate from Player data to make things simpler for me.
 */
public interface IPlayerPartsProvider extends INBTSerializable<NBTTagCompound>
{
    /**
     * Get all Body Parts attached to the player
     * @return  ItemHandler of all Body Parts on player
     */
    IItemHandler getBodyParts();

    /**
     * Get player's Body Part by what part type it is.
     * @param type  Part type to look for
     * @return      Stack of part of that type, if null then no part there.
     */
    ItemStack getStackByPart(PartType type);

    /**
     * Sync all self bodyparts to player
     * Mostly controlled with packets
     * @param player    Player to sync
     */
    void sync(EntityPlayerMP player);

    /**
     * Sync all bodyparts from player to other
     * Mostly controlled with packets
     * @param player    Player to get parts from
     * @param other     Player to send to.
     */
    void syncToOther(EntityPlayerMP player,EntityPlayerMP other);

    /**
     * Get a hash value of all bodyparts on the player.
     * Compares client and server values to determine if there needs to be a sync.
     * @return  Hash value
     */
    int getHash();
}
