package com.tayjay.augments.proxy;

import com.tayjay.augments.api.AugmentsAPI;
import com.tayjay.augments.api.capabilities.IPlayerPartsProvider;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
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

    public IPlayerPartsProvider getClientPlayerParts(){return null;}

    public void initRenderOverride() {}

}
