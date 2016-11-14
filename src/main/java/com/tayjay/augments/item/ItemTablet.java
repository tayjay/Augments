package com.tayjay.augments.item;

import com.tayjay.augments.util.ChatHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.Map;

/**
 * Created by tayjay on 2016-07-08.
 */
public class ItemTablet extends ItemBase
{
    public ItemTablet(String name)
    {
        super(name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if(worldIn.isRemote)
            ChatHelper.send(playerIn,"Hello "+playerIn.getDisplayNameString()+". Welcome to the world of Augments!");
        if(worldIn.isRemote)
        {
            Map<ResourceLocation, ITextureObject> map = ReflectionHelper.getPrivateValue(TextureManager.class, Minecraft.getMinecraft().getTextureManager(),"mapTextureObjects");
            if(map!=null)
            {
                ITextureObject obj = map.get(new ResourceLocation("textures/entity/zombie/zombie.png"));
                if (obj != null)
                {
                    ChatHelper.clientMsg(obj.toString());
                    obj.setBlurMipmap(true,true);
                }
            }
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }
}
