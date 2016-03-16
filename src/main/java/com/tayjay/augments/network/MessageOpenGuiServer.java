package com.tayjay.augments.network;

import com.tayjay.augments.AugmentsCore;
import com.tayjay.augments.handler.GuiHandler;
import com.tayjay.augments.util.ChatHelper;
import com.tayjay.augments.util.LogHelper;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by tayjm_000 on 2016-01-29.<br/>
 * Force a GUI to open via the server to sync any inventory changes.
 */
public class MessageOpenGuiServer extends MessageBase<MessageOpenGuiServer>
{
    private int guiId;
    /** Need default constructor for Forge to run*/
    public MessageOpenGuiServer(){}

    /**
     * Get which gui to open by id.
     * @param id    Gui Id to open
     */
    public MessageOpenGuiServer(int id)
    {
        this.guiId = id;
    }

    @Override
    public void handleServerSide(MessageOpenGuiServer message, EntityPlayer player)
    {
        player.openGui(AugmentsCore.instance, message.guiId, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
    }

    @Override
    public void handleClientSide(MessageOpenGuiServer message, EntityPlayer player)
    {
        //NOOP
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        guiId = buf.readInt();
        LogHelper.info("Reading Bytes for MessageOpenGuiServer "+guiId);

    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(guiId);
        LogHelper.info("Writing Bytes for MessageOpenGuiServer " + guiId);

    }
}
