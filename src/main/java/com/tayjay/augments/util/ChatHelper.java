package com.tayjay.augments.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.server.FMLServerHandler;

/**
 * Created by tayjay on 2016-06-29.
 */
public class ChatHelper
{

    /**
     * Send a chat message to the world.
     * @param msg Message to be sent
     */
    public static void send(String msg) //Send to world
    {
        MinecraftServer mcServer = FMLServerHandler.instance().getServer();
        mcServer.addChatMessage(new TextComponentString(msg));
    }

    /**
     * Send a chat message to a player.
     * @param player Recipient of message
     * @param msg Message to be sent.
     */
    public static void send(EntityPlayer player, String msg) //Send to player
    {
        if(player!=null)
        {
            player.addChatMessage(new TextComponentString(msg));
            //player.addChatComponentMessage(new ChatComponentText(msg));
        }
    }

    public static void clientMsg(String msg)
    {
        if(FMLCommonHandler.instance().getEffectiveSide()== Side.CLIENT)
            Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString(msg));
    }

    /**
     * Alters color of a ITextComponent and returns it.
     * Returning the param allows for a chat message to be constructed and colored in one line.
     */
    public static ITextComponent modifyColor(ITextComponent chat, TextFormatting format)
    {
        if (format.isColor())
        {
            chat.getStyle().setColor(format);
        }
        return chat;
    }
}
