package com.tayjay.augments.api.capabilities;

import com.sun.istack.internal.NotNull;
import com.tayjay.augments.api.item.PartType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

/**
 * Created by tayjay on 2016-06-24.
 */
public interface IPlayerPartsProvider extends INBTSerializable<NBTTagCompound>
{
    IItemHandler getPartsInv();

    ItemStack getStackByPart(PartType type);

    void sync(EntityPlayerMP player);

    void syncToOther(EntityPlayerMP player,EntityPlayerMP other);
}
