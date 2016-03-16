package com.tayjay.augments.util;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

/**
 * Created by tayjm_000 on 2016-01-31.
 */
public class ServerUtil
{
    /**
     * Code from Minecraft CommandBase
     * @param p_82359_0_
     * @param p_82359_1_
     * @return
     */
    public static EntityPlayerMP getPlayerMP(ICommandSender p_82359_0_, String p_82359_1_)
    {
        EntityPlayerMP entityplayermp = PlayerSelector.matchOnePlayer(p_82359_0_, p_82359_1_);

        if (entityplayermp != null)
        {
            return entityplayermp;
        }
        else
        {
            entityplayermp = MinecraftServer.getServer().getConfigurationManager().func_152612_a(p_82359_1_);

            if (entityplayermp == null)
            {
                throw new PlayerNotFoundException();
            }
            else
            {
                return entityplayermp;
            }
        }
    }
}
