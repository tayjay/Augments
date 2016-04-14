package com.tayjay.augments.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by tayjm_000 on 2016-03-25.
 */
public class EntityAugmentHolder extends EntityCreature
{
    public EntityAugmentHolder(World world,EntityLivingBase parent)
    {
        super(world);
        ridingEntity = parent;
        
    }

    @Override
    public ItemStack getHeldItem()
    {
        return null;
    }

    @Override
    public ItemStack getEquipmentInSlot(int slot)
    {
        return null;
    }

    @Override
    public void setCurrentItemOrArmor(int p_70062_1_, ItemStack p_70062_2_)
    {

    }

    @Override
    public ItemStack[] getLastActiveItems()
    {
        return new ItemStack[0];
    }



}
