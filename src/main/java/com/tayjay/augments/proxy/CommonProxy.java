package com.tayjay.augments.proxy;

import net.minecraft.entity.player.EntityPlayer;

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
