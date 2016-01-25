package com.tayjay.augments.augment;

import com.tayjay.augments.augment.interfaces.IAugment;
import com.tayjay.augments.augment.interfaces.IAugmentPlayerTick;
import com.tayjay.augments.augment.interfaces.IPlayerAugment;
import com.tayjay.augments.init.ModItems;
import com.tayjay.augments.item.ItemAugment;
import com.tayjay.augments.lib.Names;
import com.tayjay.augments.util.ChatHelper;
import com.tayjay.augments.util.LogHelper;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * Created by tayjm_000 on 2016-01-24.
 */
public class AugmentPotionEffect extends ItemAugment implements IAugment,IAugmentPlayerTick,IPlayerAugment
{
    public AugmentPotionEffect()
    {
        super();
        this.setUnlocalizedName("potionEffect");
        this.maxStackSize = 1;
        ModItems.register(this);
    }

    @Override
    public String getAugmentName()
    {
        return "potionEffect";
    }

    @Override
    public IAugment getAugment()
    {
        return this;
    }

    @Override
    public byte getTier()
    {
        return 1;
    }

    @Override
    public void onAdd(ItemStack stack, EntityLivingBase entity)
    {
        if(entity!=null && entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            if (player != null)
            {
                player.addPotionEffect(new PotionEffect(Potion.invisibility.getId(), 40));
            } else
                LogHelper.error("Player is null in AugmentPotionEffect");
        }
    }

    @Override
    public void onRemove(ItemStack stack, EntityLivingBase entity)
    {
        if(entity!=null && entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            if (player != null)
            {
                player.removePotionEffect(Potion.invisibility.getId());
            } else
                LogHelper.error("Player is null in AugmentPotionEffect");
        }
    }


    @Override
    public void writeAugmentToNBT(NBTTagCompound tag)
    {

    }

    @Override
    public void readAugmentFromNBT(NBTTagCompound tag)
    {

    }



    @Override
    public EntityPlayer getPlayer()
    {
        return null;
    }


    @Override
    public void onTick(ItemStack stack, EntityPlayer player)
    {
        if (stack.getItemDamage()==0) {
            player.addPotionEffect(new PotionEffect(Potion.invisibility.id,40,0));
        }
    }
}
