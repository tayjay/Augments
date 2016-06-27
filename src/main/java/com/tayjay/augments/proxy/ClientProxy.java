package com.tayjay.augments.proxy;

import com.tayjay.augments.Augments;
import com.tayjay.augments.api.render.LayerAugments;
import com.tayjay.augments.api.capabilities.IPlayerPartsProvider;
import com.tayjay.augments.api.render.RenderPlayerAugments;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.ReflectHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;

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
    public void initRenderOverride()
    {
        //Override RenderPlayer
        /*
        RenderManager manager = Minecraft.getMinecraft().getRenderManager();
        manager.getSkinMap().put("default",new RenderPlayerWithEvents(manager));
        manager.getSkinMap().put("slim",new RenderPlayerWithEvents(manager,true));
        */

        ReflectHelper.changeRenderPlayer("default",new RenderPlayerAugments(Minecraft.getMinecraft().getRenderManager()));
        ReflectHelper.changeRenderPlayer("slim",new RenderPlayerAugments(Minecraft.getMinecraft().getRenderManager()));

        Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();
        RenderPlayer render;
        //Get Steve Render

        render = skinMap.get("default");
        render.addLayer(new LayerAugments(render));

        //Get Alex Render

        render = skinMap.get("slim");
        render.addLayer(new LayerAugments(render));


    }
}
