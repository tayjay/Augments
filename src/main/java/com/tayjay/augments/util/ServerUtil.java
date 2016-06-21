package com.tayjay.augments.util;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public static String toMD5(String input)
    {
        try
        {
            String output = "";
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(input.getBytes());
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            output = bigInt.toString(16);
            while (output.length() < 32) {
                output = "0" + output;
            }
            return output;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return "";
    }
}
