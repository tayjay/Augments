package com.tayjay.augments.event;

import com.tayjay.augments.api.events.ILivingDeath;
import com.tayjay.augments.api.events.IPlayerTick;
import com.tayjay.augments.api.events.IRenderTask;
import com.tayjay.augments.api.item.IEnergySupply;
import com.tayjay.augments.capability.AugmentHolderImpl;
import com.tayjay.augments.api.item.IAugmentHolder;
import com.tayjay.augments.api.events.IHUDProvider;
import com.tayjay.augments.api.item.IBodyPart;
import com.tayjay.augments.init.ModItems;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.network.packets.PacketAugElytra;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ElytraSound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.DamageSource;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import scala.collection.parallel.ParIterableLike;

import java.util.*;

/**
 * Created by tayjay on 2016-06-25.<br/>
 * Handle events and actions of augments and body parts on players.
 */
public class AugmentEvents
{

    //TODO: *******Event to sort all augments by type so it is only needed to do the O(n^2) one per tick instead of every event*******


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
    public void handleRecharge(TickEvent.PlayerTickEvent event) //TODO: Only do recharge when energy not being drained/just used energy(cool down)
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
                    amountStart = amountStart * (1-(((IBodyPart) playerParts.getStackInSlot(i).getItem()).getArmour(playerParts.getStackInSlot(i)))/7);
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

    /*
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void activateAugElytra(TickEvent.PlayerTickEvent event)
    {
        if(!event.player.worldObj.isRemote || !(event.player instanceof EntityPlayerSP))
            return;

        EntityPlayerSP playerSP = (EntityPlayerSP)event.player;
        boolean flag = playerSP.movementInput.jump;
        if ( playerSP.movementInput.jump && !flag && !playerSP.onGround && playerSP.motionY < 0.0D && !playerSP.isElytraFlying() && !playerSP.capabilities.isFlying)
        {
            ItemStack part = CapHelper.getPlayerPartsCap(playerSP).getPartsInv().getStackInSlot(2);
            if (part != null)
            {
                IItemHandler augments = CapHelper.getAugHolderCap(part).getAugments();

                for (int i = 0; i < augments.getSlots(); i++)
                {
                    if (augments.getStackInSlot(i) != null && augments.getStackInSlot(i).getItem() == ModItems.augElytra)
                    {
                        NetworkHandler.INSTANCE.sendToServer(new PacketAugElytra(playerSP));
                        LogHelper.info("Sending AugElytraPacket!");
                        Minecraft.getMinecraft().getSoundHandler().playSound(new ElytraSound(playerSP));
                    }
                }
            }

        }

    }
    */

}
