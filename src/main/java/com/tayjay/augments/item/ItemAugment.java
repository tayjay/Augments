package com.tayjay.augments.item;

import com.tayjay.augments.api.item.IAugment;
import com.tayjay.augments.init.IItemModelProvider;

/**
 * Created by tayjay on 2016-06-23.<br/>
 * Items extending this will be augments to be used in the mod.
 */
public class ItemAugment extends ItemBase implements IAugment,IItemModelProvider
{
    public ItemAugment(String name)
    {
        super(name);
    }
}
