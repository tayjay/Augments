package com.tayjay.augments.api.capabilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * Created by tayjay on 2016-06-26.
 */
public interface IPlayerDataProvider extends INBTSerializable<NBTTagCompound>
{
    void sync(EntityPlayerMP player);

    EntityPlayer getPlayer();

}
