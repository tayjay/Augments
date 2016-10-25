package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.events.IActivate;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.network.packets.PacketExplode;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Created by tayjay on 2016-07-08.
 */
public class ItemExplosiveSystem extends ItemAugment
{
    public ItemExplosiveSystem(String name)
    {
        super(name,3,PartType.TORSO,3,"Create an explosion around the player");
    }

    @Override
    public void activate(ItemStack stack, EntityPlayer player)
    {
        if(validate(stack, player))
        {
            CapHelper.getPlayerDataCap(player).removeEnergy(getEnergyUse(stack));
            CapHelper.getPlayerDataCap(player).setAugmentActive(true);
            if(!player.capabilities.disableDamage)
            {
                player.capabilities.disableDamage = true;
                NetworkHandler.sendToServer(new PacketExplode(player, player.posX, player.posY, player.posZ, 10, false));
                player.capabilities.disableDamage = false;
            }
            else
            {
                NetworkHandler.sendToServer(new PacketExplode(player, player.posX, player.posY, player.posZ, 10, false));
            }
        }
    }



    @Override
    public void tickAugment(ItemStack augmentStack, TickEvent.PlayerTickEvent event)
    {
        super.tickAugment(augmentStack, event);
        /*if(CapHelper.getPlayerDataCap(event.player).getCurrentAugment()!=null && CapHelper.getPlayerDataCap(event.player).getCurrentAugment().getItem()==this&&CapHelper.getPlayerDataCap(event.player).isAugmentActive())
            CapHelper.getPlayerDataCap(event.player).setAugmentActive(false);*/
    }

    @Override
    public boolean validate(ItemStack augment, EntityPlayer player)
    {
        IPlayerDataProvider playerData = CapHelper.getPlayerDataCap(player);
        if(playerData.getCurrentAugment()==null || playerData.getCurrentAugment().getItem()!=this)
            return false;
        if(!playerData.validate())
            return false;
        if(playerData.getCurrentEnergy()>=getEnergyUse(augment))
        {
            return true;
        }
        return false;
    }
}
