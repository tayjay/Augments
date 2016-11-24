package com.tayjay.augments.event;

import com.tayjay.augments.api.events.*;
import com.tayjay.augments.api.item.*;
import com.tayjay.augments.capability.AugmentDataImpl;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.items.IItemHandler;

/**
 * Created by tayjay on 2016-06-25.<br/>
 * Handle events and actions of augments and body parts on players.
 */
public class AugmentEvents
{
    /* Removed with 0.1.2.0 update
    @Deprecated
    @SubscribeEvent
    public void createAugmentHolderItem(AttachCapabilitiesEvent.Item event)
    {
        if(event.getItem() instanceof IAugmentHolder)
        {
            event.addCapability(AugmentHolderImpl.Provider.NAME,new AugmentHolderImpl.Provider(((IAugmentHolder) event.getItem()).getHolderSize(event.getItemStack())));
        }
    }
    */

    @SubscribeEvent
    public void createAugmentDataItem(AttachCapabilitiesEvent.Item event)
    {
        if (event.getItem() instanceof IAugment)
        {
            event.addCapability(AugmentDataImpl.Provider.NAME,new AugmentDataImpl.Provider(((IAugment)event.getItem()).activeWhenCreated(event.getItemStack())));
        }
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event)//Tick Augments on the player
    {
        if(event.player.worldObj.isRemote)//Only running server side
            return;
        IItemHandler augments = CapHelper.getPlayerBodyCap(event.player).getAugments();
        for(int i = 0;i<augments.getSlots();i++)
        {
            if(augments.getStackInSlot(i)!=null)
            {
                ((IAugment)augments.getStackInSlot(i).getItem()).tickAugment(augments.getStackInSlot(i),event);
            }
        }
    }

    @SubscribeEvent
    public void handleRecharge(TickEvent.PlayerTickEvent event)
    {
        if(event.player.worldObj.isRemote)
            return;
        if(event.player.worldObj.getTotalWorldTime()%10==0)
        {
            CapHelper.getPlayerDataCap(event.player).rechargeTick();
            //CapHelper.getPlayerDataCap(event.player).sync((EntityPlayerMP) event.player);
        }
    }

    @SubscribeEvent
    public void syncEnergy(TickEvent.PlayerTickEvent event)
    {
        if(event.player.worldObj.isRemote)
            return;
        if(event.player.worldObj.getTotalWorldTime()%5==0)
        {
            CapHelper.getPlayerDataCap(event.player).sync((EntityPlayerMP) event.player);
        }
    }

    @SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event)
    {
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        ItemStack eyes = CapHelper.getPlayerBodyCap(Minecraft.getMinecraft().thePlayer).getStackByPart(PartType.EYES);
        if(eyes !=null && eyes.getItem() instanceof IHUDProvider)
            ((IHUDProvider)eyes.getItem()).drawWorldElements(eyes,event);

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent event)
    {
        ItemStack eyes = CapHelper.getPlayerBodyCap(Minecraft.getMinecraft().thePlayer).getStackByPart(PartType.EYES);
        if(eyes !=null && eyes.getItem() instanceof IHUDProvider)
            ((IHUDProvider)eyes.getItem()).drawHudElements(eyes,event);
    }

    @SubscribeEvent
    public void playerInteract(PlayerInteractEvent event)
    {

    }

    //TODO: Make this more accurate/ change calculation of damage reduction
    //TODO: Instead change base armour of player?
    @SubscribeEvent
    public void armourCheck(LivingHurtEvent event)
    {
        if(event.getEntityLiving() instanceof EntityPlayer && !(((EntityPlayer) event.getEntityLiving()).worldObj.isRemote))
        {
            IItemHandler playerParts = CapHelper.getPlayerBodyCap((EntityPlayer)event.getEntityLiving()).getBodyParts();
            float amountStart = event.getAmount();
            for(int i = 0;i<playerParts.getSlots();i++)
            {
                if(playerParts.getStackInSlot(i) != null && playerParts.getStackInSlot(i).getItem() instanceof IBodyPart)
                {
                    amountStart = amountStart * (1-(((IBodyPart) playerParts.getStackInSlot(i).getItem()).getArmourValue(playerParts.getStackInSlot(i)))/7);
                }
            }
            event.setAmount(amountStart);
        }
    }

    //TODO: BIG ONE!!! When looking though augments to activate. Only do so for available augments within current tier, not entire inventory
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void playerDeath(LivingDeathEvent event)
    {
        if(event.getSource()== DamageSource.outOfWorld)
            return;
        if(event.getEntityLiving() instanceof EntityPlayer && !(((EntityPlayer) event.getEntityLiving()).worldObj.isRemote))
        {
            IItemHandler augs = CapHelper.getPlayerBodyCap((EntityPlayer) event.getEntityLiving()).getAugments();
            for(int i = 0;i<augs.getSlots();i++)
            {
                if (augs.getStackInSlot(i)!=null && augs.getStackInSlot(i).getItem() instanceof ILivingDeath)
                {
                    ((ILivingDeath)augs.getStackInSlot(i).getItem()).onDeath(augs.getStackInSlot(i), (EntityPlayer) event.getEntityLiving(),event);
                }
            }
        }
    }

    @SubscribeEvent
    public void playerAttack(LivingAttackEvent event)
    {
        if(event.getEntityLiving() instanceof EntityPlayer && !event.getEntityLiving().getEntityWorld().isRemote)
        {
            IItemHandler parts = CapHelper.getPlayerBodyCap((EntityPlayer) event.getEntityLiving()).getBodyParts();
            if(parts.getStackInSlot(PartType.TORSO.ordinal())!=null)//ONLY LOOKING THROUGH TORSO
            {
                IItemHandler augs = CapHelper.getPlayerBodyCap((EntityPlayer) event.getEntityLiving()).getAugments();
                for(int i = 0;i<augs.getSlots();i++)
                {
                    if (augs.getStackInSlot(i)!=null && augs.getStackInSlot(i).getItem() instanceof ILivingAttack)
                    {
                        ((ILivingAttack)augs.getStackInSlot(i).getItem()).onAttack(augs.getStackInSlot(i), (EntityPlayer) event.getEntityLiving(),event);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void playerHurt(LivingHurtEvent event)
    {
        if(event.getEntityLiving() instanceof EntityPlayer && !event.getEntityLiving().getEntityWorld().isRemote)
        {
            IItemHandler augs = CapHelper.getPlayerBodyCap((EntityPlayer) event.getEntityLiving()).getAugments();
            for (int i = 0; i < augs.getSlots(); i++)
            {
                if (augs.getStackInSlot(i) != null && augs.getStackInSlot(i).getItem() instanceof ILivingHurt)
                {
                    ((ILivingHurt) augs.getStackInSlot(i).getItem()).onHurt(augs.getStackInSlot(i), (EntityPlayer) event.getEntityLiving(), event);
                }
            }
        }

    }

    @SubscribeEvent
    public void playerPickupXP(PlayerPickupXpEvent event)
    {
        IItemHandler augs = CapHelper.getPlayerBodyCap(event.getEntityPlayer()).getAugments();
        for(int i=0;i<augs.getSlots();i++)
        {
            if (augs.getStackInSlot(i) != null && augs.getStackInSlot(i).getItem() instanceof IPickupXP)
            {
                ((IPickupXP) augs.getStackInSlot(i).getItem()).onPickupXP(augs.getStackInSlot(i), event);
            }
        }
    }

}
