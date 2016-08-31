package com.tayjay.augments.api.item;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Created by tayjay on 2016-06-23.
 * Item that can be added to the player to replace parts of their body.
 */
public interface IBodyPart extends IAugmentHolder
{
    /**
     * Rarity/complexity of this part
     * @param stack This part
     * @return  Tier of part
     */
    int getTier(ItemStack stack);

    /**
     * Display this part on the player. This is accessed in LayerAugments
     * @param stack         This body part
     * @param playerIn      Player to render on
     * @param renderPlayer  Render Player instance to use
     */
    void renderOnPlayer(ItemStack stack, EntityPlayer playerIn, RenderPlayer renderPlayer);

    /**
     * Get what type of body part this is
     * @param stack This stack
     * @return      BodyPart type(EYES,HEAD,TORSO...)
     */
    PartType getPartType(ItemStack stack);

    /**
     * How much protection this provides when worn, similar to armour.
     * @param stack This stack
     * @return      Armour points
     */
    int getArmourValue(ItemStack stack);

    /**
     * Get texture to use when rendering the player. Sensitive for if the player uses the Alex model(smallArms)
     * @param stack         This stack
     * @param hasSmallArms  Whether player has small arms to render, need to different texture in most cases.
     * @return              Texture to use
     */
    ResourceLocation getTexture(ItemStack stack,boolean hasSmallArms);

    /**
     * Go through all augments and tick them on the PlayerTickEvent
     * @param bodyPartStack This stack
     * @param event         Event to tick on
     */
    void tickAllAugments(ItemStack bodyPartStack, TickEvent.PlayerTickEvent event);

    /**
     * Check augments in this whether it has the passed augment
     * @param bodyPartStack This body part's stack
     * @param augment   Augment to check for
     * @return          Whether this has the augment
     */
    boolean hasAugment(ItemStack bodyPartStack, IAugment augment);

}
