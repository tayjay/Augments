package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.item.PartType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Created by tayjay on 2016-08-21.
 */
public class ItemFlight extends ItemAugment
{
    public ItemFlight(String name, float energyUse)
    {
        super(name,3, PartType.TORSO, energyUse,"Allows flight");
    }


}
