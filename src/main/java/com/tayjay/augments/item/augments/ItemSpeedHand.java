package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.capabilities.IPlayerBodyProvider;
import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.item.IBodyPart;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.UUID;

/**
 * Created by tayjay on 2016-07-22.
 */
public class ItemSpeedHand extends ItemAugment
{
    private static final UUID ATTACK_SPEED_BOOST_MODIFIER_UUID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
    private static final AttributeModifier ATTACK_SPEED_BOOST_MODIFIER = (new AttributeModifier(ATTACK_SPEED_BOOST_MODIFIER_UUID, "Attacking speed boost", 10D, 0)).setSaved(false);

    public ItemSpeedHand(String name)
    {
        super(name,1,PartType.ARM,0.04f,"Make your arms work faster");
    }


    @Override
    public void tickAugment(ItemStack augmentStack, TickEvent.PlayerTickEvent event)
    {
        if(validate(augmentStack,event.player))
        {
            if(!event.player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).hasModifier(ATTACK_SPEED_BOOST_MODIFIER))
            {
                event.player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(ATTACK_SPEED_BOOST_MODIFIER);
            }
            //System.out.println(event.player.getCooldownTracker().getCooldown(this, MinecraftServer.getCurrentTimeMillis()%20));
            if(event.player.swingProgressInt==1) //todo: Need better calculation
            {
                CapHelper.getPlayerDataCap(event.player).removeEnergy(getEnergyUse(augmentStack) / 2);
                //event.player.addPotionEffect(new PotionEffect(MobEffects.HASTE,10,5));
            }
        }
        else
        {
            if(event.player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).hasModifier(ATTACK_SPEED_BOOST_MODIFIER))
                event.player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).removeModifier(ATTACK_SPEED_BOOST_MODIFIER);
        }
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
        ItemStack leftArm=playerBody.getStackByPartSided(PartType.ARM,0);
        ItemStack rightArm=playerBody.getStackByPartSided(PartType.ARM,1);
        if(leftArm!=null &&((IBodyPart)leftArm.getItem()).getTier(leftArm)>=tier&&
                rightArm!=null &&((IBodyPart)rightArm.getItem()).getTier(rightArm)>=tier)
        {
            return true;
        }
        return false;//Doesn't use super because it is checking for 2 sides of player, super ignores that case

    }
}
