package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.events.ILivingHurt;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Created by tayjay on 2016-07-03.
 */
public class ItemOvershield extends ItemAugment implements ILivingHurt
{
    public ItemOvershield(String name)
    {
        super(name,1,PartType.TORSO,2,"Create a shield around the player");

    }

    @Override
    public void tickAugment(ItemStack augmentStack, TickEvent.PlayerTickEvent event)
    {

    }



    @Override
    public void onHurt(ItemStack augment, EntityPlayer player, LivingHurtEvent event)
    {
        float drainAmount;
        if(validate(augment,player)&& isActive(augment,player))
        {
            if(!event.getSource().damageType.equals("void"))
            {
                drainAmount = event.getAmount()/6;
                if(CapHelper.getPlayerDataCap(player).removeEnergy(drainAmount))
                    event.setAmount(0);
            }
        }
    }
}
