package com.tayjay.augments.network.commands;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by tayjay on 2016-06-29.
 */
public class CommandAugments extends CommandAugmentBase
{
    private static final List<String> commands = Lists.newArrayList("viewPlayerParts","setMaxEnergy","setCurrentEnergy","setRechargeEnergy");

    final CommandPlayerParts playerPartsCMD = new CommandPlayerParts();
    final CommandEnergy energyMaxCMD = new CommandEnergy(CommandEnergy.CommandType.MAX);
    final CommandEnergy energyCurrentCMD = new CommandEnergy(CommandEnergy.CommandType.CURRENT);
    final CommandEnergy energyRechargeCMD = new CommandEnergy(CommandEnergy.CommandType.RECHARGE);

    @Nonnull
    @Override
    public String getCommandName()
    {
        return "aug";
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
        return "aug.command.main.usage";
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos)
    {
        if(args.length == 1)
        {
            return commands;
        }
        return null;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] params) throws CommandException
    {
        if (params.length < 1)
        {
            sendError(sender, new TextComponentTranslation("aug.command.main.usage"));
            return;
        }
        String[] relayparams = new String[0];

        if (params.length > 1)
        {
            relayparams = Arrays.copyOfRange(params, 1, params.length);
        }

        String subName = params[0].toLowerCase(Locale.ROOT);

        if("viewPlayerParts".toLowerCase().equals(subName))
        {
            if(playerPartsCMD.checkPermission(server,sender))
            {
                playerPartsCMD.execute(server,sender,relayparams);
            }
        }
        else if("setMaxEnergy".toLowerCase().equals(subName))
        {
            if(energyMaxCMD.checkPermission(server,sender))
            {
                energyMaxCMD.execute(server,sender,relayparams);
            }
        }
        else if("setCurrentEnergy".toLowerCase().equals(subName))
        {
            if(energyCurrentCMD.checkPermission(server,sender))
            {
                energyCurrentCMD.execute(server,sender,relayparams);
            }
        }
        else if("setRechargeEnergy".toLowerCase().equals(subName))
        {
            if(energyRechargeCMD.checkPermission(server,sender))
            {
                energyRechargeCMD.execute(server,sender,relayparams);
            }
        }
    }
}
