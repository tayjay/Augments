package com.tayjay.augments.api.item;

import com.tayjay.augments.api.events.IAugmentHolder;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-06-23.
 * Item that can be added to the player to replace parts of their body.
 */
public interface IBodyPart extends IAugmentHolder
{
    int getTier(ItemStack stack);

    void renderOnPlayer(ItemStack stack, EntityPlayer playerIn, RenderPlayer renderPlayer);

    PartType getPartType(ItemStack stack);

    void setPartType(ItemStack stack, PartType type);

    //TODO: Check if correct player to wear part
}
