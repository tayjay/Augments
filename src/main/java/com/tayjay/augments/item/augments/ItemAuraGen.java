package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

/**
 * Created by tayjay on 2016-08-31.
 */
public class ItemAuraGen extends ItemAugment
{
    PotionEffect effect;
    public ItemAuraGen(String name)
    {
        super(name,2, PartType.TORSO, 0.02f, "Generate an area of effect around the player.");
    }

    @Override
    public boolean validate(ItemStack augment, EntityPlayer player)
    {
        if(CapHelper.getPlayerDataCap(player).getCurrentEnergy()<0.2f)
            return false;
        return super.validate(augment, player);
    }

    @Override
    public void tickAugment(ItemStack augmentStack, TickEvent.PlayerTickEvent event)
    {
        if(validate(augmentStack,event.player))
        {
            World world = event.player.worldObj;

            AxisAlignedBB radius = event.player.getEntityBoundingBox().expand(10, 3, 10);
            if (world.getEntitiesWithinAABBExcludingEntity(event.player, radius) != null)
            {
                List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(event.player, radius);
                for (Entity e : entities)
                {
                    if (e instanceof EntityLivingBase)
                    {
                        EntityLivingBase living = (EntityLivingBase) e;
                        if(living.getActivePotionEffect(MobEffects.GLOWING)==null)
                        {
                            living.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 40));//Make this different with metadata
                            CapHelper.getPlayerDataCap(event.player).removeEnergy(0.09f);
                        }
                        else if(living.getActivePotionEffect(MobEffects.GLOWING).getDuration()<5)
                        {
                            living.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 40));//Make this different with metadata
                            CapHelper.getPlayerDataCap(event.player).removeEnergy(0.09f);
                        }
                    }
                }
            }
        }

    }

    @Override
    public boolean activeWhenCreated(ItemStack stack)
    {
        return false;
    }
}
