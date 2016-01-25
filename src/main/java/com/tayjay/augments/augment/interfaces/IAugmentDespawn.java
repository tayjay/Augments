package com.tayjay.augments.augment.interfaces;

import net.minecraftforge.event.entity.living.LivingSpawnEvent;

/**
 * Controls the despawning of the attached entity<br/>
 * I recommend that if you have an entity that is augmented. You make sure this is activated to keep from losing all your augments.
 * Created by tayjm_000 on 2016-01-18.
 */
public interface IAugmentDespawn
{
    /**
     * Can this mob despawn?
     * @return  Mob is allowed to despawn
     */
    boolean canDespawn();

    /**
     * What to do on entity despawn.<br/>
     * Result.DENY to stop this from despawning.
     * @param event
     */
    void onDespawn(LivingSpawnEvent.AllowDespawn event);
}
