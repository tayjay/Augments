package com.tayjay.augments.augment.handlers;

import com.tayjay.augments.AugmentsCore;
import com.tayjay.augments.augment.ModelAntennaTest;
import com.tayjay.augments.augment.interfaces.IAugmentRender;
import com.tayjay.augments.augment.interfaces.IHUDProvider;
import com.tayjay.augments.handler.EventHandlerEntity;
import com.tayjay.augments.handler.EventHandlerNetwork;
import com.tayjay.augments.handler.PlayerHandler;
import com.tayjay.augments.inventory.InventoryAugmentPlayer;
import com.tayjay.augments.properties.PlayerAugmentProperties;
import com.tayjay.augments.util.LogHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.lwjgl.opengl.GL11;

import java.util.Set;

/**
 * Handler for all Client side augment events.
 * Created by tayjm_000 on 2016-01-21.
 */
public class ClientAugmentHandler
{
    private long renderTickLast;
    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent.Specials.Post event)
    {
        EntityPlayer player = event.entityPlayer;
        InventoryAugmentPlayer augments = PlayerHandler.getPlayerAugments(player);

        dispatchRenders(augments,event);


    }

    private void dispatchRenders(InventoryAugmentPlayer inv, RenderPlayerEvent event) {
        //event.renderer.modelBipedMain.bipedLeftArm.isHidden = false;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(stack != null) {
                Item item = stack.getItem();

                if(item instanceof IAugmentRender) {
                    GL11.glPushMatrix();
                    GL11.glColor4f(1F, 1F, 1F, 1F);
                    ((IAugmentRender) stack.getItem()).doRender(stack, event);
                    GL11.glPopMatrix();
                }
            }
        }
    }



    @SubscribeEvent
    public void onRenderPlayerPre(RenderPlayerEvent.Pre event)
    {
        InventoryAugmentPlayer augments = PlayerHandler.getPlayerAugments(event.entityPlayer);
        //dispatchRenders(augments,event);
    }

    @SubscribeEvent
    public void onRenderLiving(RenderLivingEvent.Pre event)
    {
        if(event.entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.entity;
            InventoryAugmentPlayer inv = PlayerHandler.getPlayerAugments(player);
            for(int i = 0; i < inv.getSizeInventory(); i++) {
                ItemStack stack = inv.getStackInSlot(i);
                if(stack != null) {
                    Item item = stack.getItem();

                    if(item instanceof IAugmentRender) {
                        GL11.glPushMatrix();
                        GL11.glColor4f(1F, 1F, 1F, 1F);
                        ((IAugmentRender) stack.getItem()).doRender(stack, event);
                        GL11.glPopMatrix();
                    }
                }
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRenderWorld(RenderWorldLastEvent event)
    {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        InventoryAugmentPlayer inv = PlayerHandler.getPlayerAugments(player);
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(stack != null) {
                Item item = stack.getItem();

                if(item instanceof IHUDProvider) {
                    GL11.glPushMatrix();
                    GL11.glColor4f(1F, 1F, 1F, 1F);
                    ((IHUDProvider) stack.getItem()).displayInWorldElements(stack, event);
                    GL11.glPopMatrix();
                }
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onGameOverlayRender(RenderGameOverlayEvent event)
    {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        InventoryAugmentPlayer inv = PlayerHandler.getPlayerAugments(player);
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(stack != null) {
                Item item = stack.getItem();

                if(item instanceof IHUDProvider) {
                    GL11.glPushMatrix();
                    GL11.glColor4f(1F, 1F, 1F, 1F);
                    ((IHUDProvider) stack.getItem()).displayGameOverlay(stack, event);
                    GL11.glPopMatrix();
                }
            }
        }
    }
}
