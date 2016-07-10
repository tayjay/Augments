package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.item.IAugment;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.init.IItemModelProvider;
import com.tayjay.augments.item.ItemBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tayjay on 2016-06-23.<br/>
 * Items extending this will be augments to be used in the mod.
 */
public class ItemAugment extends ItemBase implements IAugment,IItemModelProvider
{
    List<PartType> acceptedParts;

    public ItemAugment(String name)
    {
        super(name);
        setMaxStackSize(1);
        acceptedParts = new ArrayList<PartType>();
        //acceptedParts.add(PartType.HEAD);//TODO: Just for testing, remove soon!
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        super.addInformation(stack, playerIn, tooltip, advanced);
        String adding = "Can add to: ";
        for(PartType type : acceptedParts)
        {
            adding = adding+type.name()+", ";
        }
        tooltip.add(adding);
        tooltip.add("Energy use: "+this.getEnergyUse(stack));
    }

    @Override
    public List<PartType> getParentTypes(ItemStack stack)
    {
        return acceptedParts;
    }

    @Override
    public boolean validate(ItemStack augment, ItemStack bodyPart, EntityPlayer player)
    {
        return false;
    }

    @Override
    public float getEnergyUse(ItemStack stack)
    {
        return 0;
    }
}
