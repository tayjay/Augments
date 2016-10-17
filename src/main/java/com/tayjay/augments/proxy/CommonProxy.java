package com.tayjay.augments.proxy;

import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.capabilities.IPlayerBodyProvider;
import net.minecraft.item.Item;

/**
 * Created by tayjay on 2016-06-23.
 */
public class CommonProxy
{
    public void registerItemRenderer(Item item, int meta, String id)
    {
        //NOOP
    }

    public IPlayerBodyProvider getClientPlayerParts(){return null;}

    public IPlayerDataProvider getClientPlayerData(){return null;}

    public void initRenderOverride() {}

    public void init(){}

    public void preInit(){}

    public void registerKeyBindings(){}

}
