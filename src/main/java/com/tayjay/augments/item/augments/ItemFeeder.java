package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Created by tayjay on 2016-10-22.
 * Use energy to feed the player.
 */
public class ItemFeeder extends ItemAugment
{
    public ItemFeeder(String name)
    {
        super(name, 2, PartType.TORSO, 0.02f, "Use energy to feed the player");
    }

    @Override
    public boolean validate(ItemStack augment, EntityPlayer player)
    {
        if(CapHelper.getPlayerDataCap(player).getCurrentEnergy()< getEnergyUse(augment))
            return false;
        return super.validate(augment, player);
    }

    @Override
    public void tickAugment(ItemStack augmentStack, TickEvent.PlayerTickEvent event)
    {
        EntityPlayer p = event.player;
        if(validate(augmentStack,event.player) && p.getFoodStats().getFoodLevel()<18)
        {
            p.getFoodStats().addStats(10, 10);
            CapHelper.getPlayerDataCap(p).removeEnergy(getEnergyUse(augmentStack));
        }
    }
}
