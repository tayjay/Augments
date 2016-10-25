package com.tayjay.augments.item.augments;

import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.item.IAugment;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.init.IItemModelProvider;
import com.tayjay.augments.item.ItemBase;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.network.packets.PacketActivateAugment;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.ChatHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;

/**
 * Created by tayjay on 2016-06-23.<br/>
 * Items extending this will be augments to be used in the mod.
 */
public class ItemAugment extends ItemBase implements IAugment,IItemModelProvider
{

    PartType partNeeded;
    float energyUse;
    int tier;
    String description;

    public ItemAugment(String name, int tier, PartType part, float energyUse, String description)
    {
        super(name);
        setMaxStackSize(1);
        partNeeded = part;
        this.tier = tier;
        this.energyUse = energyUse;
        this.description = description;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add(TextFormatting.BLUE+"Applies to: "+partNeeded);
        tooltip.add(TextFormatting.BLUE+"Tier required: "+this.tier);
        tooltip.add(TextFormatting.BLUE+"Energy use: "+this.getEnergyUse(stack));
        tooltip.add("Augment is active: "+this.isActive(stack,playerIn));
        if(!GuiScreen.isShiftKeyDown())
            tooltip.add(TextFormatting.BOLD+"Hold Shift for Description");
        else if(GuiScreen.isShiftKeyDown())
        {
            tooltip.add(description);
        }
    }

    @Override
    public boolean validate(ItemStack augment, EntityPlayer player)
    {
        return CapHelper.getPlayerDataCap((EntityPlayer) player).validate()
                && CapHelper.getAugmentDataCap(augment).isActive()
                && CapHelper.getPlayerBodyCap(player).getStackByPart(partNeeded)!=null;
    }

    @Override
    public float getEnergyUse(ItemStack stack)
    {
        return energyUse;
    }

    @Override
    public void tickAugment(ItemStack augmentStack, TickEvent.PlayerTickEvent event)
    {

    }

    @Override
    public void activate(ItemStack augment, EntityPlayer player)
    {
        if(isActive(augment,player))
        {
            CapHelper.getAugmentDataCap(augment).setActive(false);
            onDeactivate(augment, player);
            ChatHelper.clientMsg("Augment Disabled");
        }
        else
        {
            CapHelper.getAugmentDataCap(augment).setActive(true);
            onActivate(augment, player);
            ChatHelper.clientMsg("Augment Enabled");
        }
        if(FMLCommonHandler.instance().getEffectiveSide()== Side.CLIENT)
            NetworkHandler.sendToServer(new PacketActivateAugment());
    }

    public void onActivate(ItemStack augment,EntityPlayer player)
    {

    }

    public void onDeactivate(ItemStack augment,EntityPlayer player)
    {

    }

    @Override
    public boolean isActive(ItemStack augment, EntityPlayer player)
    {
        return CapHelper.getAugmentDataCap(augment).isActive();
    }

    @Override
    public boolean activeWhenCreated(ItemStack stack)
    {
        return true;
    }


}
