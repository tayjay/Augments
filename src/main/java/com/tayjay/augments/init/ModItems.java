package com.tayjay.augments.init;

import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.item.*;
import com.tayjay.augments.item.augments.ItemAugment;
import com.tayjay.augments.item.augments.ItemDefib;
import com.tayjay.augments.item.augments.ItemLandingSystem;
import com.tayjay.augments.item.augments.ItemSonar;
import com.tayjay.augments.item.bodyParts.ItemBodyPart;
import com.tayjay.augments.item.bodyParts.ItemBodySet;
import com.tayjay.augments.item.bodyParts.ItemCyborgEyes;
import com.tayjay.augments.item.bodyParts.ItemEnergyCell;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by tayjay on 2016-06-23.
 */
public class ModItems
{

    public static ItemBase carbonMesh;
    public static ItemBodyPart cyborgEyes;
    public static ItemAugment augmentTest;
    public static ItemAugment landingSystem;
    public static ItemAugment defibrillator;
    public static ItemAugment sonar;
    public static ItemBodySet armLeftTest;
    public static ItemBodySet armRightTest;
    public static ItemBodySet torsoTest;
    public static ItemBodySet headTest;
    public static ItemBodySet legLeftTest;
    public static ItemBodySet legRightTest;
    public static ItemEnergyCell energyCellBasic;

    public static void init()
    {
        carbonMesh = register(new ItemBase("carbonMesh"));
        cyborgEyes = register(new ItemCyborgEyes("cyborgEyes",2));
        augmentTest = register(new ItemAugment("augmentTest"));
        landingSystem = register(new ItemLandingSystem("landingSystem"));
        defibrillator = register(new ItemDefib("defibrillator"));
        sonar =  register(new ItemSonar("sonar"));
        armLeftTest = register(new ItemBodySet("armLeftTest",3,"template", PartType.ARM_LEFT));
        armRightTest = register(new ItemBodySet("armRightTest",3,"template", PartType.ARM_RIGHT));//TODO: Use metadata instead of diff item?
        torsoTest = register(new ItemBodySet("torsoTest",4,"template", PartType.TORSO));
        headTest = register(new ItemBodySet("headTest",2,"template", PartType.HEAD));
        legLeftTest = register(new ItemBodySet("legLeftTest",3,"template", PartType.LEG_LEFT));
        legRightTest = register(new ItemBodySet("legRightTest",3,"template", PartType.LEG_RIGHT));
        energyCellBasic = register(new ItemEnergyCell("energyCellBasic",1,1));

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
