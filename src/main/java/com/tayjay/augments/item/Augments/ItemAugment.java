package com.tayjay.augments.item.Augments;

import com.google.common.collect.Multimap;
import com.tayjay.augments.augment.interfaces.IAugment;
import com.tayjay.augments.creativetab.CreativeTabA;
import com.tayjay.augments.init.ModItems;
import com.tayjay.augments.lib.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * Created by tayjm_000 on 2016-01-24.
 */
public abstract class ItemAugment extends Item implements IAugment
{

    public ItemAugment()
    {
        super();
        setHasSubtypes(true);
        setCreativeTab(CreativeTabA.AUGMENT_TAB);
        this.maxStackSize = 1;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List subTypes)
    {
        super.getSubItems(item, creativeTabs, subTypes);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }

    @Override
    public Multimap getAttributeModifiers(ItemStack stack)
    {
        return super.getAttributeModifiers(stack);
    }

    public String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }


    @Override
    public void onAdd(ItemStack stack, EntityLivingBase entity)
    {

    }

    @Override
    public void onRemove(ItemStack stack, EntityLivingBase entity)
    {

    }

    @Override
    public void writeAugmentToNBT(NBTTagCompound tag)
    {

    }


    @Override
    public void readAugmentFromNBT(NBTTagCompound tag)
    {

    }


    @Override
    public boolean canAdd(ItemStack augment, ItemStack bodyPart)
    {
        return true;
    }


}
