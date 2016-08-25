package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.events.IPlayerTick;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Created by tayjay on 2016-08-21.
 */
public class ItemFlight extends ItemAugment implements IPlayerTick
{
    public ItemFlight(String name, float energyUse)
    {
        super(name, energyUse);
    }

    @Override
    public void onPlayerTick(ItemStack augmentStack, ItemStack bodyPartStack, TickEvent.PlayerTickEvent event)
    {

    }
}
