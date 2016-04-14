package com.tayjay.augments.tileentity;

import com.tayjay.augments.network.DescriptionHandler;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by tayjm_000 on 2016-04-05.
 */
public abstract class TileEntityAug extends TileEntity
{
    @Override
    public Packet getDescriptionPacket()
    {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(xCoord);
        buf.writeInt(yCoord);
        buf.writeInt(zCoord);
        writeToPacket(buf);
        return new FMLProxyPacket(buf, DescriptionHandler.CHANNEL);
    }

    protected abstract void writeToPacket(ByteBuf buf);

    public abstract void readFromPacket(ByteBuf buf);

    public void onGuiButtonPress(int id)
    {

    }

    public void onGuiTextfieldUpdate(int id, String text){}
}
