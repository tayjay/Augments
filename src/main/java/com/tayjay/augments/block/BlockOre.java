package com.tayjay.augments.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * Created by tayjay on 2016-06-23.
 */
public class BlockOre extends BlockBase
{
    public BlockOre(String name)
    {
        super(Material.ROCK,name);

        setHardness(3f);
        setResistance(5f);
    }
}
