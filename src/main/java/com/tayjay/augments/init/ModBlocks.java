package com.tayjay.augments.init;

import com.tayjay.augments.block.BlockA;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

/**
 * Created by tayjm_000 on 2016-01-16.
 */
public class ModBlocks
{

    /**
     * Declair all Blocks
     */

    /**
     * Initiate each block object
     */
    public static void init()
    {

    }

    /**
     * Register the block.
     *
     * @param block Block to be Registered.
     */
    public static void register(BlockA block)
    {
        String name = block.getUnwrappedUnlocalizedName(block.getUnlocalizedName());
        if(isEnabled(block)) GameRegistry.registerBlock(block, name.substring(name.indexOf(":") + 1));
    }

    /**
     * Register the block.
     *
     * @param block Block to be Registered
     * @param item
     */
    public static void register(BlockA block, Class<? extends ItemBlock> item)
    {
        String name = block.getUnwrappedUnlocalizedName(block.getUnlocalizedName());
        if (isEnabled(block))GameRegistry.registerBlock(block, item, name.substring(name.indexOf(":") + 1));
    }

    /**
     * Register another block.
     *
     * @param block Block to be registered
     */
    public static void registerOther(Block block)
    {
        String name = block.getUnlocalizedName().substring(block.getUnlocalizedName().indexOf(".") + 1);
        if (isEnabled(block)) GameRegistry.registerBlock(block, name.substring(name.indexOf(":") + 1));
    }

    /**
     * Look through config to find if the block param is allowed to be registered.
     *
     * @param block Block to check
     * @return      Is the block NOT on the blacklist
     */
    public static boolean isEnabled(Block block)
    {
        //return !ConfigHandler.disabledNamesList.contains(block.getUnlocalizedName());
        return true;
    }
}
