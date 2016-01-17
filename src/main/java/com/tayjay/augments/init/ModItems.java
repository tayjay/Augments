package com.tayjay.augments.init;

import com.tayjay.augments.item.ItemA;
import com.tayjay.augments.lib.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

/**
 * Created by tayjm_000 on 2016-01-16.
 */
@GameRegistry.ObjectHolder(Reference.MOD_ID) //To protect this Item Registry from tampering
public class ModItems
{
    /**
     * Declairing Items
     */


    /**
     * Initiate the item objects
     */
    public static void init()
    {

    }

    /**
     * Try to register an item.
     *
     * @param item  Item to register.
     */
    public static void register(final ItemA item)
    {
        String name = item.getUnwrappedUnlocalizedName(item.getUnlocalizedName());
        if(isEnabled(item)) GameRegistry.registerItem(item, name.substring(name.indexOf(":") + 1));
    }

    /**
     * Check is an item can be registered.
     *
     * @param item  Item to check.
     * @return      Item is NOT on blacklist.
     */
    public static boolean isEnabled(Item item)
    {
        //return !ConfigHandler.disabledNamesList.contains(item.getUnlocalizedName());
        return true;
    }
}