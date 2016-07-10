package com.tayjay.augments.item.bodyParts;

import com.tayjay.augments.api.item.IEnergySupply;
import com.tayjay.augments.api.item.PartType;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by tayjay on 2016-06-26.
 */
public class ItemEnergyCell extends ItemBodyPart implements IEnergySupply
{
    int maxEnergy;//TODO: Change to float/double for recharging energy.

    public ItemEnergyCell(String name, int tier,int energyMax)
    {
        super(name, tier,0,0,"","", PartType.POWER);
        this.maxEnergy = energyMax;
    }

    @Override
    public void renderOnPlayer(ItemStack stack, EntityPlayer playerIn, RenderPlayer renderPlayer)
    {

    }

    @Override
    public int maxEnergy(ItemStack stack)
    {
        return this.maxEnergy+3;
    }

    @Override
    public int currentEnergy(ItemStack stack)
    {
        return this.maxEnergy;
    }
}
