package com.tayjay.augments.augment.interfaces;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;

import java.util.Iterator;

/**
 * Created by tayjm_000 on 2016-04-14.
 */
public interface IBodyPart
{
    boolean addAugment(ItemStack bodyPart, ItemStack augment);

    boolean removeAugment(ItemStack bodyPart, ItemStack augment);

    int augmentCapacity();

    String getName();

    int getType();

    float getEnergy();

    void render(ItemStack stack, RenderPlayerEvent event);

    ModelRenderer getModel();

    boolean canAdd(ItemStack bodyStack, EntityLivingBase entity);

    enum Type
    {
        CRANIUM,EYES,TORSO,ARM,LEG;
    }
}
