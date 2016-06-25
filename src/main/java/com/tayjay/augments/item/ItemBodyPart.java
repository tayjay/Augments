package com.tayjay.augments.item;

import com.tayjay.augments.api.item.IBodyPart;
import com.tayjay.augments.init.IItemModelProvider;

/**
 * Created by tayjay on 2016-06-24.
 */
public class ItemBodyPart extends ItemBase implements IBodyPart,IItemModelProvider
{
    public ItemBodyPart(String name)
    {
        super(name);
    }
}
