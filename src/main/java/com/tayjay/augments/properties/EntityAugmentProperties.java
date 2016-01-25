package com.tayjay.augments.properties;

import com.tayjay.augments.augment.interfaces.IAugment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tayjm_000 on 2016-01-17.
 */
public class EntityAugmentProperties implements IAugmentProperties, IExtendedEntityProperties
{
    public static final String AUG_PROP_NAME = "EntityAugmentProperties";
    private EntityLiving entity;
    private List<IAugment> augments;
    private boolean isAugmented;
    private boolean canDespawn;

    public EntityAugmentProperties(EntityLiving e)
    {
        this.entity = e;
        this.augments = new ArrayList<IAugment>();
        this.isAugmented =false;
        this.canDespawn = true;
    }

    public static final void register(EntityLiving e)
    {
        e.registerExtendedProperties(EntityAugmentProperties.AUG_PROP_NAME,new EntityAugmentProperties(e));
    }

    public boolean canDespawn()
    {
        return !isAugmented||canDespawn;
    }

    public void setCanDespawn(boolean b)
    {
        this.canDespawn = b;
        /*living.func_110163_bv();*/
        /*This is the method that allows the persistance of a mob.*/
    }


    @Override
    public boolean isDirty()
    {
        return false;
    }

    @Override
    public void markDirty()
    {

    }

    @Override
    public boolean isAugmented()
    {
        return false;
    }

    @Override
    public boolean isPlayer()
    {
        return false;
    }

    @Override
    public Class getEntityClass()
    {
        return null;
    }

    @Override
    public List<IAugment> getAugments()
    {
        return null;
    }

    @Override
    public boolean verify()
    {
        return false;
    }

    @Override
    public void addAugment(IAugment a)
    {

    }

    @Override
    public void removeAugment(IAugment a)
    {

    }

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {

    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {

    }

    @Override
    public void init(Entity entity, World world)
    {

    }

    /**
     * Returns EntityAugmentPropeties properties for entity
     * This method is for convenience only; it will make your code look nicer
     */
    public static final EntityAugmentProperties get(EntityLiving e)
    {

        return (EntityAugmentProperties) e.getExtendedProperties(AUG_PROP_NAME);
    }
}
