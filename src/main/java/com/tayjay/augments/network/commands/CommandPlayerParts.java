package com.tayjay.augments.network.commands;

import com.tayjay.augments.api.AugmentsAPI;
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
public class CommandPlayerParts extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "aug_playerParts";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "/aug playerParts";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        EntityPlayerMP player = (EntityPlayerMP) sender;
        IItemHandler inv = player.getCapability(AugmentsAPI.PLAYER_PARTS_CAPABILITY,null).getPartsInv();
        player.addChatMessage(new TextComponentString("BodyParts for "+player.getDisplayNameString()));
        for(int i = 0; i < inv.getSlots();i++)
        {
            if(inv.getStackInSlot(i)!=null)
                player.addChatMessage(new TextComponentString(inv.getStackInSlot(i).toString()));
            else
                player.addChatMessage(new TextComponentString("null"));
        }
    }
}
