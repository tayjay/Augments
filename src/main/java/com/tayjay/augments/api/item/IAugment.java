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
     * Check that this augment can be activated/used by the player.(ex. A leg augment that can only be used if it is on both legs.)
     * If the augment must be on both sides of body to activate. Only check 1 side and confirm other side has the same augment.
     * @param augment   Augment being checked
     * @param player    Player that augment will be applied.
     * @return          Whether augment can be used.
     */
    boolean validate(ItemStack augment, EntityPlayer player);

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
     * @param event             PlayerTickEvent to work with.
     */
    void tickAugment(ItemStack augmentStack, TickEvent.PlayerTickEvent event);

    //Moved this code from the IActivate class to more generalize augments
    /**
     * Do validation and active augment if it is currently selected.
     * @param augment
     * @param player
     */
    void activate(ItemStack augment, EntityPlayer player);

    /**
     * Check through player data and find if this augment is set to be active
     * @param augment
     * @param player
     * @return
     */
    boolean isActive(ItemStack augment,EntityPlayer player);

    /**
     * What active state the augment should be in when it is created. Used by IAugDataProvider
     * @param stack
     * @return
     */
    boolean activeWhenCreated(ItemStack stack);


}
