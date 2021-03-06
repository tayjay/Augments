package com.tayjay.augments.client.handler;

import com.tayjay.augments.api.events.IActivate;
import com.tayjay.augments.api.item.IAugment;
import com.tayjay.augments.client.settings.Keybindings;
import com.tayjay.augments.handler.GuiHandler;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.network.packets.PacketOpenGui;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

/**
 * Created by tayjay on 2016-06-30.
 */
public class KeyInputHandler
{
    private Keybindings getPressedKey()
    {
        for(Keybindings key : Keybindings.values())
        {
            if(key.isPressed()) return key;
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.KeyInputEvent event)
    {
        Keybindings key = getPressedKey();
        if (key != null)
        {
            switch (key)
            {
                case ACTIVATE:
                    /*
                    IItemHandler playerParts = CapHelper.getPlayerBodyCap(Minecraft.getMinecraft().thePlayer).getBodyParts();
                    IItemHandler augments;
                    for(int i = 0; i<playerParts.getSlots();i++)
                    {
                        if(playerParts.getStackInSlot(i) != null)
                        {
                            augments = CapHelper.getAugHolderCap(playerParts.getStackInSlot(i)).getAugments();
                            for (int j = 0; j < augments.getSlots(); j++)
                            {
                                if(augments.getStackInSlot(j)!=null && augments.getStackInSlot(j).getItem() instanceof IActivate)
                                {
                                    ((IActivate) augments.getStackInSlot(j).getItem()).activate(augments.getStackInSlot(j),playerParts.getStackInSlot(i),Minecraft.getMinecraft().thePlayer);
                                }
                            }
                        }
                    }
                    */
                    ItemStack currentAugment = CapHelper.getPlayerDataCap(Minecraft.getMinecraft().thePlayer).getCurrentAugment();
                    if(currentAugment!=null)
                    {
                        ((IAugment)currentAugment.getItem()).activate(currentAugment,Minecraft.getMinecraft().thePlayer);
                    }
                    break;
                case CYCLE:
                    CapHelper.getPlayerDataCap(Minecraft.getMinecraft().thePlayer).cycleActiveAugment();
                    break;
                case OPEN_PLAYER_BODY_GUI:
                    NetworkHandler.sendToServer(new PacketOpenGui(GuiHandler.GuiIDs.PLAYER_BODY.ordinal()));
                    break;

            }
        }
    }
}
