package com.tayjay.augments.inventory;

import com.tayjay.augments.tileentity.TileEntityAugmentBuilder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

/**
 * Created by tayjm_000 on 2016-04-05.
 */
public class ContainerAugmentBuilder extends ContainerBase
{
    private TileEntityAugmentBuilder te;

    public ContainerAugmentBuilder(InventoryPlayer playerInventory, TileEntityAugmentBuilder te)
    {
        this.addSlotToContainer(new SlotAugmentBuilder(te,0,80,58));

        this.addPlayerSlots(playerInventory,8,84);
    }
    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return te.isUseableByPlayer(player);
    }
}
