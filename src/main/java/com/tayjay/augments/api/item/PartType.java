package com.tayjay.augments.api.item;

/**
 * Created by tayjay on 2016-06-26.
 * Determines what type a Body Part is.
 * Used by container to know what slot to allow it in.
 */
public enum PartType
{
    HEAD("Head"),EYES("Eyes"),TORSO("Torso"),ARM("Arm"),LEG("Leg");


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
