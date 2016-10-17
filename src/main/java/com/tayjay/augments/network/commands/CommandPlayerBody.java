package com.tayjay.augments.network.commands;

import com.tayjay.augments.Augments;
import com.tayjay.augments.api.AugmentsAPI;
import com.tayjay.augments.handler.GuiHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.items.IItemHandler;

/**
 * Created by tayjay on 2016-06-24.
 */
public class CommandPlayerBody extends CommandAugmentBase
{
    @Override
    public String getCommandName()
    {
        return "aug_viewPlayerBody";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "/aug playerBody";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        EntityPlayerMP player = (EntityPlayerMP) sender;
        player.openGui(Augments.instance, GuiHandler.GuiIDs.PLAYER_BODY.ordinal(),player.worldObj,(int)player.posX,(int)player.posY,(int)player.posZ);
    }
}
