package com.tayjay.augments.init;

import com.tayjay.augments.api.item.IBodyPart;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.item.*;
import net.minecraft.creativetab.CreativeTabs;
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
    public static ItemBodySet armLeftTest;
    public static ItemBodySet armRightTest;
    public static ItemBodySet torsoTest;
    public static ItemBodySet headTest;

    public static void init()
    {
        carbonMesh = register(new ItemBase("carbonMesh"));
        cyborgEyes = register(new ItemCyborgEyes("cyborgEyes",2));
        augmentTest = register(new ItemAugment("augmentTest"));
        armLeftTest = register(new ItemBodySet("armLeftTest",3, PartType.ARM_LEFT));
        armRightTest = register(new ItemBodySet("armRightTest",3, PartType.ARM_RIGHT));//TODO: Use metadata instead of diff item?
        torsoTest = register(new ItemBodySet("torsoTest",4, PartType.TORSO));
        headTest = register(new ItemBodySet("headTest",2, PartType.HEAD));
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
