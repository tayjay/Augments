package com.tayjay.augments.handler;

import com.google.common.io.Files;
import com.tayjay.augments.inventory.InventoryAugmentPlayer;
import com.tayjay.augments.properties.PlayerAugmentProperties;
import com.tayjay.augments.util.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tayjm_000 on 2016-02-06.
 */
public class PlayerHandler
{
    private static HashMap<String, ArrayList<ItemStack>> playerAugments = new HashMap<String, ArrayList<ItemStack>>();
    private static HashMap<String, PlayerAugmentProperties> playerProperties = new HashMap<String, PlayerAugmentProperties>();

    public static void clearPlayerAugments(EntityPlayer player)
    {
        playerAugments.remove(player.getCommandSenderName());
    }

    public static ArrayList<ItemStack> getPlayerAugments(EntityPlayer player)
    {
        if(!playerAugments.containsKey(player.getCommandSenderName()))
        {
            ArrayList<ItemStack> augments = PlayerAugmentProperties.get(player).loadAugmentsFromInventory();
            playerAugments.put(player.getCommandSenderName(),augments);
        }
        return playerAugments.get(player.getCommandSenderName());
    }

    public static void setPlayerAugments(EntityPlayer player, ArrayList<ItemStack> augments)
    {
        playerAugments.put(player.getCommandSenderName(),augments);
        EventHandlerEntity.syncSchedule.add(player.getEntityId());
    }

    public static PlayerAugmentProperties getPlayerProperties(EntityPlayer player)
    {
        if(!playerProperties.containsKey(player.getCommandSenderName()))
        {
            PlayerAugmentProperties props = PlayerAugmentProperties.get(player);
            playerProperties.put(player.getCommandSenderName(),props);
            Minecraft.getMinecraft().thePlayer.getLocationSkin();
        }
        return playerProperties.get(player.getCommandSenderName());
    }

    public static InventoryAugmentPlayer getPlayerAugmentInventory(EntityPlayer player)
    {
        InventoryAugmentPlayer inv = PlayerAugmentProperties.get(player).inventory;
        if(inv!=null)
            return inv;
        return null;
    }

    /*
    public static void loadPlayerAugments(EntityPlayer player, File file1, File file2) {
        if (player != null && !player.worldObj.isRemote) {
            try {
                NBTTagCompound data = null;
                boolean save = false;
                if (file1 != null && file1.exists()) {
                    try {
                        FileInputStream fileinputstream = new FileInputStream(
                                file1);
                        data = CompressedStreamTools
                                .readCompressed(fileinputstream);
                        fileinputstream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (file1 == null || !file1.exists() || data == null
                        || data.hasNoTags()) {
                    LogHelper.warn("Data not found for "
                            + player.getCommandSenderName()
                            + ". Trying to load backup data.");
                    if (file2 != null && file2.exists()) {
                        try {
                            FileInputStream fileinputstream = new FileInputStream(
                                    file2);
                            data = CompressedStreamTools
                                    .readCompressed(fileinputstream);
                            fileinputstream.close();
                            save = true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (data != null) {
                    InventoryAugmentPlayer inventory = new InventoryAugmentPlayer(player);
                    inventory.readFromNBT(data);
                    playerAugments.put(player.getCommandSenderName(), inventory);
                    if (save)
                        savePlayerAugments(player, file1, file2);
                }
            } catch (Exception exception1) {
                LogHelper.fatal("Error loading baubles inventory");
                exception1.printStackTrace();
            }
        }
    }

    public static void savePlayerAugments(EntityPlayer player, File file1, File file2)
    {
        if (player != null && !player.worldObj.isRemote)
        {
            try
            {
                if (file1 != null && file1.exists())
                {
                    try
                    {
                        Files.copy(file1, file2);
                    } catch (Exception e)
                    {
                        LogHelper.error("Could not backup old augments file for player " + player.getCommandSenderName());
                    }
                }

                try
                {
                    if (file1 != null)
                    {
                        InventoryAugmentPlayer inventory = getPlayerAugments(player);
                        NBTTagCompound data = new NBTTagCompound();
                        inventory.writeToNBT(data);

                        FileOutputStream fileoutputstream = new FileOutputStream(
                                file1);
                        CompressedStreamTools.writeCompressed(data,
                                fileoutputstream);
                        fileoutputstream.close();

                    }
                } catch (Exception e)
                {
                    LogHelper.error("Could not save baubles file for player "
                            + player.getCommandSenderName());
                    e.printStackTrace();
                    if (file1.exists())
                    {
                        try
                        {
                            file1.delete();
                        } catch (Exception e2)
                        {
                        }
                    }
                }
            } catch (Exception exception1)
            {
                LogHelper.fatal("Error saving baubles inventory");
                exception1.printStackTrace();
            }
        }
    }
    */
}
