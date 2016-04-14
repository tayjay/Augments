package com.tayjay.augments.augment;

import com.tayjay.augments.augment.interfaces.IAugment;
import com.tayjay.augments.item.ItemAugment;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Add extra health to an entity
 * Created by tayjm_000 on 2016-01-21.
 */
public class AugmentHealthBoost extends ItemAugment
{
    private String augmentName;
    private int id;
    private EntityLiving entity;
    private float boost;
    private final float defaultMaxHealth;

    public AugmentHealthBoost(String n,int i, EntityLiving e, float b)
    {
        this.augmentName = n;
        this.id = i;
        this.entity = e;
        this.boost = b;
        this.defaultMaxHealth = e.getMaxHealth();
    }

    @Override
    public String getAugmentName()
    {
        return augmentName;
    }

    @Override
    public IAugment getAugment()
    {
        return this;
    }

    @Override
    public byte getTier()
    {
        return 0;
    }

    @Override
    public void onAdd(ItemStack stack, EntityLivingBase entity)
    {
        this.entity.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.defaultMaxHealth+this.boost);
    }

    @Override
    public void onRemove(ItemStack stack, EntityLivingBase entity)
    {
        this.entity.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.defaultMaxHealth);

    }

    @Override
    public void writeAugmentToNBT(NBTTagCompound tag)
    {

    }

    @Override
    public void readAugmentFromNBT(NBTTagCompound tag)
    {

    }

    @Override
    public boolean canAdd(ItemStack stack, EntityLivingBase addingTo)
    {
        return true;
    }

    @Override
    public void onEvent(ItemStack itemStack, Event event)
    {

    }


}
