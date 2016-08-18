package com.tayjay.augments.item.augments;

import com.sun.scenario.DelayedRunnable;
import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.events.IActivate;
import com.tayjay.augments.api.events.IHUDProvider;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.network.packets.PacketChangeEnergy;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.ChatHelper;
import com.tayjay.augments.util.ReflectHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.util.List;

/**
 * Created by tayjay on 2016-06-30.
 */
public class ItemSonar extends ItemAugment implements IActivate
{

    public ItemSonar(String name)
    {
        super(name,1);
        acceptedParts.add(PartType.EYES);
    }

    @Override
    public KeyBinding getKey()
    {
        return null;
    }

    @Override
    public void activate(ItemStack stack,ItemStack bodyPart, EntityPlayer player)
    {
        if(validate(stack,null,player))
        {
            CapHelper.getPlayerDataCap(player).removeEnergy(getEnergyUse(stack));
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
    public boolean validate(ItemStack augment, ItemStack bodyPart, EntityPlayer player)
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