package com.tayjay.augments.network.packets;

import com.tayjay.augments.init.ModItems;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.ChatHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ElytraSound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.IItemHandler;

/**
 * Created by tayjay on 2016-07-03.
 */
public class PacketAugElytra extends PacketRunnable<PacketAugElytra>
{
    private int playerId;

    public PacketAugElytra(){}



    public PacketAugElytra(EntityPlayer player)
    {
        this.playerId = player.getEntityId();
    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.playerId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.playerId);
    }

    @Override
    public Runnable getServerRunnable(PacketAugElytra message, final MessageContext ctx)
    {
        return new Runnable()
        {
            @Override
            public void run()
            {
                ctx.getServerHandler().playerEntity.markPlayerActive();
                EntityPlayerMP player = ctx.getServerHandler().playerEntity;
                if(player!=null)
                {
                    if (!player.onGround && player.motionY < 0.0D && !player.isElytraFlying() && !player.isInWater())
                    {
                        IItemHandler parts = CapHelper.getPlayerPartsCap(player).getPartsInv();
                        if(parts.getStackInSlot(2)!=null)
                        {
                            IItemHandler augments = CapHelper.getAugHolderCap(parts.getStackInSlot(2)).getAugments();

                            for (int i = 0; i < augments.getSlots(); i++)
                            {
                                if (augments.getStackInSlot(i) != null && augments.getStackInSlot(i).getItem() == ModItems.augElytra)
                                {
                                    player.setElytraFlying();
                                    ChatHelper.send(player,"Attempting to fly!");
                                    return;
                                }
                            }
                        }
                    } else
                    {
                        player.clearElytraFlying();
                        ChatHelper.send(player,"Clearing flying!");
                    }
                }
            }
        };
    }

    @Override
    public Runnable getClientRunnable(PacketAugElytra message, MessageContext ctx)
    {
        return null;
    }
}
