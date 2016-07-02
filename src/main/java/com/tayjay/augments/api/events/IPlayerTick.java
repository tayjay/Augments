package com.tayjay.augments.api.events;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Created by tayjay on 2016-06-25.
 */
public interface IPlayerTick
{
    void onPlayerTick(ItemStack augmentStack, ItemStack bodyPartStack, TickEvent.PlayerTickEvent event);
}
