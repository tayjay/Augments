package com.tayjay.augments.proxy;

import com.tayjay.augments.augment.handlers.ServerAugmentHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by tayjm_000 on 2016-01-16.
 */
public class CommonProxy implements IProxy
{
    @Override
    public void preInit()
    {

    }

    @Override
    public void init()
    {

    }

    @Override
    public void postInit()
    {

    }

    @Override
    public EntityPlayer getClientPlayer()
    {
        //NOOP
        return null;
    }
}
