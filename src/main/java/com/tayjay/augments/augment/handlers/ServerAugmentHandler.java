package com.tayjay.augments.augment.handlers;

import com.tayjay.augments.augment.AugmentCyborgArmLeft;
import com.tayjay.augments.augment.event.AugmentChangeEvent;
import com.tayjay.augments.augment.interfaces.IAugmentPlayerTick;
import com.tayjay.augments.handler.EventHandlerEntity;
import com.tayjay.augments.handler.PlayerHandler;
import com.tayjay.augments.inventory.InventoryAugmentPlayer;
import com.tayjay.augments.inventory.SlotChangeEvent;
import com.tayjay.augments.properties.EntityAugmentProperties;
import com.tayjay.augments.properties.PlayerAugmentProperties;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.Set;

/**
 * Handler for all Server side augment events
 * Created by tayjm_000 on 2016-01-21.
 */
public class ServerAugmentHandler
{
    @SubscribeEvent
    public void onAugmentAdd(AugmentChangeEvent.Add event)
    {

    }

    @SubscribeEvent
    public void onAugmentRemove(AugmentChangeEvent.Remove event)
    {

    }

    @SubscribeEvent
    public void onStartTracking(PlayerEvent.StartTracking event)
    {
        if (event.entityPlayer != null && event.entityPlayer instanceof EntityPlayer ){
            EntityTracker et = ((WorldServer) event.entityPlayer.worldObj).getEntityTracker(); // player is the one that sent change to his model
            Set<EntityPlayer> players = et.getTrackingPlayers(event.entityPlayer);
            for(EntityPlayer player : players)
            {
                EventHandlerEntity.syncSchedule.add(player.getEntityId());
            }
        }
    }

    @SubscribeEvent
    public void joinWorld(EntityJoinWorldEvent event)
    {

    }

    @SubscribeEvent
    public void onSlotChanged(SlotChangeEvent event)
    {

    }

    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event)
    {

    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {

    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event)
    {
        if(event.entityLiving instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            InventoryAugmentPlayer augments = PlayerHandler.getPlayerAugments(player);
            for (int a = 0; a < augments.getSizeInventory(); a++) {
                if (augments.getStackInSlot(a) != null
                        && augments.getStackInSlot(a).getItem() instanceof AugmentCyborgArmLeft) {
                    event.ammount -= 500;
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        // player events
        EntityPlayer player = event.player;

        InventoryAugmentPlayer augments = PlayerAugmentProperties.get(player).inventory;
        for (int a = 0; a < augments.getSizeInventory(); a++) {
            if (augments.getStackInSlot(a) != null
                    && augments.getStackInSlot(a).getItem() instanceof IAugmentPlayerTick) {
                ((IAugmentPlayerTick) augments.getStackInSlot(a).getItem()).onTick(augments.getStackInSlot(a), player);
            }
        }


    }

    /**
     * I'm going to try to allow mobs to load themselves with this.
     * @param event
     */
    @SubscribeEvent
    public void onEntityEnterChunck(EntityEvent.EnteringChunk event)
    {

    }

    @SubscribeEvent
    public void onEntityDespawn(LivingSpawnEvent.AllowDespawn event)
    {
        if(event.entity instanceof EntityLiving)
        {
            EntityAugmentProperties properties = EntityAugmentProperties.get((EntityLiving) event.entity);
            if(properties==null)
                return;
            if(!properties.canDespawn())
            {
                event.setResult(Event.Result.DENY);
                return;
            }

        }
    }

}
