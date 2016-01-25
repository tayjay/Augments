package com.tayjay.augments.properties;

import com.tayjay.augments.util.LogHelper;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

/**
 * Created by tayjm_000 on 2016-01-24.
 */
public class PropertyHandler
{

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onConstructingEntity(EntityEvent.EntityConstructing event)
    {

        if (event.entity instanceof EntityPlayer && PlayerAugmentProperties.get((EntityPlayer) event.entity) == null)
        {
            PlayerAugmentProperties.register((EntityPlayer) event.entity);
        }



    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
            if (event.entity instanceof EntityPlayer)
            {
                if (PlayerAugmentProperties.get((EntityPlayer) event.entity) == null)
                {
                    LogHelper.info("Registering PlayerAugmentProperties to " + event.entity.getCommandSenderName());
                    PlayerAugmentProperties.register((EntityPlayer) event.entity);
                    if(PlayerAugmentProperties.get((EntityPlayer)event.entity)==null)
                        LogHelper.error("Y U No Register Extended Properties?");
                } else
                {
                    //PlayerAugmentProperties.loadProxyData((EntityPlayer)event.entity);
                    LogHelper.info(event.entity.getCommandSenderName() + " already has PlayerAugmentProperties.");
                }
            }
    }
}
