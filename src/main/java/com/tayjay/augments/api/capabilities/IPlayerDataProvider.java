package com.tayjay.augments.api.capabilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

/**
 * Created by tayjay on 2016-06-26.
 */
public interface IPlayerDataProvider extends INBTSerializable<NBTTagCompound>
{

    /*TODO: Implement these correctly
    IItemHandler getPlayerParts();

    IItemHandler getPlayerAugments();

    IItemHandler getAugTick();

    IItemHandler getAugHUD();

    IItemHandler getAugAttack();

    IItemHandler getAugDeath();

    IItemHandler getAugHurt();

    IItemHandler getAugInteract();

    void sortAugments();
    */


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

    //ItemStack getAllAugments();


}
