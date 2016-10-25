package com.tayjay.augments.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.world.World;

/**
 * Created by tayjay on 2016-10-21.
 */
public class EntityDrone extends EntityTameable
{
    public EntityDrone(World worldIn)
    {
        super(worldIn);
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable)
    {
        return null;
    }


}
