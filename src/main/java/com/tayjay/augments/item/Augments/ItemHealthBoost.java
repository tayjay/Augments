package com.tayjay.augments.item.Augments;

import com.tayjay.augments.init.ModItems;
import com.tayjay.augments.lib.Names;

/**
 * Created by tayjay on 2016-06-20.
 */
public class ItemHealthBoost extends ItemAugment
{

    public ItemHealthBoost()
    {
        super();
        this.setUnlocalizedName("augmentHealthBoost");
        ModItems.register(this);
    }

    @Override
    public String getName()
    {
        return getUnlocalizedName();
    }


}
