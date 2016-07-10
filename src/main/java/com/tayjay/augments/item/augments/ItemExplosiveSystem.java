package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.events.IActivate;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.network.packets.PacketExplode;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-07-08.
 */
public class ItemExplosiveSystem extends ItemAugment implements IActivate
{
    public ItemExplosiveSystem(String name)
    {
        super(name);
        acceptedParts.add(PartType.TORSO);
    }

    @Override
    public KeyBinding getKey()
    {
        return null;
    }

    @Override
    public void activate(ItemStack stack, EntityPlayer player)
    {
        if(player.isSneaking()&&validate(stack,null,player))
        {
            CapHelper.getPlayerDataCap(player).removeEnergy(getEnergyUse(stack));
            if(!player.capabilities.disableDamage)
            {
                player.capabilities.disableDamage = true;
                NetworkHandler.INSTANCE.sendToServer(new PacketExplode(player, player.posX, player.posY, player.posZ, 10, false));
                player.capabilities.disableDamage = false;
            }
            else
            {
                NetworkHandler.INSTANCE.sendToServer(new PacketExplode(player, player.posX, player.posY, player.posZ, 10, false));
            }
        }
    }

    @Override
    public boolean validate(ItemStack augment, ItemStack bodyPart, EntityPlayer player)
    {
        IPlayerDataProvider playerData = CapHelper.getPlayerDataCap(player);
        if(playerData.getCurrentEnergy()>=getEnergyUse(augment))
        {
            return true;
        }
        return false;
    }

    @Override
    public float getEnergyUse(ItemStack stack)
    {
        return 3;
    }
}
