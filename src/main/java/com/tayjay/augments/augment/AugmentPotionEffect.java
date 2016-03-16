package com.tayjay.augments.augment;

import com.tayjay.augments.augment.interfaces.IAugment;
import com.tayjay.augments.augment.interfaces.IAugmentPlayerTick;
import com.tayjay.augments.augment.interfaces.IPlayerAugment;
import com.tayjay.augments.init.ModItems;
import com.tayjay.augments.item.ItemAugment;
import com.tayjay.augments.util.DummyPlayer;
import com.tayjay.augments.util.LogHelper;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

/**
 * Created by tayjm_000 on 2016-01-24.
 */
public class AugmentPotionEffect extends ItemAugment implements IAugment,IAugmentPlayerTick,IPlayerAugment
{
    private final double attackIncrease = 10;
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

                double defaultAttack = DummyPlayer.PLAYER.getEntityAttribute(SharedMonsterAttributes.attackDamage).getBaseValue();
                player.addPotionEffect(new PotionEffect(Potion.invisibility.getId(), 40));
                player.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(defaultAttack + attackIncrease + 100);
                LogHelper.info("Adding potion effect to " + player.getCommandSenderName());
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
                double defaultAttack = DummyPlayer.PLAYER.getEntityAttribute(SharedMonsterAttributes.attackDamage).getBaseValue();
                player.removePotionEffect(Potion.invisibility.getId());
                player.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0);
                LogHelper.info("Removing potion effect from "+player.getCommandSenderName());
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
    public void onTick(ItemStack augment, EntityPlayer player)
    {
        if (augment.getItemDamage()==0) {
            player.addPotionEffect(new PotionEffect(Potion.invisibility.id,40,0));
            player.addPotionEffect(new PotionEffect(Potion.resistance.id,40,2));
        }
    }

}
