package com.tayjay.augments.util;

import com.tayjay.augments.api.AugmentsAPI;
import com.tayjay.augments.api.capabilities.IAugHolderProvider;
import com.tayjay.augments.api.capabilities.IPlayerPartsProvider;
import com.tayjay.augments.api.events.IAugmentHolder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-06-26.
 * Helper class for retrieving and setting capabilities.
 */
public class CapHelper
{
    public static IPlayerPartsProvider getPlayerPartsCap(EntityPlayer player)
    {
        if(hasPlayerPartsCap(player))
        {
            return player.getCapability(AugmentsAPI.PLAYER_PARTS_CAPABILITY,null);
        }
        return null;
    }

    public static boolean hasPlayerPartsCap(EntityPlayer player)
    {
        return player.hasCapability(AugmentsAPI.PLAYER_PARTS_CAPABILITY,null);
    }


    public static IAugHolderProvider getAugHolderCap(ItemStack stack)
    {
        if(stack !=null && stack.getItem() instanceof IAugmentHolder && hasAugHolderCap(stack))
        {
            return stack.getCapability(AugmentsAPI.AUGMENT_HOLDER_CAPABILITY,null);
        }
        return null;
    }

    public static boolean hasAugHolderCap(ItemStack stack)
    {
        return stack!=null && stack.hasCapability(AugmentsAPI.AUGMENT_HOLDER_CAPABILITY,null);
    }
}
