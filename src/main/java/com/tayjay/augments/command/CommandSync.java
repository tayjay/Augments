package com.tayjay.augments.command;

import com.tayjay.augments.handler.EventHandlerEntity;
import com.tayjay.augments.handler.EventHandlerNetwork;
import com.tayjay.augments.handler.PlayerHandler;
import com.tayjay.augments.inventory.InventoryAugmentPlayer;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.network.PacketSyncPlayerAugments;
import com.tayjay.augments.properties.PlayerAugmentProperties;
import com.tayjay.augments.util.ChatHelper;
import com.tayjay.augments.util.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.util.List;

/**
 * Created by tayjm_000 on 2016-02-06.
 */
public class CommandSync extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "sync";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_)
    {
        return "/sync";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] p_71515_2_)
    {
        String[] allUsers = MinecraftServer.getServer().getAllUsernames();
        List<EntityPlayerMP> players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
        for(EntityPlayerMP player:players)
        {
            EventHandlerEntity.syncSchedule.add(player.getEntityId());
            /*
            InventoryAugmentPlayer inv = PlayerAugmentProperties.get(player).inventory;
            for(int i = 0; i<inv.inventory.length;i++)
            {
                NetworkHandler.INSTANCE.sendToAll(new PacketSyncPlayerAugments(player, i));
                if(inv.inventory[i]!=null)
                {
                    LogHelper.info(inv.inventory[i].getDisplayName());
                    ChatHelper.sendTo((EntityPlayer) sender, inv.inventory[i].getDisplayName());
                }
            }
            */
            ChatHelper.sendTo((EntityPlayer) sender,"Synced Player Augments for "+player.getCommandSenderName());
        }
    }
}
