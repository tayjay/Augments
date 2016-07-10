package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.item.PartType;

/**
 * Created by tayjay on 2016-07-03.
 */
public class ItemAugElytra extends ItemAugment
{
    public ItemAugElytra(String name)
    {
        super(name);
        acceptedParts.add(PartType.TORSO);
    }
}
