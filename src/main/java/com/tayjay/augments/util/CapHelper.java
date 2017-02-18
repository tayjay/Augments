package com.tayjay.augments.util;

import com.tayjay.augments.api.AugmentsAPI;
import com.tayjay.augments.api.capabilities.IAugDataProvider;
import com.tayjay.augments.api.capabilities.IAugHolderProvider;
import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.capabilities.IPlayerBodyProvider;
import com.tayjay.augments.api.item.IAugment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-06-26.
 * Helper class for retrieving and setting capabilities.
 */
public class CapHelper
{
    public static IPlayerBodyProvider getPlayerBodyCap(EntityPlayer player)
    {
        if(hasPlayerBodyCap(player))
        {
            return player.getCapability(AugmentsAPI.PLAYER_BODY_CAPABILITY,null);
        }
        return null;
    }

    public static boolean hasPlayerBodyCap(EntityPlayer player)
    {
        return player.hasCapability(AugmentsAPI.PLAYER_BODY_CAPABILITY,null);
    }

    /*//Removed in favor of central augment storage.
    @Deprecated
    public static IAugHolderProvider getAugHolderCap(ItemStack stack)
    {
        if(stack !=null && stack.getItem() instanceof IAugmentHolder && hasAugHolderCap(stack))
        {
            return stack.getCapability(AugmentsAPI.AUGMENT_HOLDER_CAPABILITY,null);
        }
        return null;
    }

    @Deprecated
    public static boolean hasAugHolderCap(ItemStack stack)
    {
        return stack!=null && stack.hasCapability(AugmentsAPI.AUGMENT_HOLDER_CAPABILITY,null);
    }*/

    public static IPlayerDataProvider getPlayerDataCap(EntityPlayer player)
    {
        if (hasPlayerDataCap(player))
        {
            return player.getCapability(AugmentsAPI.PLAYER_DATA_CAPABILITY, null);
        }
        return null;
    }

    public static boolean hasPlayerDataCap(EntityPlayer player)
    {
        return player.hasCapability(AugmentsAPI.PLAYER_DATA_CAPABILITY, null);
    }

    public static IAugDataProvider getAugmentDataCap(ItemStack stack)
    {
        if(stack!=null && stack.getItem() instanceof IAugment&& hasAugmentDataCap(stack))
        {
            return stack.getCapability(AugmentsAPI.AUGMENT_DATA_CAPABILITY,null);
        }
        return null;
    }

    public static boolean hasAugmentDataCap(ItemStack stack)
    {
        return stack != null && stack.hasCapability(AugmentsAPI.AUGMENT_DATA_CAPABILITY, null);
    }

}
