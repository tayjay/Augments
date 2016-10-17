package com.tayjay.augments.api.capabilities;

import com.tayjay.augments.api.item.PartType;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

/**
 * Created by tayjay on 2016-06-24.
 * Holder the Body attached to the player.
 * This is separate from Player data to reduce network traffic,
 * player data needs to be synced alot to handle power levels, body only needs to sync when augments and parts have changed.
 * Formally IPlayerPartProvider until 0.1.2.0
 */
public interface IPlayerBodyProvider extends INBTSerializable<NBTTagCompound>
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
     * Get body part on the player, accounting for which side of the body it is on.
     * Used for new implentation of Body Parts in 0.1.2.0 which doesn't have sides built in
     * @param type  BodyPart type
     * @param side  Side of body this part is on, 0=left,1=right
     * @return      Stack of part type on the side passed
     */
    ItemStack getStackByPartSided(PartType type, int side);

    /**
     * Get augments on player, different implementation from older versions
     * @return ItemHandler of all augments on player
     */
    IItemHandler getAugments();

    /**
     * Augment capacity will be controlled by other sources, like body tier.
     * @return
     */
    int getAugmentCapacity();


    /**
     * Sync self body to player
     * Mostly controlled with packets
     * @param player    Player to sync
     */
    void sync(EntityPlayerMP player);

    /**
     * Sync body from player to other
     * Mostly controlled with packets
     * @param player    Player to get parts from
     * @param other     Player to send to.
     */
    void syncToOther(EntityPlayerMP player,EntityPlayerMP other);

    /**
     * Get a hash value of body on the player.
     * Compares client and server values to determine if there needs to be a sync.
     * @return  Hash value
     */
    int getHash();
}
