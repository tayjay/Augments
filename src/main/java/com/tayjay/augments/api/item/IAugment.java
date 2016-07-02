package com.tayjay.augments.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by tayjay on 2016-06-23.
 * Item that is added to player to change themselves.
 */
public interface IAugment
{
    /**
     * Get a list of all body part types this augment can be applied to.
     * @param stack     Augment stack being checked
     * @return          All Types of Parts that allow this augment.
     */
    List<PartType> getParentTypes(ItemStack stack);

    /**
     * Check that this augment can be activated/used by the player.(ex. A leg augment that can only be used if it is on both legs.)
     * @param augment   Augment being checked
     * @param bodyPart  Body Part this augment is on. Use this to check for augments in same inventory IAugmentHolderProvider
     * @param player    Player that augment will be applied. Check other body parts for augments IBodyPartProvider
     * @return
     */
    boolean validate(ItemStack augment, ItemStack bodyPart, EntityPlayer player);

    float getEnergyUse(ItemStack stack);
}
