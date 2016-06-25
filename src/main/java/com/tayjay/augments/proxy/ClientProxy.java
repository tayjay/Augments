package com.tayjay.augments.proxy;

import com.tayjay.augments.Augments;
import com.tayjay.augments.api.AugmentsAPI;
import com.tayjay.augments.api.capabilities.IPlayerPartsProvider;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;

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
        return FMLClientHandler.instance().getClientPlayerEntity().getCapability(AugmentsAPI.PLAYER_PARTS_CAPABILITY,null);
    }
}
