package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.events.IActivate;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.network.packets.PacketChangeEnergy;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.ChatHelper;
import com.tayjay.augments.util.EntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

/**
 * Created by tayjay on 2016-07-03.
 */
public class ItemOvershield extends ItemAugment implements IActivate
{
    public ItemOvershield(String name)
    {
        super(name);
        acceptedParts.add(PartType.TORSO);
    }

    @Override
    public boolean validate(ItemStack augment, ItemStack bodyPart, EntityPlayer player)
    {
        return CapHelper.getPlayerDataCap(player).getCurrentEnergy()>=getEnergyUse(augment);
    }

    @Override
    public float getEnergyUse(ItemStack stack)
    {
        return 2;
    }


    @Override
    public KeyBinding getKey()
    {
        return null;
    }

    @Override
    public void activate(ItemStack stack, EntityPlayer playerIn)
    {
        if(validate(stack,null,playerIn))
        {
            CapHelper.getPlayerDataCap(playerIn).removeEnergy(getEnergyUse(stack));

            EntityUtil.setFlag(playerIn,21,true);
        }
        else
        {
            ChatHelper.send(playerIn, "Not enough energy!");
        }
    }
}
