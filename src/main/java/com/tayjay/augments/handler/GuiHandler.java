package com.tayjay.augments.handler;

import com.tayjay.augments.api.AugmentsAPI;
import com.tayjay.augments.client.gui.GuiBodyPartAugments;
import com.tayjay.augments.client.gui.GuiPlayerParts;
import com.tayjay.augments.inventory.ContainerAugments;
import com.tayjay.augments.inventory.ContainerPlayerParts;
import com.tayjay.augments.inventory.InventoryAugments;
import com.tayjay.augments.inventory.InventoryPlayerParts;
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
        PLAYER_PARTS,BODY_PART_AUGS,
    }
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (GuiIDs.values()[ID])
        {
            case PLAYER_PARTS:
                return new ContainerPlayerParts(player.inventory,new InventoryPlayerParts(CapHelper.getPlayerPartsCap(player),player));
            case BODY_PART_AUGS:
                ItemStack stack = player.getHeldItemMainhand();
                return new ContainerAugments(player.inventory,new InventoryAugments(CapHelper.getAugHolderCap(stack),stack));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (GuiIDs.values()[ID])
        {
            case PLAYER_PARTS:
                return new GuiPlayerParts(player.inventory,new InventoryPlayerParts(CapHelper.getPlayerPartsCap(player),player));
            case BODY_PART_AUGS:
                ItemStack stack = player.getHeldItemMainhand();
                return new GuiBodyPartAugments(player.inventory,new InventoryAugments(CapHelper.getAugHolderCap(stack),stack));
        }
        return null;
    }
}
