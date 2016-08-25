package com.tayjay.augments.init;

import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.item.*;
import com.tayjay.augments.item.augments.*;
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
    public static ItemTablet tablet;
    public static ItemAugmentKit praxisKit;
    public static ItemDrug antiRejectDrug;

    public static ItemBodyPart cyborgEyes;
    public static ItemBodyPart eyeSingleL;
    public static ItemBodyPart eyeSingleR;

    public static ItemAugment augmentTest;
    public static ItemAugment landingSystem;
    public static ItemAugment defibrillator;
    public static ItemAugment sonar;
    public static ItemAugment overShield;
    public static ItemAugment typhoon;

    public static ItemBodySet armLeftTest;
    public static ItemBodySet armRightTest;
    public static ItemBodySet torsoTest;
    public static ItemBodySet headTest;
    public static ItemBodySet legLeftTest;
    public static ItemBodySet legRightTest;

    public static ItemBodySet headIron;
    public static ItemBodySet torsoIron;
    public static ItemBodySet armLeftIron;
    public static ItemBodySet armRightIron;
    public static ItemBodySet legLeftIron;
    public static ItemBodySet legRightIron;

    public static ItemEnergyCell energyCellBasic;

    public static void init()
    {
        carbonMesh = register(new ItemBase("carbonMesh"));
        tablet = register(new ItemTablet("tablet"));
        praxisKit = register(new ItemAugmentKit("praxisKit"));
        antiRejectDrug = register(new ItemDrug("antiRejectDrug",1000));

        cyborgEyes = register(new ItemCyborgEyes("cyborgEyes",2));
        eyeSingleL = register(new ItemCyborgEyes("eyeSingleL",1));
        eyeSingleR = register(new ItemCyborgEyes("eyeSingleR",1));
        augmentTest = register(new ItemAugment("augmentTest",0));
        landingSystem = register(new ItemLandingSystem("landingSystem"));
        defibrillator = register(new ItemDefib("defibrillator"));
        sonar =  register(new ItemSonar("sonar"));

        overShield = register(new ItemOvershield("overshield"));
        typhoon = register(new ItemExplosiveSystem("typhoon"));

        armLeftTest = register(new ItemBodySet("armLeftTest",2,3,2,"template", PartType.ARM_LEFT));
        armRightTest = register(new ItemBodySet("armRightTest",2,3,2,"template", PartType.ARM_RIGHT));//TODO: Use metadata instead of diff item?
        torsoTest = register(new ItemBodySet("torsoTest",2,5,3,"template", PartType.TORSO));
        headTest = register(new ItemBodySet("headTest",2,4,2,"template", PartType.HEAD));
        legLeftTest = register(new ItemBodySet("legLeftTest",2,3,2,"template", PartType.LEG_LEFT));
        legRightTest = register(new ItemBodySet("legRightTest",2,3,2,"template", PartType.LEG_RIGHT));


        headIron = register(new ItemBodySet("headIron",1,3,2,"iron_good","iron_good",PartType.HEAD));
        torsoIron = register(new ItemBodySet("torsoIron",1,4,3,"iron_good","iron_good",PartType.TORSO));
        armLeftIron = register(new ItemBodySet("armLeftIron",1,2,2,"iron_good","iron_good",PartType.ARM_LEFT));
        armRightIron = register(new ItemBodySet("armRightIron",1,2,2,"iron_good","iron_good",PartType.ARM_RIGHT));
        legLeftIron = register(new ItemBodySet("legLeftIron",1,2,2,"iron_good","iron_good",PartType.LEG_LEFT));
        legRightIron = register(new ItemBodySet("legRightIron",1,2,2,"iron_good","iron_good",PartType.LEG_RIGHT));

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
