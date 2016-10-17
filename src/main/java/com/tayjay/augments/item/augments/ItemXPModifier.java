package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.events.IPickupXP;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;

/**
 * Created by tayjay on 2016-10-16.
 * This is an augment that will apply a multiplier to any xp picked up while it is active.
 */
public class ItemXPModifier extends ItemAugment implements IPickupXP
{
    private float multiplier;
    public ItemXPModifier(String name)
    {
        super(name, 1, PartType.HEAD, 0.02f, "Applies a multiplier to xp pickups");
        multiplier = -100;
    }

    @Override
    public void onPickupXP(ItemStack augment, PlayerPickupXpEvent event)
    {
        if(validate(augment,event.getEntityPlayer()))
        {
            CapHelper.getPlayerDataCap(event.getEntityPlayer()).removeEnergy(getEnergyUse(augment));
            event.getOrb().xpValue*=multiplier;
        }
    }
}
