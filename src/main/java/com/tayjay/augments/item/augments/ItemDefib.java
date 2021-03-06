package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.events.ILivingDeath;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.ChatHelper;
import com.tayjay.augments.util.ItemNBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.List;

/**
 * Created by tayjay on 2016-06-29.
 */
public class ItemDefib extends ItemAugment implements ILivingDeath
{
    public ItemDefib(String name)
    {
        super(name,3, PartType.TORSO, 9001, "Saves player from death once.");
    }

    @Override
    public boolean validate(ItemStack augment, EntityPlayer player)
    {
        if(ItemNBTHelper.getBoolean(augment,"augmentDead",false))
            return false;
        return super.validate(augment, player);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        super.addInformation(stack, playerIn, tooltip, advanced);
        if(ItemNBTHelper.getCompound(stack,"augmentDead",true)==null)
            ItemNBTHelper.setBoolean(stack,"augmentDead",false);
        tooltip.add("Augment Dead:"+ItemNBTHelper.getBoolean(stack,"augmentDead",false));
    }

    @Override
    public void activate(ItemStack augment, EntityPlayer player)
    {
        if(isActive(augment,player))
        {
            ChatHelper.send(player,"Disabling Defibrillator!");
            CapHelper.getPlayerDataCap(player).setAugmentActive(false);
        }
        else
        {
            ChatHelper.send(player,"Defibrillator Enabled!");
            CapHelper.getPlayerDataCap(player).setAugmentActive(true);
        }
    }

    @Override
    public void onDeath(ItemStack augment, EntityPlayer dieing, LivingDeathEvent event)
    {
        if(validate(augment,dieing))
        {
            IPlayerDataProvider data = CapHelper.getPlayerDataCap(dieing);
            data.setCurrentEnergy(0);
            dieing.setHealth(dieing.getMaxHealth());
            ChatHelper.send(dieing, "User is dieing, activating augment!");
            event.setCanceled(true);
            CapHelper.getAugmentDataCap(augment).setActive(false);
            ItemNBTHelper.setBoolean(augment,"augmentDead",true);
        }
    }
}
