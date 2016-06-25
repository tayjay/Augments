package com.tayjay.augments.item;

import com.tayjay.augments.Augments;
import com.tayjay.augments.handler.GuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

/**
 * Created by tayjay on 2016-06-24.
 */
public class ItemCyborgEyes extends ItemBodyPart
{
    public ItemCyborgEyes(String name)
    {
        super(name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if(!worldIn.isRemote)
        {
            playerIn.openGui(Augments.instance, GuiHandler.GuiIDs.PLAYER_PARTS.ordinal(),worldIn,(int)playerIn.posX,(int)playerIn.posY,(int)playerIn.posZ);
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }


}
