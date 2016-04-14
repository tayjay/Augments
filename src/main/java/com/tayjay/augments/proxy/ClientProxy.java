package com.tayjay.augments.proxy;

import com.tayjay.augments.augment.handlers.ClientAugmentHandler;
import com.tayjay.augments.augment.handlers.ServerAugmentHandler;
import com.tayjay.augments.client.KeyInputHandler;
import com.tayjay.augments.client.Keybindings;
import com.tayjay.augments.init.EntityRenders;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by tayjm_000 on 2016-01-16.
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit()
    {
        registerKeybinds();

    }

    private void registerKeybinds()
    {
        for(Keybindings key : Keybindings.values())
        {
            FMLCommonHandler.instance().bus().register(new KeyInputHandler());
            if(key!=null&& key.getKeybind()!=null)
            {
                ClientRegistry.registerKeyBinding(key.getKeybind());
            }
        }
    }

    @Override
    public void init()
    {
        super.init();

        MinecraftForge.EVENT_BUS.register(new ClientAugmentHandler());
        FMLCommonHandler.instance().bus().register(new ClientAugmentHandler());
    }

    @Override
    public void registerRenderers()
    {
        EntityRenders.init();

    }

    @Override
    public void postInit()
    {
        super.postInit();
    }

    @Override
    public EntityPlayer getClientPlayer()
    {
        return Minecraft.getMinecraft().thePlayer;
    }

    @Override
    public World getClientWorld() {
        return FMLClientHandler.instance().getClient().theWorld;
    }
}
