package com.tayjay.augments.properties;

import com.tayjay.augments.handler.EventHandlerEntity;
import com.tayjay.augments.handler.EventHandlerNetwork;
import com.tayjay.augments.handler.PlayerHandler;
import com.tayjay.augments.util.LogHelper;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

/**
 * Created by tayjm_000 on 2016-01-24.
 */
public class PropertyHandler
{

    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event)
    {
            if (event.entity instanceof EntityPlayer&&event.entity!=null)
            {
                if (PlayerAugmentProperties.get((EntityPlayer) event.entity) == null)
                {
                    LogHelper.info("Registering PlayerAugmentProperties");
                    PlayerAugmentProperties.register((EntityPlayer) event.entity);
                    if(PlayerAugmentProperties.get((EntityPlayer)event.entity)==null)
                        LogHelper.error("Y U No Register Extended Properties?");
                } else
                {
                    /*
                    PlayerAugmentProperties.loadProxyData((EntityPlayerMP)event.entity);
                    */
                }

            }
    }


    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if(event.entity instanceof EntityPlayer&&event.entity!=null)
        {
            if (PlayerAugmentProperties.get((EntityPlayer) event.entity) != null)
            {
                LogHelper.info("Syncing player data for "+event.entity.getCommandSenderName());
                LogHelper.info(event.entity.getEntityData());
                EventHandlerEntity.syncSchedule.add(event.entity.getEntityId());
            }
        }
    }
}
