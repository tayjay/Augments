package com.tayjay.augments.item;

import com.tayjay.augments.entity.IAugmentable;
import com.tayjay.augments.init.ModItems;
import com.tayjay.augments.lib.Names;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.network.PacketHackEntity;
import com.tayjay.augments.util.ChatHelper;
import com.tayjay.augments.util.ClientUtil;
import com.tayjay.augments.util.NBTHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tayjm_000 on 2016-03-26.
 */
public class ItemCreativeHacker extends ItemA
{
    private final byte MAX_MODE = 1;
    public ItemCreativeHacker()
    {
        super();
        this.setUnlocalizedName(Names.Items.CREATIVE_HACKER);
        this.maxStackSize = 1;
        ModItems.register(this);
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean p_77624_4_)
    {
        super.addInformation(itemStack, player, list, p_77624_4_);
        if(!NBTHelper.hasTag(itemStack,"HackMode"))
            NBTHelper.setByte(itemStack,"HackMode",(byte)0);
        byte currentMode = NBTHelper.getByte(itemStack, "HackMode");
        list.add("Current Mode: "+currentMode);
        switch (currentMode)
        {
            case 0:
                list.add("Hijack");
                break;
            case 1:
                list.add("Upgrade");
                break;
            default:
                list.add("Unknown");
                break;
        }

    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if(NBTHelper.hasTag(itemStack,"HackMode"))
        {
            if (!player.isSneaking())
            {
                if(world.isRemote)
                {
                    byte currentMode = NBTHelper.getByte(itemStack, "HackMode");
                    switch (currentMode)
                    {
                        case 0:
                            ChatHelper.sendTo(player, "Attempting Hijack...");
                            Entity entity = ClientUtil.getEntityLookingAt(world, player);

                            if (entity instanceof EntityLivingBase)
                                NetworkHandler.INSTANCE.sendToServer(new PacketHackEntity((EntityLivingBase) entity, player));
                            break;
                        case 1:
                            ChatHelper.sendTo(player, "Attempting Upgrade...");
                    }
                }
            } else
            {
                if(!world.isRemote)
                {
                    byte currentMode = NBTHelper.getByte(itemStack, "HackMode");

                    if (currentMode++ > MAX_MODE)
                    {
                        currentMode = 0;
                    }
                    ChatHelper.sendTo(player, "Changing mode to " + currentMode);
                    NBTHelper.setByte(itemStack, "HackMode", currentMode);

                }

            }
        }
        else
        {
            NBTHelper.setByte(itemStack,"HackMode",(byte)0);
        }


        return super.onItemRightClick(itemStack, world, player);
    }
}
