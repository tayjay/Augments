package com.tayjay.augments.properties;

import com.tayjay.augments.augment.interfaces.IAugment;

import java.util.List;

/**
 * Created by tayjm_000 on 2016-01-17.
 */
public interface IAugmentProperties
{
    /**
     * Whether properties have changed and need updating
     * @return  This needs to be updated
     */
    boolean isDirty();

    /**
     * Mark this for an update
     */
    void markDirty();

    /**
     * Whether this is augmented and should run through to find events.
     * @return  This has augments
     */
    boolean isAugmented();

    /**
     * Whether the Entity being referenced is a Player.
     * @return  Is this a player
     */
    boolean isPlayer();

    /**
     * Get the Class of the Entity this object is referencing
     * @return  Class of Entity
     */
    Class getEntityClass();

    /**
     * List of Augments that this holds
     * @return  List of Augments
     */
    List<IAugment> getAugments();

    /**
     * Look at everything to make sure there are no discrepencies.<br/>
     * Such as confirming that the Augments contained match the effects added to this.
     * @return
     */
    boolean verify();

    void addAugment(IAugment a);

    void removeAugment(IAugment a);
}
