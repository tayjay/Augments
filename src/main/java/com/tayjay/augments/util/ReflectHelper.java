package com.tayjay.augments.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
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
}
