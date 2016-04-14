package com.tayjay.augments.network;

import com.tayjay.augments.util.ChatHelper;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;

/**
 * Created by tayjm_000 on 2016-03-26.
 */
public class PacketHackEntity extends MessageBase<PacketHackEntity>
{
    int entityId;


    public PacketHackEntity(){}

    public PacketHackEntity(EntityLivingBase entity, EntityPlayer player)
    {
        entityId = entity.getEntityId();

    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        entityId = buf.readInt();

    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(entityId);

    }

    @Override
    public void handleServerSide(PacketHackEntity message, EntityPlayer player)
    {
        EntityLivingBase entity = (EntityLivingBase)player.worldObj.getEntityByID(message.entityId);
        if (entity!=null && entity instanceof EntityTameable)
        {
            EntityTameable livingBase = (EntityTameable) entity;
            if (!livingBase.worldObj.isRemote)
            {
                livingBase.setTamed(true);
                livingBase.setPathToEntity((PathEntity) null);
                livingBase.setAttackTarget((EntityLivingBase) null);

                livingBase.setHealth(20.0F);
                livingBase.func_152115_b(player.getUniqueID().toString());

                livingBase.worldObj.setEntityState(livingBase, (byte) 7);
                ChatHelper.sendTo(player, "Owner Changed!");
            }
            else
            {
                ChatHelper.sendTo(player, "Failed at entity side.");
            }
        }
    }

    @Override
    public void handleClientSide(PacketHackEntity message, EntityPlayer player)
    {

    }
}
