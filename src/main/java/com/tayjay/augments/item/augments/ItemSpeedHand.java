package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.item.IBodyPart;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
    private static final AttributeModifier ATTACK_SPEED_BOOST_MODIFIER = (new AttributeModifier(ATTACK_SPEED_BOOST_MODIFIER_UUID, "Attacking speed boost", 0.2D, 0)).setSaved(false);

    public ItemSpeedHand(String name)
    {
        super(name,1,PartType.ARM,0.1f,"Make youre arms work faster");
    }


    @Override
    public void tickAugment(ItemStack augmentStack, TickEvent.PlayerTickEvent event)
    {
        /*if(((IBodyPart) bodyPartStack.getItem()).getPartType(bodyPartStack)== PartType.ARM_RIGHT)
        {
            if(event.player.getActiveHand() == EnumHand.MAIN_HAND && event.player.getPrimaryHand() == EnumHandSide.RIGHT)
            {
                event.player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(ATTACK_SPEED_BOOST_MODIFIER);
            }
        }
        else if(((IBodyPart) bodyPartStack.getItem()).getPartType(bodyPartStack)== PartType.ARM_LEFT)
        {
            if(event.player.getActiveHand() == EnumHand.MAIN_HAND && event.player.getPrimaryHand() == EnumHandSide.LEFT)
            {
                event.player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(ATTACK_SPEED_BOOST_MODIFIER);
            }
        }*/
    }

    @Override
    public boolean validate(ItemStack augment, EntityPlayer player)
    {
        IPlayerDataProvider playerData = CapHelper.getPlayerDataCap(player);

        if (!playerData.validate())
        {
            return false;
        }
        return true;

    }
}
