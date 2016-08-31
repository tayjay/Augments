package com.tayjay.augments.api.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

/**
 * Created by tayjay on 2016-06-25.
 * Augment that activates when the player is hurt.
 */
public interface ILivingHurt
{
    void onHurt(ItemStack augment, ItemStack bodyPart, EntityPlayer player, LivingHurtEvent event);
}
