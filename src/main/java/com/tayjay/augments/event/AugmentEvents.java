package com.tayjay.augments.event;

import com.tayjay.augments.api.events.ILivingDeath;
import com.tayjay.augments.api.events.IPlayerTick;
import com.tayjay.augments.api.events.IRenderTask;
import com.tayjay.augments.api.item.IEnergySupply;
import com.tayjay.augments.capability.AugmentHolderImpl;
import com.tayjay.augments.api.item.IAugmentHolder;
import com.tayjay.augments.api.events.IHUDProvider;
import com.tayjay.augments.api.item.IBodyPart;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.items.IItemHandler;

import java.util.*;

/**
 * Created by tayjay on 2016-06-25.<br/>
 * Handle events and actions of augments and body parts on players.
 */
public class AugmentEvents
{

    //TODO: *******Event to sort all augments by type so it is only needed to do the O(n^2) one per tick instead of every event*******

    public static HashSet<IRenderTask<RenderWorldLastEvent>> extraWorldRendering = new HashSet<IRenderTask<RenderWorldLastEvent>>();

    public static void addExtraWorldRender(IRenderTask<RenderWorldLastEvent> rendering)
    {
        try
        {
            extraWorldRendering.add(rendering);
        }catch(ConcurrentModificationException e)
        {

        }
    }

    public static void removeExtraWorldRender(IRenderTask<RenderWorldLastEvent> rendering)
    {
        try
        {
            extraWorldRendering.remove(rendering);
        }catch(ConcurrentModificationException e)
        {

        }
    }

    public void doExtraWorldRendering(RenderWorldLastEvent event)
    {
        try
        {
            Iterator<IRenderTask<RenderWorldLastEvent>> iterator = extraWorldRendering.iterator();
            while (iterator.hasNext())
            {
                IRenderTask<RenderWorldLastEvent> task = iterator.next();

                if (!task.shouldRemove())
                    task.render(event);
                else
                    removeExtraWorldRender(task);
            }
        }catch (ConcurrentModificationException e)
        {

        }
    }

    @SubscribeEvent
    public void createAugmentHolderItem(AttachCapabilitiesEvent.Item event)
    {
        if(event.getItem() instanceof IAugmentHolder)
        {
            event.addCapability(AugmentHolderImpl.Provider.NAME,new AugmentHolderImpl.Provider(((IAugmentHolder) event.getItem()).getHolderSize(event.getItemStack())));
        }
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event)//TODO: Maybe change this to another living tick event?
    {
        if(event.player.worldObj.isRemote)//Only running server side
            return;
        IItemHandler parts = CapHelper.getPlayerPartsCap(event.player).getPartsInv();
        IItemHandler augments;
        for(int i = 0;i<parts.getSlots();i++)
        {
            if(parts.getStackInSlot(i)!=null)
            {
                augments = CapHelper.getAugHolderCap(parts.getStackInSlot(i)).getAugments();
                for (int j = 0; j < augments.getSlots(); j++)//TODO: Oh Dear God WTF is with all of these O(n^2) operations. FIX THIS SOON!
                {
                    if(augments.getStackInSlot(j)!=null)
                    {
                        if (augments.getStackInSlot(j).getItem() instanceof IPlayerTick)
                        {
                            ((IPlayerTick) augments.getStackInSlot(j).getItem()).onPlayerTick(augments.getStackInSlot(j), parts.getStackInSlot(i), event);
                        }
                    }
                }
                if (parts.getStackInSlot(i).getItem() instanceof IEnergySupply)
                {
                    CapHelper.getPlayerDataCap(event.player).setMaxEnergy(((IEnergySupply) parts.getStackInSlot(i).getItem()).maxEnergy(parts.getStackInSlot(i)));
                }
            }
        }
    }

    @SubscribeEvent
    public void handleRecharge(TickEvent.PlayerTickEvent event)
    {
        if(event.player.worldObj.isRemote)
            return;
        if(event.player.worldObj.getTotalWorldTime()%20==0)
        {
            CapHelper.getPlayerDataCap(event.player).rechargeTick();
            CapHelper.getPlayerDataCap(event.player).sync((EntityPlayerMP) event.player);
        }
    }

    @SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event)
    {
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        IItemHandler parts = CapHelper.getPlayerPartsCap(Minecraft.getMinecraft().thePlayer).getPartsInv();
        for(int i = 0; i<parts.getSlots();i++)
        {

            if(parts.getStackInSlot(i)!=null && parts.getStackInSlot(i).getItem() instanceof IHUDProvider)
            {
                ((IHUDProvider) parts.getStackInSlot(i).getItem()).drawWorldElements(parts.getStackInSlot(i), event);
                doExtraWorldRendering(event);
            }

        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
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
        if(event.getEntityLiving() instanceof EntityPlayer && !(((EntityPlayer) event.getEntityLiving()).worldObj.isRemote))
        {
            IItemHandler playerParts = CapHelper.getPlayerPartsCap((EntityPlayer)event.getEntityLiving()).getPartsInv();
            float amountStart = event.getAmount();
            for(int i = 0;i<playerParts.getSlots();i++)
            {
                if(playerParts.getStackInSlot(i) != null && playerParts.getStackInSlot(i).getItem() instanceof IBodyPart)
                {
                    amountStart = amountStart * (1-(((IBodyPart) playerParts.getStackInSlot(i).getItem()).getTier(playerParts.getStackInSlot(i)))/5);
                }
            }
            event.setAmount(amountStart);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void playerDeath(LivingDeathEvent event)
    {
        if(event.getSource()== DamageSource.outOfWorld)
            return;
        if(event.getEntityLiving() instanceof EntityPlayer && !(((EntityPlayer) event.getEntityLiving()).worldObj.isRemote))
        {
            IItemHandler parts = CapHelper.getPlayerPartsCap((EntityPlayer) event.getEntityLiving()).getPartsInv();
            if(parts.getStackInSlot(2)!=null)//ONLY LOOKING THROUGH TORSO
            {
                IItemHandler augmentsInTorso = CapHelper.getAugHolderCap(parts.getStackInSlot(2)).getAugments();
                for (int i = 0; i < augmentsInTorso.getSlots(); i++)
                {
                    if (augmentsInTorso.getStackInSlot(i) != null && augmentsInTorso.getStackInSlot(i).getItem() instanceof ILivingDeath)
                    {
                        ((ILivingDeath) augmentsInTorso.getStackInSlot(i).getItem()).onDeath(augmentsInTorso.extractItem(i, 1, false), (EntityPlayer) event.getEntityLiving(),event);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void playerAttack(LivingAttackEvent event)
    {

    }

}
