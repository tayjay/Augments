package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.item.PartType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-11-24.
 * A leg augment that causes the player to launch high into the air.
 */
public class ItemHighJump extends ItemAugment
{
    public ItemHighJump(String name, int tier, PartType part, float energyUse, String description)
    {
        super(name, tier, part, energyUse, description);
    }

    @Override
    public void activate(ItemStack augment, EntityPlayer player)
    {
        player.addVelocity(0,10,0);
    }
}
