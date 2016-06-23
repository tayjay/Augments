package com.tayjay.augments.proxy;

import com.tayjay.augments.Augments;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

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
}
