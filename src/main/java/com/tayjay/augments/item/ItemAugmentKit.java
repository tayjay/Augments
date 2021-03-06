package com.tayjay.augments.item;

import com.tayjay.augments.util.ChatHelper;
import com.tayjay.augments.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

/**
 * Created by tayjay on 2016-07-08.
 */
public class ItemAugmentKit extends ItemBase
{
    public ItemAugmentKit(String name)
    {
        super(name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if(!playerIn.worldObj.isRemote && playerIn.rayTrace(1,0).entityHit.getDataManager().get(EntityUtil.ENERGY)!=null)
        {
            ChatHelper.send(playerIn,playerIn.rayTrace(1,0).entityHit.getDataManager().get(EntityUtil.ENERGY).toString());
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }
}
