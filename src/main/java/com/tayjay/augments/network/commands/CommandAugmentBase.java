package com.tayjay.augments.network.commands;

import com.tayjay.augments.util.ChatHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;

/**
 * Created by tayjay on 2016-06-29.
 */
public abstract class CommandAugmentBase extends CommandBase
{
    @Nonnull
    @Override
    public abstract String getCommandName();

    @Override
    public abstract int getRequiredPermissionLevel();

    @Nonnull
    @Override
    public abstract String getCommandUsage(@Nonnull ICommandSender sender);

    @Override
    public abstract void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] params) throws CommandException;

    protected void sendSuccess(ICommandSender sender, ITextComponent message)
    {
        sendMessage(sender, ChatHelper.modifyColor(message, TextFormatting.GREEN));
    }

    protected void sendError(ICommandSender sender, ITextComponent message)
    {
        sendMessage(sender, ChatHelper.modifyColor(message, TextFormatting.RED));
    }

    protected void sendMessage(ICommandSender sender, ITextComponent message)
    {
        sender.addChatMessage(message);
    }
}
