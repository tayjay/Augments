package com.tayjay.augments.proxy;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by tayjm_000 on 2016-01-16.
 */
public interface IProxy
{
    void preInit();
    void init();
    void postInit();
    EntityPlayer getClientPlayer();
}
