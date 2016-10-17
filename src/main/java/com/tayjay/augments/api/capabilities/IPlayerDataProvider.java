package com.tayjay.augments.api.capabilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

/**
 * Created by tayjay on 2016-06-26.
 * All data needed by the mod to preform functions.
 */
public interface IPlayerDataProvider extends INBTSerializable<NBTTagCompound>
{

    boolean validate();

    void sync(EntityPlayerMP player);

    float getMaxEnergy();

    void setMaxEnergy(float maxEnergy);

    float getCurrentEnergy();

    void setCurrentEnergy(float currentEnergy);

    float getRechargeRate();

    void setRechargeRate(float rechargeRate);

    void addEnergy(float energyIn);

    boolean removeEnergy(float energyIn);

    void rechargeTick();

    void reboot();

    //Anti-Rejection Drug Data*********************
    boolean needsDrug();

    void doDrugTick();

    int getDrugTimer();

    void setDrugTimer(int amount);

    void addDrugTimer(int amount);

    void doDrugEffect();

    //**********************************************
    /**
     * Get the current augment that implements IActivate to be used when the "use" key is pressed
     * @return  Augment to activate
     */
    ItemStack getCurrentAugment();

    /**
     * Move to the next augment in the active list.
     */
    void cycleActiveAugment();

    /**
     * Get whether the current augment is active or not.
     * The augment can use this to control a prolonged effect then shut itself down.
     * @return
     */
    boolean isAugmentActive();

    /**
     * Change the state of the current augment whether it is active or not
     * @param active    New active state of current augment
     */
    void setAugmentActive(boolean active);

    /**
     * Get the current tier of the player. This will help with progression.
     * @return
     */
    byte getPlayerTier();

}
