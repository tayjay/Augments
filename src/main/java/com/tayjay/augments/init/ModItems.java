package com.tayjay.augments.init;

import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.item.*;
import com.tayjay.augments.item.augments.*;
import com.tayjay.augments.item.bodyParts.ItemBodyPart;
import com.tayjay.augments.item.bodyParts.ItemBodySet;
import com.tayjay.augments.item.bodyParts.ItemCyborgEyes;
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
    public static ItemAugment xpModifier;
    public static ItemAugment auraGen;
    public static ItemAugment speedHands;
    public static ItemAugment flight;
    public static ItemAugment mindControl;
    public static ItemAugment feeder;

    public static ItemBodySet armTest;
    //public static ItemBodySet armRightTest;
    public static ItemBodySet torsoTest;
    public static ItemBodySet headTest;
    public static ItemBodySet legTest;
    //public static ItemBodySet legRightTest;

    public static ItemBodySet headIron;
    public static ItemBodySet torsoIron;
    public static ItemBodySet armIron;
    //public static ItemBodySet armRightIron;
    public static ItemBodySet legIron;
    //public static ItemBodySet legRightIron;

    public static ItemBodySet headExo1;
    public static ItemBodySet torsoExo1;
    public static ItemBodySet armExo1;
    //public static ItemBodySet armRightExo1;
    public static ItemBodySet legExo1;
    //public static ItemBodySet legRightExo1;

    public static ItemBodySet headDiamond;
    public static ItemBodySet torsoDiamond;
    public static ItemBodySet armDiamond;
    public static ItemBodySet legDiamond;

    //public static ItemEnergyCell energyCellBasic;

    public static void init()
    {
        carbonMesh = register(new ItemBase("carbonMesh"));
        tablet = register(new ItemTablet("tablet"));
        praxisKit = register(new ItemAugmentKit("praxisKit"));
        antiRejectDrug = register(new ItemDrug("antiRejectDrug",1000));

        cyborgEyes = register(new ItemCyborgEyes("cyborgEyes",2));
        eyeSingleL = register(new ItemCyborgEyes("eyeSingleL",1));
        eyeSingleR = register(new ItemCyborgEyes("eyeSingleR",1));
        //augmentTest = register(new ItemAugment("augmentTest",0));
        landingSystem = register(new ItemLandingSystem("landingSystem"));
        defibrillator = register(new ItemDefib("defibrillator"));
        sonar =  register(new ItemSonar("sonar"));
        xpModifier = register(new ItemXPModifier("xpModifier"));
        auraGen = register(new ItemAuraGen("auraGen"));
        speedHands = register(new ItemSpeedHand("speedHands"));
        flight = register(new ItemFlight("flight",0.05f));
        mindControl = register(new ItemMindControl("mindControl"));
        feeder = register(new ItemFeeder("feeder"));

        overShield = register(new ItemOvershield("overshield"));
        typhoon = register(new ItemExplosiveSystem("typhoon"));

        armTest = register(new ItemBodySet("armTest",2,3,2,"template", PartType.ARM));
        //armRightTest = register(new ItemBodySet("armRightTest",2,3,2,"template", PartType.ARM_RIGHT));//TODO: Use metadata instead of diff item?
        torsoTest = register(new ItemBodySet("torsoTest",2,5,3,"template", PartType.TORSO));
        headTest = register(new ItemBodySet("headTest",2,4,2,"template", PartType.HEAD));
        legTest = register(new ItemBodySet("legTest",2,3,2,"template", PartType.LEG));
        //legRightTest = register(new ItemBodySet("legRightTest",2,3,2,"template", PartType.LEG_RIGHT));


        headIron = register(new ItemBodySet("headIron",2,3,2,"iron_good","iron_good",PartType.HEAD));
        torsoIron = register(new ItemBodySet("torsoIron",2,4,3,"iron_good","iron_good",PartType.TORSO));
        armIron = register(new ItemBodySet("armIron",2,2,2,"iron_good","iron_good",PartType.ARM));
        //armRightIron = register(new ItemBodySet("armRightIron",1,2,2,"iron_good","iron_good",PartType.ARM_RIGHT));
        legIron = register(new ItemBodySet("legIron",2,2,2,"iron_good","iron_good",PartType.LEG));
        //legRightIron = register(new ItemBodySet("legRightIron",1,2,2,"iron_good","iron_good",PartType.LEG_RIGHT));

        headExo1 = register(new ItemBodySet("headExo1",1,3,2,"exo1",PartType.HEAD));
        torsoExo1 = register(new ItemBodySet("torsoExo1",1,4,3,"exo1",PartType.TORSO));
        armExo1 = register(new ItemBodySet("armExo1",1,2,2,"exo1",PartType.ARM));
        //armRightExo1 = register(new ItemBodySet("armRightExo1",1,2,2,"exo1",PartType.ARM_RIGHT));
        legExo1 = register(new ItemBodySet("legExo1",1,2,2,"exo1",PartType.LEG));
        //legRightExo1 = register(new ItemBodySet("legRightExo1",1,2,2,"exo1",PartType.LEG_RIGHT));

        headDiamond =  register(new ItemBodySet("headDiamond",3,3,2,"diamond_body",PartType.HEAD));
        torsoDiamond =  register(new ItemBodySet("torsoDiamond",3,4,2,"diamond_body",PartType.TORSO));
        armDiamond =  register(new ItemBodySet("armDiamond",3,2,2,"diamond_body",PartType.ARM));
        legDiamond =  register(new ItemBodySet("legDiamond",3,2,2,"diamond_body",PartType.LEG));


        //energyCellBasic = register(new ItemEnergyCell("energyCellBasic",1,1));

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
