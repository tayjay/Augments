package com.tayjay.augments.item;

import com.tayjay.augments.AugmentsCore;
import com.tayjay.augments.handler.GuiHandler;
import com.tayjay.augments.init.ModItems;
import com.tayjay.augments.lib.Names;
import com.tayjay.augments.network.MessageOpenGuiServer;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.properties.PlayerAugmentProperties;
import com.tayjay.augments.util.ChatHelper;
import com.tayjay.augments.util.ClientUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by tayjm_000 on 2016-01-17.
 */
public class ItemTestScanner extends ItemA
{
    public ItemTestScanner()
    {
        super();
        this.setUnlocalizedName(Names.Items.TEST_SCANNER);
        this.maxStackSize = 1;
        ModItems.register(this);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if(world.isRemote)
        {
            Entity e = ClientUtil.getEntityLookingAt(world,player);
            if(e!=null)
            {
                if(e instanceof EntityLiving)
                {
                    EntityLivingBase target = (EntityLivingBase) e;
                    player.openGui(AugmentsCore.instance, GuiHandler.GuiIDs.ENTITY_INFO.ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
                }
                else if(e instanceof EntityPlayer)
                {
                    EntityPlayer target = (EntityPlayer) e;
                    target.openGui(AugmentsCore.instance,GuiHandler.GuiIDs.INVENTORY_AUGMENT_PLAYER.ordinal(),world, (int) player.posX, (int) player.posY, (int) player.posZ);
                }
            }
            else if(e==null)
            {
                if(PlayerAugmentProperties.get(player)!=null)
                {
                    ChatHelper.sendTo(player,"Opening your Extended Properties");
                    NetworkHandler.INSTANCE.sendToServer(new MessageOpenGuiServer(GuiHandler.GuiIDs.INVENTORY_AUGMENT_PLAYER.ordinal()));
                }
                else
                {
                    ChatHelper.sendTo(player,"You do not have extended properties...");
                }
            }
        }
        return super.onItemRightClick(itemStack, world, player);
    }

}
