package com.tayjay.augments.init;

import com.tayjay.augments.block.BlockBase;
import com.tayjay.augments.block.BlockModelTest;
import com.tayjay.augments.block.BlockOre;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by tayjay on 2016-06-23.
 */
public class ModBlocks
{
    public static BlockOre oreCarbon;
    public static BlockModelTest modelTest;


    public static void init()
    {
        oreCarbon = register(new BlockOre("oreCarbon"));
        modelTest = register(new BlockModelTest("modelTest"));

    }

    private static <T extends Block> T register(T block, ItemBlock itemBlock)
    {
        GameRegistry.register(block);
        GameRegistry.register(itemBlock);

        if(block instanceof IItemModelProvider)
        {
            ((IItemModelProvider)block).registerItemModel(itemBlock);
        }

        return block;
    }

    private static <T extends Block> T register(T block)
    {
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        return register(block,itemBlock);
    }
}
