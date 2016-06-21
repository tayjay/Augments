package com.tayjay.augments.init;

import com.tayjay.augments.augment.handlers.AugmentModels;
import com.tayjay.augments.augment.interfaces.IBodyPart;
import com.tayjay.augments.item.*;
import com.tayjay.augments.item.Augments.ItemAugment;
import com.tayjay.augments.item.Augments.ItemHealthBoost;
import com.tayjay.augments.item.BodyParts.ItemBodyPart;
import com.tayjay.augments.item.BodyParts.ItemBodySet;
import com.tayjay.augments.item.BodyParts.ItemCyborgEyes;
import com.tayjay.augments.lib.Names;
import com.tayjay.augments.lib.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.item.Item;

import java.util.HashMap;

/**
 * Created by tayjm_000 on 2016-01-16.
 */
@GameRegistry.ObjectHolder(Reference.MOD_ID) //To protect this Item Registry from tampering
public class ModItems
{
    /**
     * Declairing Items
     */
    public static ItemMod testScanner;
    public static ItemMod creativeHacker;
    public static ItemBodyPart cyborgEyes;
    public static ItemAugment healthBoost;
    public static ItemBodyPart carbonFiberTorso;


    public static HashMap<String,ItemAugment> augmentItems = new HashMap<String, ItemAugment>();

    /**
     * Initiate the item objects
     */
    public static void init()
    {
        testScanner = new ItemTestScanner();
        creativeHacker = new ItemCreativeHacker();

        cyborgEyes = new ItemCyborgEyes();
        cyborgEyes.setUnlocalizedName(Names.Items.CYBORG_EYES);

        healthBoost = new ItemHealthBoost();
        healthBoost.setUnlocalizedName("augmentHealthBoost");

        carbonFiberTorso = new ItemBodySet(IBodyPart.Type.TORSO,1,1,Names.Items.CARBON_FIBRE_TORSO).setModel(AugmentModels.TORSO).setTextureName("tier1.png");


    }

    /**
     * Try to registerAugment an item.
     *
     * @param item  Item to registerAugment.
     */
    public static void register(final ItemMod item)
    {
        String name = item.getUnwrappedUnlocalizedName(item.getUnlocalizedName());
        if(isEnabled(item)) GameRegistry.registerItem(item, name.substring(name.indexOf(":") + 1));
    }

    public static void register(final ItemAugment item)
    {
        String name = item.getUnwrappedUnlocalizedName(item.getUnlocalizedName());
        if(isEnabled(item))
        {
            GameRegistry.registerItem(item, name.substring(name.indexOf(":") + 1));
            AugmentRegistry.registerAugment(item);
            augmentItems.put(item.getName(),item);
        }
    }

    public static void register(final ItemBodyPart item)
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
