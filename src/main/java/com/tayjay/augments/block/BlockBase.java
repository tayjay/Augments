package com.tayjay.augments.block;

import com.tayjay.augments.Augments;
import com.tayjay.augments.init.IItemModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

/**
 * Created by tayjay on 2016-06-23.
 */
public class BlockBase extends Block implements IItemModelProvider
{
    protected String name;

    public BlockBase(Material material, String name)
    {
        super(material);
        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Augments.creativeTab);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("tile.%s%s", Augments.modId.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    public String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    @Override
    public void registerItemModel(Item itemBlock)
    {
        Augments.proxy.registerItemRenderer(itemBlock,0,name);
    }
}
