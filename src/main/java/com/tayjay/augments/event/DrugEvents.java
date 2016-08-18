package com.tayjay.augments.event;

import com.tayjay.augments.Augments;
import com.tayjay.augments.util.CapHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Created by tayjay on 2016-07-31.
 * Event handler for drug dependencies in Augments.
 * When initialized, this will look at the config file to determine if the mod has the drugs enabled.
 * If the user decides not to add this element to the game (May force it in future) then this will not be created and will not run anything.
 */
public class DrugEvents
{

    @SubscribeEvent
    public void drugTick(TickEvent.PlayerTickEvent event)
    {
        if(!event.player.worldObj.isRemote && event.player.worldObj.getTotalWorldTime() %10 == 0)
            if(!event.player.isCreative())
                CapHelper.getPlayerDataCap(event.player).doDrugTick();
    }
}
