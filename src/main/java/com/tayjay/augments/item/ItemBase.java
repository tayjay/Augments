package com.tayjay.augments.item;

import com.tayjay.augments.Augments;
import com.tayjay.augments.api.DynamicIcon;
import com.tayjay.augments.init.IItemModelProvider;
import com.tayjay.augments.network.NetworkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

/**
 * Created by tayjay on 2016-06-23.
 */
public class ItemBase extends Item implements IItemModelProvider
{
    protected String name;
    public ItemBase(String name)
    {
        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Augments.creativeTab);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Augments.modId.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s", Augments.modId.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    public String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    public void registerItemModel(Item item)
    {
        DynamicIcon.createGenericIcon(Augments.modId,Augments.name,name);
        Augments.proxy.registerItemRenderer(this,0,name);
    }
}
