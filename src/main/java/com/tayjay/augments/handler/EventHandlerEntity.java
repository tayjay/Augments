package com.tayjay.augments.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.HashSet;

/**
 * Created by tayjm_000 on 2016-02-06.
 */
public class EventHandlerEntity
{
    public static HashSet<Integer> syncSchedule = new HashSet<Integer>();


    @SubscribeEvent
    public void  onPlayerTick(PlayerEvent.LivingUpdateEvent event)
    {
        if(event.entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.entity;

            if(!syncSchedule.isEmpty() && syncSchedule.contains(player.getEntityId()))
            {
                EventHandlerNetwork.syncAugments(player);
                syncSchedule.remove(player.getEntityId());
            }

            /*
            InventoryAugmentPlayer augments = PlayerHandler.getPlayerAugments(player);
            for (int a = 0; a < augments.getSizeInventory(); a++) {
                if (augments.getStackInSlot(a) != null
                        && augments.getStackInSlot(a).getItem() instanceof IPlayerTick) {
                    ((IPlayerTick) augments.getStackInSlot(a).getItem()).onTick(augments.getStackInSlot(a), player);
                }
            }
            */
        }
    }

    public void onStartTracking(PlayerEvent.StartTracking event)
    {

        if(!event.target.worldObj.isRemote && event.target instanceof EntityPlayer)
        {
            EventHandlerNetwork.syncAllAugmentsToClient((EntityPlayer) event.target);
        }
    }

    /*
    @SubscribeEvent
    public void onPlayerLoad(PlayerEvent.LoadFromFile event)
    {
        PlayerHandler.clearPlayerAugments(event.entityPlayer);

        File file1 = getPlayerFile("aug",event.playerDirectory, event.entityPlayer.getCommandSenderName());
        if(!file1.exists())
        {
            File filep = event.getPlayerFile("aug");
            if(filep.exists())
            {
                try
                {
                    Files.copy(filep, file1);
                    LogHelper.info("Loading Augment File For " + event.entity.getCommandSenderName());
                    filep.delete();
                    File fb = event.getPlayerFile("augback");
                    if (fb.exists()) fb.delete();
                } catch (IOException e)
                {
                }
            }
        }
    }

    public File getPlayerFile(String suffix, File playerDirectory, String playername)
    {
        if ("dat".equals(suffix)) throw new IllegalArgumentException("The suffix 'dat' is reserved");
        return new File(playerDirectory, "_"+playername+"."+suffix);
    }

    @SubscribeEvent
    public void onPlayerSave(PlayerEvent.SaveToFile event)
    {
        PlayerHandler.savePlayerAugments(event.entityPlayer,getPlayerFile("aug",event.playerDirectory,event.entityPlayer.getCommandSenderName()),getPlayerFile("augback",event.playerDirectory,event.entityPlayer.getCommandSenderName()));
    }
    */
}
