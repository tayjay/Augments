package com.tayjay.augments.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent;

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
     * If the augment must be on both sides of body to activate. Only check 1 side and confirm other side has the same augment.
     * @param augment   Augment being checked
     * @param bodyPart  Body Part this augment is on. Use this to check for augments in same inventory IAugmentHolderProvider
     * @param player    Player that augment will be applied.
     * @return          Whether augment can be used.
     */
    boolean validate(ItemStack augment, ItemStack bodyPart, EntityPlayer player);

    /**
     * How much energy the augment uses.
     * Either per-tick or per-use depending on action preformed
     * @param stack This augment
     * @return      Energy use of augment
     */
    float getEnergyUse(ItemStack stack);

    /**
     * I've concluded that most augments will do something on the player tick.
     * So this augment will preform an operation every tick it is in the player.
     * @param augmentStack      This augment
     * @param bodyPartStack     Containing bodypart
     * @param event             PlayerTickEvent to work with.
     */
    void tickAugment(ItemStack augmentStack, ItemStack bodyPartStack, TickEvent.PlayerTickEvent event);


}
