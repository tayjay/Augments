package com.tayjay.augments.init;

import com.tayjay.augments.item.ItemBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by tayjay on 2016-06-23.
 */
public class ModItems
{

    public static ItemBase carbonMesh;

    public static void init()
    {
        carbonMesh = register(new ItemBase("carbonMesh"));
    }

    private static <T extends Item> T register(T item)
    {
        GameRegistry.register(item);

        if(item instanceof IItemModelProvider)
        {
            ((IItemModelProvider)item).registerItemModel(item);
        }
        return item;
    }
}
