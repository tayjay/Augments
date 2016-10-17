package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.item.PartType;

/**
 * Created by tayjay on 2016-07-10.
 */
public class ItemPotionInjector extends ItemAugment
{
    public ItemPotionInjector(String name)
    {
        super(name,1, PartType.TORSO,1,"Provide potion effect to player");

    }
}
