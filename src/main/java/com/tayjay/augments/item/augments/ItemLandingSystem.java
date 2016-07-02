package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.events.IPlayerTick;
import com.tayjay.augments.api.item.IBodyPart;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.ChatHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

/**
 * Created by tayjay on 2016-06-29.
 */
public class ItemLandingSystem extends ItemAugment implements IPlayerTick
{

    public ItemLandingSystem(String name)
    {
        super(name);
        acceptedParts.add(PartType.LEG_LEFT);
        acceptedParts.add(PartType.LEG_RIGHT);
    }

    @Override
    public boolean validate(ItemStack augment, ItemStack bodyPart, EntityPlayer player)
    {
        IItemHandler playerParts = CapHelper.getPlayerPartsCap(player).getPartsInv();
        IPlayerDataProvider playerData = CapHelper.getPlayerDataCap(player);
            if (bodyPart.getItem() instanceof IBodyPart)
            {
                IItemHandler augs;
                if (playerParts.getStackInSlot(6) != null && ((IBodyPart) bodyPart.getItem()).getPartType(bodyPart) == PartType.LEG_LEFT)
                {
                    augs = CapHelper.getAugHolderCap(playerParts.getStackInSlot(6)).getAugments();//CHECK RIGHT_LEG TODO: Change magic number
                    for (int i = 0; i < augs.getSlots(); i++)
                    {
                        if (augs.getStackInSlot(i) != null && augs.getStackInSlot(i).getItem() == augment.getItem())
                        {
                            return true;
                        }
                    }
                } else if (playerParts.getStackInSlot(5) != null && ((IBodyPart) bodyPart.getItem()).getPartType(bodyPart) == PartType.LEG_RIGHT)
                {
                    augs = CapHelper.getAugHolderCap(playerParts.getStackInSlot(5)).getAugments();//CHECK RIGHT_LEG TODO: Change magic number
                    for (int i = 0; i < augs.getSlots(); i++)
                    {
                        if (augs.getStackInSlot(i).getItem() == augment.getItem())
                        {
                            return true;
                        }
                    }
                }
            }
        return false;
    }


    @Override
    public void onPlayerTick(ItemStack augmentStack, ItemStack bodyPartStack, TickEvent.PlayerTickEvent event)
    {
        if(!event.player.worldObj.isRemote && event.player.fallDistance >= 3f && event.player.worldObj.getBlockState(new BlockPos(event.player.posX,event.player.posY-1,event.player.posZ)).getBlock() != Blocks.AIR && validate(augmentStack,bodyPartStack,event.player))
        {
            if(CapHelper.getPlayerDataCap(event.player).removeEnergy(getEnergyUse(augmentStack)))
            {
                event.player.fallDistance = 0;
                ChatHelper.send(event.player, "Landing System engaged!");
                event.player.motionY = 0;
            }

        }
    }

    @Override
    public float getEnergyUse(ItemStack stack)
    {
        return 1;
    }
}
