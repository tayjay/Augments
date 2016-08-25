package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.events.ILivingHurt;
import com.tayjay.augments.api.events.IPlayerTick;
import com.tayjay.augments.api.item.IBodyPart;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.ChatHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.items.IItemHandler;

/**
 * Created by tayjay on 2016-06-29.
 */
public class ItemLandingSystem extends ItemAugment implements IPlayerTick,ILivingHurt
{

    public ItemLandingSystem(String name)
    {
        super(name,1);
        acceptedParts.add(PartType.LEG_LEFT);
        acceptedParts.add(PartType.LEG_RIGHT);
    }

    @Override
    public boolean validate(ItemStack augment, ItemStack bodyPart, EntityPlayer player)
    {
        IItemHandler playerParts = CapHelper.getPlayerPartsCap(player).getPartsInv();
        IPlayerDataProvider playerData = CapHelper.getPlayerDataCap(player);
        if(playerData.getCurrentEnergy()<getEnergyUse(augment))
            return false;
        if(!CapHelper.getPlayerDataCap(player).validate())
            return false;
        if (bodyPart.getItem() instanceof IBodyPart)
        {
            IItemHandler augs;
            if (playerParts.getStackInSlot(PartType.LEG_RIGHT.ordinal()) != null && ((IBodyPart) bodyPart.getItem()).getPartType(bodyPart) == PartType.LEG_LEFT)
            {
                augs = CapHelper.getAugHolderCap(playerParts.getStackInSlot(PartType.LEG_RIGHT.ordinal())).getAugments();
                for (int i = 0; i < augs.getSlots(); i++)
                {
                    if (augs.getStackInSlot(i) != null && augs.getStackInSlot(i).getItem() == augment.getItem())
                    {
                        return true;
                    }
                }
            }

            else if (playerParts.getStackInSlot(PartType.LEG_LEFT.ordinal()) != null && ((IBodyPart) bodyPart.getItem()).getPartType(bodyPart) == PartType.LEG_RIGHT)
            {
                augs = CapHelper.getAugHolderCap(playerParts.getStackInSlot(PartType.LEG_LEFT.ordinal())).getAugments();
                for (int i = 0; i < augs.getSlots(); i++)
                {
                    if (augs.getStackInSlot(i) != null && augs.getStackInSlot(i).getItem() == augment.getItem())
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

    }

    @Override
    public void onHurt(ItemStack augment,ItemStack bodyPart, EntityPlayer player, LivingHurtEvent event)
    {
        if(!event.getEntityLiving().worldObj.isRemote && event.getSource().damageType.equals("fall"))
        {
            if(validate(augment,bodyPart,player) && ((IBodyPart)bodyPart.getItem()).getPartType(bodyPart)==PartType.LEG_LEFT)
            {
                CapHelper.getPlayerDataCap(player).removeEnergy(getEnergyUse(augment));
                player.fallDistance = 0;
                event.setAmount(0);
                ChatHelper.send(player, "Landing System engaged!");
                player.motionY = 0;
                event.setCanceled(true);
            }

        }
    }
}
