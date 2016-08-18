package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.events.ILivingDeath;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.ChatHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

/**
 * Created by tayjay on 2016-06-29.
 */
public class ItemDefib extends ItemAugment implements ILivingDeath
{
    public ItemDefib(String name)
    {
        super(name,99);
        acceptedParts.add(PartType.TORSO);
    }

    @Override
    public boolean validate(ItemStack augment, ItemStack bodyPart, EntityPlayer player)
    {
        return super.validate(augment,bodyPart,player);
    }

    @Override
    public void onDeath(ItemStack augment, EntityPlayer dieing, LivingDeathEvent event)
    {
        IPlayerDataProvider data = CapHelper.getPlayerDataCap(dieing);
        data.setCurrentEnergy(0);
        dieing.setHealth(dieing.getMaxHealth());
        ChatHelper.send(dieing,"User is dieing, activating augment!");
        event.setCanceled(true);
    }
}
