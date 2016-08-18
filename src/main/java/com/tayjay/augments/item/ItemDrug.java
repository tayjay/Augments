package com.tayjay.augments.item;

import com.tayjay.augments.util.CapHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

/**
 * Created by tayjay on 2016-07-31.
 */
public class ItemDrug extends ItemBase
{
    int restoreAmount;
    public ItemDrug(String name,int restoreAmount)
    {
        super(name);
        this.maxStackSize = 1;
        this.restoreAmount = restoreAmount;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if(!worldIn.isRemote)
        {
            CapHelper.getPlayerDataCap(playerIn).addDrugTimer(this.restoreAmount);
            itemStackIn = null;
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }
}
