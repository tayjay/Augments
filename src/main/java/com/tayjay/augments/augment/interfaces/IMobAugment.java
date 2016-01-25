package com.tayjay.augments.augment.interfaces;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

/**
 * Interface for Mob specific augments.<br/>
 * <b>NOTE: To stop this augment from being wasted, implement IAugmentDespawn with this to keep mob alive.</b><br/>
 * Created by tayjm_000 on 2016-01-18.
 */
public interface IMobAugment
{
    /**
     * Get the EntityLiving attached to this augment
     * @return  Attached EntityLiving
     */
    EntityLiving getEntity();
}
