package com.tayjay.augments.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

/**
 * Created by tayjm_000 on 2016-01-17.
 */
public class ContainerEmpty extends Container
{
    @Override
    public boolean canInteractWith(EntityPlayer p_75145_1_)
    {
        return false;
    }
}
