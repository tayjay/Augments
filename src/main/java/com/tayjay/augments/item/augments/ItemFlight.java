package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.network.packets.PacketActivateAugment;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.ChatHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by tayjay on 2016-08-21.
 */
public class ItemFlight extends ItemAugment
{
    public ItemFlight(String name, float energyUse)
    {
        super(name,1, PartType.TORSO, energyUse,"Allows flight");
    }

    @Override
    public boolean validate(ItemStack augment, EntityPlayer player)
    {
        if(!CapHelper.getAugmentDataCap(augment).isActive())
            return false;
        return super.validate(augment, player);
    }

    @Override
    public void tickAugment(ItemStack augmentStack, TickEvent.PlayerTickEvent event)
    {
        if(validate(augmentStack,event.player))
        {
            event.player.capabilities.isFlying = true;
        }
    }

    @Override
    public void activate(ItemStack augment, EntityPlayer player)
    {
        if(isActive(augment,player))
        {
            CapHelper.getAugmentDataCap(augment).setActive(false);
            ChatHelper.clientMsg("Augment Disabled");
            player.capabilities.isFlying = false;
        }
        else
        {
            CapHelper.getAugmentDataCap(augment).setActive(true);
            ChatHelper.clientMsg("Augment Enabled");
            if(validate(augment,player))
            {
                player.capabilities.isFlying = true;
            }
        }
        if(FMLCommonHandler.instance().getEffectiveSide()== Side.CLIENT)
            NetworkHandler.sendToServer(new PacketActivateAugment());

    }
}
