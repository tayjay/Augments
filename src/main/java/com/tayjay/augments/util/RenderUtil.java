package com.tayjay.augments.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;

/**
 * Created by tayjay on 2016-06-25.
 */
public class RenderUtil
{
    public static HashMap<EntityPlayer, RenderPlayer> playerRenders = new HashMap<EntityPlayer, RenderPlayer>();
    private static RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();

    public static void addRenderPlayer(EntityPlayer player, RenderPlayer render)
    {
        if(playerRenders.containsKey(player))
            return;
        playerRenders.put(player,render);
    }


    public static RenderPlayer getRenderPlayer(EntityPlayer player)
    {
        if(playerRenders.containsKey(player))
            return playerRenders.get(player);
        return null;
    }

    private static void drawItemStack(ItemStack stack, int x, int y, float scale)
    {
        itemRender.renderItemIntoGUI(stack,x,y);
    }
}
