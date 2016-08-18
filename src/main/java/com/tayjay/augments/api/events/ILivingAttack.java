package com.tayjay.augments.api.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

/**
 * Created by tayjay on 2016-06-25.
 */
public interface ILivingAttack
{
    void onAttack(ItemStack stack, EntityPlayer player, LivingAttackEvent event);
}
