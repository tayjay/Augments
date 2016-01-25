package com.tayjay.augments.augment.event;

import com.tayjay.augments.augment.interfaces.IAugment;
import com.tayjay.augments.util.LogHelper;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Created by tayjm_000 on 2016-01-21.
 */
public class AugmentChangeEvent extends Event
{

    public IAugment augment;
    /** This could be an EntityLivingBase or an EntityPlayer. Remember to cast when used */
    public EntityLivingBase holder;

    public AugmentChangeEvent(EntityLivingBase e, IAugment a)
    {
        this.augment = a;
        this.holder = e;
    }

    public IAugment getAugment()
    {
        return augment;
    }

    public EntityLivingBase getHolder()
    {
        return holder;
    }

    /**
     * This is just for convinence. Just casts holder to a player
     * @return  The holder as a player
     */
    public EntityPlayer getHolderAsPlayer()
    {
        if(holder instanceof EntityPlayer)
            return (EntityPlayer) holder;
        LogHelper.error("Attempted to cast EntityLivingBase to EntityPlayer in AugmentChangeEvent. Returning null.");
        return null;
    }

    /**
     * Allow this event to be cancelled. This may be nessecary if I want to stop an augment from being applyed to an entity. Just as a redundancy.
     * @return  Event can be cancelled.
     */
    @Override
    public boolean isCancelable()
    {
        return true;
    }

    public static class Add extends AugmentChangeEvent
    {
        public Add(EntityLivingBase e, IAugment a)
        {
            super(e, a);
        }
    }

    public static class Remove extends AugmentChangeEvent
    {
        public Remove(EntityLivingBase e, IAugment a)
        {
            super(e, a);
        }
    }
}
