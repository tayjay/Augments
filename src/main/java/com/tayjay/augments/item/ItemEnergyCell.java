package com.tayjay.augments.item;

import com.tayjay.augments.api.item.PartType;

/**
 * Created by tayjay on 2016-06-26.
 */
public class ItemEnergyCell extends ItemBodyPart
{
    public ItemEnergyCell(String name, int tier)
    {
        super(name, tier, PartType.POWER);
    }
}
