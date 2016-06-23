package com.tayjay.augments.item.BodyParts;

import com.tayjay.augments.augment.handlers.AugmentModels;
import com.tayjay.augments.init.ModItems;
import com.tayjay.augments.lib.Reference;
import com.tayjay.augments.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;

import java.util.List;

/**
 * Created by tayjay on 2016-06-20.
 */
public class ItemBodySet extends ItemBodyPart
{

    public ItemBodySet(Type type, int tier, int augmentCapacity,String name)
    {
        super(type, tier, augmentCapacity);
        this.setUnlocalizedName(name);

        ModItems.register(this);
    }

    @Override
    public String getName()
    {
        return this.getUnlocalizedName();
    }




    @Override
    public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List list, boolean p_77624_4_)
    {
        super.addInformation(stack, p_77624_2_, list, p_77624_4_);
        list.add("Type: "+this.type);
        list.add("Tier: "+this.tier);
        list.add("Cap: "+this.augmentCapacity);
    }

    @Override
    public void render(ItemStack stack, RenderPlayerEvent event)
    {

        RenderUtil.renderModelOnPlayer(event, new ModelBiped(),new ResourceLocation("augments:/textures/entity/EntityCyborgTier2.png"),this.type);
    }
}
