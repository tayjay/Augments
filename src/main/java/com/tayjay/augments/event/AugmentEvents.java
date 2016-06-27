package com.tayjay.augments.event;

import com.tayjay.augments.api.capabilities.impl.AugmentHolderImpl;
import com.tayjay.augments.api.events.IAugmentHolder;
import com.tayjay.augments.api.events.IHUDProvider;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.IItemHandler;

/**
 * Created by tayjay on 2016-06-25.<br/>
 * Handle events and actions of augments and body parts on players.
 */
public class AugmentEvents
{

    @SubscribeEvent
    public void createAugmentHolderItem(AttachCapabilitiesEvent.Item event)
    {
        if(event.getItem() instanceof IAugmentHolder)
        {
            event.addCapability(AugmentHolderImpl.Provider.NAME,new AugmentHolderImpl.Provider(((IAugmentHolder) event.getItem()).getHolderSize(event.getItemStack())));
        }
    }

    @SubscribeEvent
    public void playerTick(PlayerEvent.LivingUpdateEvent event)
    {

    }

    @SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event)
    {

    }

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent event)
    {
        IItemHandler parts = CapHelper.getPlayerPartsCap(Minecraft.getMinecraft().thePlayer).getPartsInv();
        for(int i = 0; i<parts.getSlots();i++)
        {
            if(parts.getStackInSlot(i)!=null && parts.getStackInSlot(i).getItem() instanceof IHUDProvider)
                ((IHUDProvider)parts.getStackInSlot(i).getItem()).drawHudElements(parts.getStackInSlot(i),event);
        }
    }

    @SubscribeEvent
    public void playerInteract(PlayerInteractEvent event)
    {

    }

    @SubscribeEvent
    public void playerHurt(LivingHurtEvent event)
    {

    }

    @SubscribeEvent
    public void playerDeath(LivingDeathEvent event)
    {

    }

    @SubscribeEvent
    public void playerAttack(LivingAttackEvent event)
    {

    }

}
