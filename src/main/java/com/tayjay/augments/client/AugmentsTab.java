package com.tayjay.augments.client;

import com.tayjay.augments.Augments;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

/**
 * Created by tayjay on 2016-06-23.
 */
public class AugmentsTab extends CreativeTabs
{
    public AugmentsTab()
    {
        super(Augments.modId);
    }

    @Override
    public Item getTabIconItem()
    {
        return Items.NETHER_STAR;
    }
}
