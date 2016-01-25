package com.tayjay.augments.augment.handlers;

import com.tayjay.augments.augment.event.AugmentChangeEvent;
import com.tayjay.augments.augment.interfaces.IAugment;
import com.tayjay.augments.augment.interfaces.IAugmentPlayerTick;
import com.tayjay.augments.augment.interfaces.IPlayerAugment;
import com.tayjay.augments.inventory.InventoryAugmentPlayer;
import com.tayjay.augments.inventory.SlotChangeEvent;
import com.tayjay.augments.properties.EntityAugmentProperties;
import com.tayjay.augments.properties.PlayerAugmentProperties;
import com.tayjay.augments.util.ChatHelper;
import com.tayjay.augments.util.LogHelper;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

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
