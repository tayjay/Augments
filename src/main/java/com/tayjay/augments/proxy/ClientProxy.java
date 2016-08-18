package com.tayjay.augments.proxy;

import com.tayjay.augments.Augments;
import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.render.LayerAugments;
import com.tayjay.augments.api.capabilities.IPlayerPartsProvider;
import com.tayjay.augments.api.render.RenderPlayerAugments;
import com.tayjay.augments.client.handler.KeyInputHandler;
import com.tayjay.augments.client.settings.Keybindings;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.ReflectHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.Map;

/**
 * Created by tayjay on 2016-06-23.
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public void registerItemRenderer(Item item, int meta, String id)
    {
        ModelLoader.setCustomModelResourceLocation(item,meta, new ModelResourceLocation(Augments.modId+":"+id,"inventory"));
    }

    @Override
    public IPlayerPartsProvider getClientPlayerParts()
    {
        return CapHelper.getPlayerPartsCap(FMLClientHandler.instance().getClientPlayerEntity());
    }

    @Override
    public IPlayerDataProvider getClientPlayerData()
    {
        return CapHelper.getPlayerDataCap(FMLClientHandler.instance().getClientPlayerEntity());
    }

    @Override
    public void initRenderOverride()
    {
        //Override RenderPlayer
        /*
        RenderManager manager = Minecraft.getMinecraft().getRenderManager();
        manager.getSkinMap().put("default",new RenderPlayerWithEvents(manager));
        manager.getSkinMap().put("slim",new RenderPlayerWithEvents(manager,true));
        */
        Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();
        if(Augments.renderType == 0)
        {
            //Don't handle any rendering of Augments
        }
        if(Augments.renderType >= 1)
        {
            //Only render over skin. No overriding

            RenderPlayer render;

            //Get Steve Render
            render = skinMap.get("default");
            render.addLayer(new LayerAugments(render));

            //Get Alex Render
            render = skinMap.get("slim");
            render.addLayer(new LayerAugments(render));
        }
        if(Augments.renderType >=2)
        {
            //Change the player render objects
            //As well as the layer
            //WARNING: CAREFUL! As when changing the player renderer it resets all values.
            ReflectHelper.changeRenderPlayer("default", new RenderPlayerAugments(skinMap.get("default"),Minecraft.getMinecraft().getRenderManager()));
            ReflectHelper.changeRenderPlayer("slim", new RenderPlayerAugments(skinMap.get("slim"),Minecraft.getMinecraft().getRenderManager(),true));
        }




    }

    public static KeyBinding[] keyBindings;

    public void init()
    {

    }

    public void preInit()
    {
        registerKeyBindings();
    }

    public void registerKeyBindings()
    {
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
        for(Keybindings key : Keybindings.values())
        {
            ClientRegistry.registerKeyBinding(key.getKeybind());
        }
    }
}
