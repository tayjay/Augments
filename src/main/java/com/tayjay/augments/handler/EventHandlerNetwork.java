package com.tayjay.augments.handler;

import com.tayjay.augments.network.MessageSyncPlayerAugments;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.network.PacketSyncPlayerAugments;
import com.tayjay.augments.properties.PlayerAugmentProperties;
import com.tayjay.augments.util.ChatHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import java.util.List;


/**
 * Lifted from Baubles
 * Created by tayjm_000 on 2016-02-06.
 */
public class EventHandlerNetwork
{
    @SubscribeEvent
    public void playerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        if(side == Side.SERVER)
        {
            EventHandlerEntity.syncSchedule.add(event.player.getEntityId());
        }
    }

    @SubscribeEvent
    public void playerJoinWorld(EntityJoinWorldEvent event)
    {
        if(event.entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.entity;
            if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
            {
                EventHandlerEntity.syncSchedule.add(player.getEntityId());
                syncAllAugmentsToClient(player);
            }
        }
    }

    public static void syncAllAugmentsToClient(EntityPlayer receiver)
    {
        List<EntityPlayerMP> players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
        for(EntityPlayerMP player:players)
        {
            PlayerHandler.setPlayerAugments(player,PlayerAugmentProperties.get(player).loadAugmentsFromInventory());
            EventHandlerEntity.syncSchedule.add(player.getEntityId());
            ChatHelper.sendTo((EntityPlayer) receiver,"Synced Player Augments for "+player.getCommandSenderName());
        }
    }

    public static void syncAugments(EntityPlayer player)
    {
        try
        {
            if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
            {
                //LogHelper.info("Sending Augments from"+player.get()+" to all.");
                PlayerAugmentProperties.get(player).syncAll();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
