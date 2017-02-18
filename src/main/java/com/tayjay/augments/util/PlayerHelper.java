package com.tayjay.augments.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

/**
 * Created by tayjay on 2016-12-26.
 */
public class PlayerHelper
{
    //From Extra Utilities checks
    public static boolean isPlayerReal(final EntityPlayer player) {
        return !isPlayerFake(player);
    }

    public static boolean isPlayerReal(final EntityPlayerMP player) {
        return !isPlayerFake(player);
    }

    public static boolean isPlayerFake(final EntityPlayer player) {
        return player.worldObj == null || (!player.worldObj.isRemote && player.getClass() != EntityPlayerMP.class && !FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerList().contains(player));
    }

    public static boolean isPlayerFake(final EntityPlayerMP player) {
        if (player.getClass() != EntityPlayerMP.class) {
            return true;
        }
        if (player.connection == null) {
            return true;
        }
        try {
            player.getPlayerIP().length();
            player.connection.netManager.getRemoteAddress().toString();
        }
        catch (Exception e) {
            return true;
        }
        return !FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerList().contains(player);
    }
    //End Extra Utilities
}
