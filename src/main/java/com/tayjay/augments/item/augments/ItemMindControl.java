package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;

/**
 * Created by tayjay on 2016-10-21.
 * Enter the mind of another mob and control it, or change it's AI, or both!
 */
public class ItemMindControl extends ItemAugment
{
    public ItemMindControl(String name)
    {
        super(name, 1, PartType.HEAD, 1, "Enter the mind of another mob and control it, or change it's AI, or both!");
    }

    @Override
    public void onActivate(ItemStack augment, EntityPlayer player)
    {
        //todo: do energy check
        if(player instanceof EntityPlayerSP)
        {
            RayTraceResult rt = Minecraft.getMinecraft().objectMouseOver;
            if (rt != null && rt.entityHit != null)
            {
                Entity entity = rt.entityHit;
                if (entity instanceof EntityLivingBase)
                {
                    Minecraft.getMinecraft().setRenderViewEntity(entity);
                    CapHelper.getPlayerDataCap(player).removeEnergy(getEnergyUse(augment));
                }
            } else
            {
                CapHelper.getAugmentDataCap(augment).setActive(false);
            }
        }
    }

    @Override
    public void onDeactivate(ItemStack augment, EntityPlayer player)
    {
        if(Minecraft.getMinecraft().getRenderViewEntity()!=Minecraft.getMinecraft().thePlayer)
        {
            Minecraft.getMinecraft().setRenderViewEntity(Minecraft.getMinecraft().thePlayer);
        }
    }
}
