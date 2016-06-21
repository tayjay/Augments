package com.tayjay.augments.augment.handlers;

import com.tayjay.augments.augment.interfaces.IAugmentRender;
import com.tayjay.augments.augment.interfaces.IBodyPart;
import com.tayjay.augments.augment.interfaces.IHUDProvider;
import com.tayjay.augments.handler.PlayerHandler;
import com.tayjay.augments.inventory.InventoryAugmentPlayer;
import com.tayjay.augments.inventory.InventoryBodyPart;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.*;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

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
        InventoryAugmentPlayer augments = PlayerHandler.getPlayerAugmentInventory(player); //Gets the body parts

        dispatchRenders(augments,event);


    }

    private void dispatchRenders(InventoryAugmentPlayer inv, RenderPlayerEvent event) {
        //event.renderer.modelBipedMain.bipedLeftArm.isHidden = false;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(stack != null) {
                Item item = stack.getItem();

                    GL11.glPushMatrix();
                    GL11.glColor4f(1F, 1F, 1F, 1F);
                    ((IBodyPart) stack.getItem()).render(stack, event);
                    GL11.glPopMatrix();
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRenderWorld(RenderWorldLastEvent event)
    {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        ItemStack stack = PlayerHandler.getPlayerAugmentInventory(player).inventory[InventoryAugmentPlayer.SLOT_EYES];
        if(stack!=null && stack.getItem() instanceof IHUDProvider)
        {
            GL11.glPushMatrix();
            GL11.glColor4f(1F, 1F, 1F, 1F);
            ((IHUDProvider) stack.getItem()).displayInWorldElements(stack, event);
            GL11.glPopMatrix();
            //ItemStack[] inv = new InventoryBodyPart(PlayerHandler.getPlayerAugmentInventory(player).inventory[InventoryAugmentPlayer.SLOT_EYES]).getAugmentStacks();
            /*
            for (ItemStack stack : inv)
            {
                if (stack != null)
                {
                    if (stack.getItem() instanceof IHUDProvider)
                    {
                        GL11.glPushMatrix();
                        GL11.glColor4f(1F, 1F, 1F, 1F);
                        ((IHUDProvider) stack.getItem()).displayInWorldElements(stack, event);
                        GL11.glPopMatrix();
                    }
                }
            }
            */
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onGameOverlayRender(RenderGameOverlayEvent.Post event)
    {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        ItemStack stack = PlayerHandler.getPlayerAugmentInventory(player).inventory[InventoryAugmentPlayer.SLOT_EYES];
        if(stack!=null && stack.getItem() instanceof IHUDProvider)
        {
            GL11.glPushMatrix();
            GL11.glColor4f(1F, 1F, 1F, 1F);
            ((IHUDProvider) stack.getItem()).displayGameOverlay(stack, event);
            GL11.glPopMatrix();
        /*
        //Only look through the eyes section of the body.
        //TODO: Change this to include other body parts if not just eyes access IHUDProvider
        if(PlayerHandler.getPlayerAugmentInventory(player).inventory[InventoryAugmentPlayer.SLOT_EYES]!=null)
        {
            ItemStack[] inv = new InventoryBodyPart(PlayerHandler.getPlayerAugmentInventory(player).inventory[InventoryAugmentPlayer.SLOT_EYES]).getAugmentStacks();
            for (ItemStack stack : inv)
            {
                if (stack != null)
                {
                    if (stack.getItem() instanceof IHUDProvider)
                    {
                        GL11.glPushMatrix();
                        GL11.glColor4f(1F, 1F, 1F, 1F);
                        ((IHUDProvider) stack.getItem()).displayGameOverlay(stack, event);
                        GL11.glPopMatrix();
                    }
                }
            }
            */
        }
    }
}
