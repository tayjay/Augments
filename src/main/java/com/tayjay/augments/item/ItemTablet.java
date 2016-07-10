package com.tayjay.augments.item;

import com.tayjay.augments.util.ChatHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiConfigEntries;

/**
 * Created by tayjay on 2016-07-08.
 */
public class ItemTablet extends ItemBase
{
    public ItemTablet(String name)
    {
        super(name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if(worldIn.isRemote)
            ChatHelper.send(playerIn,"Hello "+playerIn.getDisplayNameString()+". Welcome to the world of Augments!");
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }
}
