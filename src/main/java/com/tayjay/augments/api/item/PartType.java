package com.tayjay.augments.api.item;

/**
 * Created by tayjay on 2016-06-26.
 * Determines what type a Body Part is.
 * Used by container to know what slot to allow it in.
 * Used by Augments to know what part they can be applied to.
 */
public enum PartType
{
    BRAIN("Brain"),HEAD("Head"),EYES("Eyes"),TORSO("Torso"),ARM_LEFT("Left Arm"),ARM_RIGHT("Right Arm"),LEG_LEFT("Left Leg"),LEG_RIGHT("Right Leg"),POWER("Power");


    String partName;
    PartType(String name)
    {
        this.partName = name;
    }

    @Override
    public String toString()
    {
        return this.partName;
    }
}
