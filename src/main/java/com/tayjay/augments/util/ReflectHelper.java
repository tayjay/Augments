package com.tayjay.augments.util;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by tayjay on 2016-06-26.
 * Get Deobf names from MCP Mapping Viewer
 */
public class ReflectHelper
{
    public static void changeRenderPlayer(String id, RenderPlayer renderPlayer)
    {
        Map<String, RenderPlayer> skinMapReflect = ReflectionHelper.getPrivateValue(RenderManager.class, Minecraft.getMinecraft().getRenderManager(),"skinMap","field_178636_l","l");
        skinMapReflect.put(id,renderPlayer);
        //ReflectionHelper.setPrivateValue(RenderManager.class,Minecraft.getMinecraft().getRenderManager(),skinMapReflect,"skinMap","field_178636_l");
    }

    public static void getRenderPlayer()
    {
        Map<String, RenderPlayer> skinMapReflect = ReflectionHelper.getPrivateValue(RenderManager.class, Minecraft.getMinecraft().getRenderManager(),"skinMap","field_178636_l","l");
    }

    public static void setCapeLocation(AbstractClientPlayer player, ResourceLocation location)
    {
        NetworkPlayerInfo networkPlayerInfo = Minecraft.getMinecraft().getConnection().getPlayerInfo(player.getUniqueID());
        Map<MinecraftProfileTexture.Type, ResourceLocation> playerTextures = ReflectionHelper.getPrivateValue(NetworkPlayerInfo.class,networkPlayerInfo,"playerTextures");
        playerTextures.put(MinecraftProfileTexture.Type.CAPE,location);
    }

    public static void resetTextures(AbstractClientPlayer player)
    {
        NetworkPlayerInfo networkPlayerInfo = Minecraft.getMinecraft().getConnection().getPlayerInfo(player.getUniqueID());
        Method method = ReflectionHelper.findMethod(NetworkPlayerInfo.class,networkPlayerInfo,new String[]{"loadPlayerTextures"});
        ReflectionHelper.setPrivateValue(NetworkPlayerInfo.class,networkPlayerInfo,false,"playerTexturesLoaded");
        if(method!=null)
            try
            {
                method.invoke(networkPlayerInfo);
            } catch (IllegalAccessException e)
            {
                e.printStackTrace();
            } catch (InvocationTargetException e)
            {
                e.printStackTrace();
            }
    }

    public static void setDisplayName(AbstractClientPlayer player)
    {
        NetworkPlayerInfo networkPlayerInfo = Minecraft.getMinecraft().getConnection().getPlayerInfo(player.getUniqueID());
        networkPlayerInfo.setDisplayName(new TextComponentString("Hello World"));
    }
}
