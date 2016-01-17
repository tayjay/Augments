package com.tayjay.augments.creativetab;

import com.tayjay.augments.lib.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Created by tayjm_000 on 2016-01-16.
 */
public class CreativeTabA
{
    public static final CreativeTabs A_TAB = new CreativeTabs(Reference.MOD_ID.toLowerCase())
    {
        @Override
        public Item getTabIconItem()
        {
            return null;
        }
    };
}
