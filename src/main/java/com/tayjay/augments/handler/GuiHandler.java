package com.tayjay.augments.handler;

import com.tayjay.augments.client.gui.GuiEntityInfo;
import com.tayjay.augments.client.gui.GuiInventoryAugmentPlayer;
import com.tayjay.augments.inventory.ContainerAugmentPlayer;
import com.tayjay.augments.inventory.ContainerEmpty;
import com.tayjay.augments.inventory.InventoryAugmentPlayer;
import com.tayjay.augments.properties.PlayerAugmentProperties;
import com.tayjay.augments.util.LogHelper;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by tayjm_000 on 2016-01-17.
 */
public class GuiHandler implements IGuiHandler
{
    public enum GuiIDs
    {
        ENTITY_INFO,INVENTORY_AUGMENT_PLAYER
    }
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (GuiIDs.values()[ID])
        {
            case ENTITY_INFO:
                return new ContainerEmpty();
            case INVENTORY_AUGMENT_PLAYER:
                PlayerHandler.setPlayerAugments(player,PlayerAugmentProperties.get(player).inventory);
                return new ContainerAugmentPlayer(player,player.inventory, PlayerHandler.getPlayerAugments(player));
        }
        throw new IllegalArgumentException("No Container with id "+ID);
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (GuiIDs.values()[ID])
        {
            case ENTITY_INFO:
                return new GuiEntityInfo(player,null,"entityInfo");
            case INVENTORY_AUGMENT_PLAYER:
                try
                {
                    PlayerHandler.setPlayerAugments(player,PlayerAugmentProperties.get(player).inventory);
                    return new GuiInventoryAugmentPlayer(player, player.inventory, PlayerHandler.getPlayerAugments(player));
                }catch(NullPointerException e)
                {
                    LogHelper.error(PlayerAugmentProperties.get(player).toString());
                    return new GuiInventoryAugmentPlayer(null,null,null);
                }
        }
        throw new IllegalArgumentException("No GUI with id "+ID);
    }
}
