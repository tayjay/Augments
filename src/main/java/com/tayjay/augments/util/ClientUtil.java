package com.tayjay.augments.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * Created by tayjm_000 on 2016-01-17.
 */
public class ClientUtil
{
    public static Entity getEntityLookingAt(World world, EntityPlayer player)
    {
        MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
        if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY)
        {
            return mop.entityHit;
        }
        return null;
    }
}
