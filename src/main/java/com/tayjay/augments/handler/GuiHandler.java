package com.tayjay.augments.handler;

import com.tayjay.augments.api.AugmentsAPI;
import com.tayjay.augments.client.gui.GuiPlayerParts;
import com.tayjay.augments.inventory.ContainerPlayerParts;
import com.tayjay.augments.inventory.InventoryPlayerParts;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Created by tayjay on 2016-06-24.
 */
public class GuiHandler implements IGuiHandler
{
    public enum GuiIDs{
        PLAYER_PARTS,
    }
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (GuiIDs.values()[ID])
        {
            case PLAYER_PARTS:
                return new ContainerPlayerParts(player.inventory,new InventoryPlayerParts(player.getCapability(AugmentsAPI.PLAYER_PARTS_CAPABILITY,null),player));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (GuiIDs.values()[ID])
        {
            case PLAYER_PARTS:
                return new GuiPlayerParts(player.inventory,new InventoryPlayerParts(player.getCapability(AugmentsAPI.PLAYER_PARTS_CAPABILITY,null),player));
        }
        return null;
    }
}
