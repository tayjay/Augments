package com.tayjay.augments.item;

import com.tayjay.augments.init.ModItems;
import com.tayjay.augments.lib.Names;
import com.tayjay.augments.util.ChatHelper;
import com.tayjay.augments.util.LogHelper;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Timer;
import net.minecraft.world.World;

import java.lang.reflect.Field;

/**
 * Created by tayjm_000 on 2016-01-17.
 */
public class ItemTestScanner extends ItemMod
{
    int cooldown = 10;
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
        if(cooldown==10)
        {
            Field timerField = ReflectionHelper.findField(Minecraft.class, "timer");
            try
            {
                Timer gameTimer = (Timer) timerField.get(Minecraft.getMinecraft());
                if (world.isRemote)
                {
                    if (gameTimer.timerSpeed != 1.0f)
                    {
                        gameTimer.timerSpeed = 1.0f;
                        ChatHelper.sendTo(player, "Normal Speed.");
                    } else
                    {
                        gameTimer.timerSpeed = .2f;
                        ChatHelper.sendTo(player, "Slow-mo!");
                    }
                    LogHelper.info("Client Speed: " + gameTimer.timerSpeed);
                } else
                {
                    LogHelper.info("Server Speed: " + gameTimer.timerSpeed);
                }

            } catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
        /*
        if(world.isRemote)
        {
            Entity e = ClientUtil.getEntityLookingAt(world,player);
            if(e!=null)
            {
                if(e instanceof EntityLiving)
                {
                    EntityLivingBase target = (EntityLivingBase) e;
                    player.openGui(AugmentsMod.registry, GuiHandler.GuiIDs.ENTITY_INFO.ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
                }
                else if(e instanceof EntityPlayer)
                {
                    EntityPlayer target = (EntityPlayer) e;
                    target.openGui(AugmentsMod.registry,GuiHandler.GuiIDs.INVENTORY_AUGMENT_PLAYER.ordinal(),world, (int) player.posX, (int) player.posY, (int) player.posZ);
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
        */
        return super.onItemRightClick(itemStack, world, player);
    }

    @Override
    public void onUpdate(ItemStack p_77663_1_, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_)
    {
        super.onUpdate(p_77663_1_, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
        if(cooldown<10) cooldown++;
        else cooldown = 10;
    }
}
