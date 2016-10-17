package com.tayjay.augments.handler;

import com.tayjay.augments.client.gui.GuiBodyPartAugments;
import com.tayjay.augments.client.gui.GuiPlayerBody;
import com.tayjay.augments.client.gui.GuiPlayerParts;
import com.tayjay.augments.inventory.*;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Created by tayjay on 2016-06-24.
 */
public class GuiHandler implements IGuiHandler
{
    public enum GuiIDs{
        PLAYER_BODY,
    }
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (GuiIDs.values()[ID])
        {
            case PLAYER_BODY:
                return new ContainerPlayerBody(player.inventory,
                                                new InventoryPlayerParts(CapHelper.getPlayerBodyCap(player),player),
                                                new InventoryPlayerAugments(CapHelper.getPlayerBodyCap(player),player));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (GuiIDs.values()[ID])
        {
            case PLAYER_BODY:
                return new GuiPlayerBody(player.inventory,
                                            new InventoryPlayerParts(CapHelper.getPlayerBodyCap(player),player),
                                            new InventoryPlayerAugments(CapHelper.getPlayerBodyCap(player),player));
        }
        return null;
    }
}
