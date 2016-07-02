package com.tayjay.augments.network.commands;

import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.ChatHelper;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nonnull;

/**
 * Created by tayjay on 2016-06-29.
 */
public class CommandEnergy extends CommandAugmentBase
{
    CommandType commandType;
    CommandEnergy(){this(CommandType.MAX);}

    CommandEnergy(CommandType commandType)
    {
        this.commandType = commandType;
    }

    @Nonnull
    @Override
    public String getCommandName()
    {
        return "aug_setMaxEnergy";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Nonnull
    @Override
    public String getCommandUsage(@Nonnull ICommandSender sender)
    {
        return null;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] params) throws CommandException
    {
        EntityPlayerMP player = (EntityPlayerMP) sender;
        try
        {
        float value = Float.parseFloat(params[0]);
        switch (commandType)
        {
            case MAX:
                CapHelper.getPlayerDataCap(player).setMaxEnergy(value);
                CapHelper.getPlayerDataCap(player).sync(player);
                ChatHelper.send(player,"Your max energy is now "+CapHelper.getPlayerDataCap(player).getMaxEnergy());
                return;
            case CURRENT:
                CapHelper.getPlayerDataCap(player).setCurrentEnergy(value);
                CapHelper.getPlayerDataCap(player).sync(player);
                ChatHelper.send(player,"Your current energy is now "+CapHelper.getPlayerDataCap(player).getCurrentEnergy());
                return;
            case RECHARGE:
                CapHelper.getPlayerDataCap(player).setRechargeRate(value);
                CapHelper.getPlayerDataCap(player).sync(player);
                ChatHelper.send(player,"Your rechargeRate is now "+CapHelper.getPlayerDataCap(player).getRechargeRate());
                return;
        }

        }catch(Exception e)
        {
            sendError(sender,new TextComponentString("Parameter is not a number!"));
        }

    }

    enum CommandType
    {
        MAX,CURRENT,RECHARGE
    }

}
