package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.events.IActivate;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.network.packets.PacketChangeEnergy;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.ChatHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

/**
 * Created by tayjay on 2016-06-30.
 */
public class ItemSonar extends ItemAugment implements IActivate
{

    public ItemSonar(String name)
    {
        super(name,1,PartType.EYES,1,"See mobs around you");
    }



    @Override
    public void activate(ItemStack stack, EntityPlayer player)
    {
        if(validate(stack, player))
        {
            //CapHelper.getPlayerDataCap(player).removeEnergy(getEnergyUse(stack));
            NetworkHandler.sendToServer(new PacketChangeEnergy(getEnergyUse(stack), PacketChangeEnergy.EnergyType.CURRENT));
            double posX = player.posX;
            double posY = player.posY;
            double posZ = player.posZ;
            int range = 20;
            AxisAlignedBB box = new AxisAlignedBB(posX - range, posY - range, posZ - range, posX + range, posY + range, posZ + range);
            List<Entity> entities = player.worldObj.getEntitiesWithinAABBExcludingEntity(Minecraft.getMinecraft().thePlayer, box);

            ChatHelper.send(player,"Pinging "+entities.size()+" mobs!");

        }
        else
        {
            ChatHelper.send(player, "Not enough energy!");
        }
    }

    @Override
    public boolean isActive(ItemStack augment, ItemStack bodyPart, EntityPlayer player)
    {
        IPlayerDataProvider playerDataProvider = CapHelper.getPlayerDataCap(player);
        return playerDataProvider.getCurrentAugment().getItem()==this && playerDataProvider.isAugmentActive();
    }

    @Override
    public boolean validate(ItemStack augment, EntityPlayer player)
    {
        IPlayerDataProvider playerData = CapHelper.getPlayerDataCap(player);
        if(!playerData.validate())
            return false;
        if(playerData.getCurrentEnergy()>=getEnergyUse(augment))
        {
            return true;
        }
        return false;
    }
}
