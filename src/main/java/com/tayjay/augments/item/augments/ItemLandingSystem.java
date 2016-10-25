package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.capabilities.IPlayerBodyProvider;
import com.tayjay.augments.api.events.ILivingHurt;
import com.tayjay.augments.api.item.IBodyPart;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.ChatHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.items.IItemHandler;

/**
 * Created by tayjay on 2016-06-29.
 */
public class ItemLandingSystem extends ItemAugment implements ILivingHurt
{

    public ItemLandingSystem(String name)
    {
        super(name,1,PartType.LEG,1,"Prevents player from taking fall damage");

    }

    @Override
    public boolean validate(ItemStack augment, EntityPlayer player)
    {
        IPlayerBodyProvider playerBody = CapHelper.getPlayerBodyCap(player);
        IPlayerDataProvider playerData = CapHelper.getPlayerDataCap(player);
        if(playerData.getCurrentEnergy()<getEnergyUse(augment))
            return false;
        if(!playerData.validate())
            return false;
        if(!CapHelper.getAugmentDataCap(augment).isActive())
            return false;
        ItemStack leftLeg=playerBody.getStackByPartSided(PartType.LEG,0);
        ItemStack rightLeg=playerBody.getStackByPartSided(PartType.LEG,1);
        if(leftLeg!=null &&((IBodyPart)leftLeg.getItem()).getTier(leftLeg)>=tier&&
                rightLeg!=null &&((IBodyPart)rightLeg.getItem()).getTier(rightLeg)>=tier)
        {
            return true;
        }
        return false;//Doesn't use super because it is checking for 2 sides of player, super ignores that case
    }

    @Override
    public void onHurt(ItemStack augment, EntityPlayer player, LivingHurtEvent event)
    {
        if(!event.getEntityLiving().worldObj.isRemote && event.getSource().damageType.equals("fall"))
        {
            if(validate(augment, player))
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
