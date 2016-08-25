package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.item.IAugment;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.init.IItemModelProvider;
import com.tayjay.augments.item.ItemBase;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tayjay on 2016-06-23.<br/>
 * Items extending this will be augments to be used in the mod.
 */
public class ItemAugment extends ItemBase implements IAugment,IItemModelProvider
{

    List<PartType> acceptedParts;
    float energyUse;

    public ItemAugment(String name, float energyUse)
    {
        super(name);
        setMaxStackSize(1);
        acceptedParts = new ArrayList<PartType>();
        this.energyUse = energyUse;

    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        super.addInformation(stack, playerIn, tooltip, advanced);
        String adding = "Can add to: ";
        for(PartType type : acceptedParts)
        {
            adding = adding+type.toString()+", ";
        }
        tooltip.add(adding);
        tooltip.add(TextFormatting.BLUE+"Energy use: "+this.getEnergyUse(stack));
        tooltip.add(TextFormatting.BOLD+"Hold Shift for instructions!");
        if(GuiScreen.isShiftKeyDown())
        {
            tooltip.remove(tooltip.size()-1);
            tooltip.add("For this augment to work you need:");
            tooltip.add("This item on both legs.");

        }
    }

    @Override
    public List<PartType> getParentTypes(ItemStack stack)
    {
        return acceptedParts;
    }

    @Override
    public boolean validate(ItemStack augment, ItemStack bodyPart, EntityPlayer player)
    {
        return CapHelper.getPlayerDataCap((EntityPlayer) player).validate();
    }

    @Override
    public float getEnergyUse(ItemStack stack)
    {
        return energyUse;
    }
}
