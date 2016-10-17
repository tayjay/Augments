package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.item.PartType;

/**
 * Created by tayjay on 2016-08-31.
 */
public class ItemAuraGen extends ItemAugment
{
    public ItemAuraGen(String name, float energyUse)
    {
        super(name,2, PartType.TORSO, energyUse, "Generate an area of effect around the player.");
    }
}
