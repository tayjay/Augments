package com.tayjay.augments.proxy;

import com.tayjay.augments.Augments;
import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.render.ItemRendererOverride;
import com.tayjay.augments.api.render.LayerAugments;
import com.tayjay.augments.api.capabilities.IPlayerBodyProvider;
import com.tayjay.augments.api.render.RenderItemOverride;
import com.tayjay.augments.api.render.RenderPlayerAugments;
import com.tayjay.augments.client.handler.KeyInputHandler;
import com.tayjay.augments.client.settings.Keybindings;
import com.tayjay.augments.event.ClientEvents;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.ReflectHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.Map;

/**
 * Created by tayjay on 2016-06-23.
 */
public class ClientProxy extends CommonProxy
{
    private ClientEvents clientEvents;
    @Override
    public void registerItemRenderer(Item item, int meta, String id)
    {
        ModelLoader.setCustomModelResourceLocation(item,meta, new ModelResourceLocation(Augments.modId+":"+id,"custom"));//inventory
    }

    @Override
    public IPlayerBodyProvider getClientPlayerParts()
    {
        return CapHelper.getPlayerBodyCap(FMLClientHandler.instance().getClientPlayerEntity());
    }

    @Override
    public IPlayerDataProvider getClientPlayerData()
    {
        return CapHelper.getPlayerDataCap(FMLClientHandler.instance().getClientPlayerEntity());
    }

    @Override
    public void initRenderOverride()
    {
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
        OBJLoader.INSTANCE.addDomain(Augments.modId);

        clientEvents = new ClientEvents();
        MinecraftForge.EVENT_BUS.register(clientEvents);
    }

    public void registerKeyBindings()
    {
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
        for(Keybindings key : Keybindings.values())
        {
            ClientRegistry.registerKeyBinding(key.getKeybind());
        }
    }

    @Override
    public ClientEvents getClientEventHandler()
    {
        return clientEvents;
    }
}
