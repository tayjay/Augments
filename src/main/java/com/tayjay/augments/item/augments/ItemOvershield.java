package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.events.IActivate;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.ChatHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-07-03.
 */
public class ItemOvershield extends ItemAugment implements IActivate
{
    public ItemOvershield(String name)
    {
        super(name,2,PartType.TORSO,2,"Create a shield around the player");

    }

    @Override
    public boolean validate(ItemStack augment, EntityPlayer player)
    {
        if(CapHelper.getPlayerDataCap(player).getCurrentEnergy()<getEnergyUse(augment))
            return false;
        if(!CapHelper.getPlayerDataCap(player).validate())
            return false;
        return true;
    }




    @Override
    public void activate(ItemStack stack, EntityPlayer playerIn)
    {
        if(validate(stack, playerIn))
        {
            CapHelper.getPlayerDataCap(playerIn).removeEnergy(getEnergyUse(stack));

            //EntityUtil.setFlag(playerIn,21,true);
        }
        else
        {
            ChatHelper.send(playerIn, "Not enough energy!");
        }
    }
    @Override
    public boolean isActive(ItemStack augment, ItemStack bodyPart, EntityPlayer player)
    {
        IPlayerDataProvider playerDataProvider = CapHelper.getPlayerDataCap(player);
        return playerDataProvider.getCurrentAugment().getItem()==this && playerDataProvider.isAugmentActive();
    }
}
