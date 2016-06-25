package com.tayjay.augments.event;

import com.tayjay.augments.api.AugmentsAPI;
import com.tayjay.augments.api.capabilities.IPlayerPartsProvider;
import com.tayjay.augments.api.capabilities.PlayerPartsImpl;
import com.tayjay.augments.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by tayjay on 2016-06-24.<br/>
 * Events that are run on the player. Registering properties and such.
 */
public class PlayerEvents
{
    @SubscribeEvent
    public void cloneEvent(PlayerEvent.Clone event)
    {
        NBTTagCompound parts = event.getOriginal().getCapability(AugmentsAPI.PLAYER_PARTS_CAPABILITY,null).serializeNBT();
        event.getEntityPlayer().getCapability(AugmentsAPI.PLAYER_PARTS_CAPABILITY,null).deserializeNBT(parts);
        LogHelper.debug("Cloned Body Parts for "+event.getEntityPlayer().getName());
    }

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent.Entity event)
    {
        if(event.getEntity() instanceof EntityPlayer)
        {
            event.addCapability(PlayerPartsImpl.Provider.NAME,new PlayerPartsImpl.Provider());
            LogHelper.debug("PlayerParts Capability added to player");
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if(!event.getEntity().worldObj.isRemote && event.getEntity() instanceof EntityPlayerMP)
        {
            EntityPlayerMP player = ((EntityPlayerMP) event.getEntity());
            IPlayerPartsProvider parts = player.getCapability(AugmentsAPI.PLAYER_PARTS_CAPABILITY,null);
            parts.sync((EntityPlayerMP)event.getEntity());
            LogHelper.info("Synced own Capabilities to client");
            //todo: Not including this yet. Hopefully "StartTracking" event will handle this.
            //NetworkHandler.INSTANCE.sendToAllAround(new PacketSyncPlayerParts(parts.serializeNBT(),player),new NetworkRegistry.TargetPoint(player.dimension,player.posX,player.posY,player.posZ,40));
        }
    }

    @SubscribeEvent
    public void startTracking(PlayerEvent.StartTracking event)
    {
        if(!event.getEntityPlayer().worldObj.isRemote && event.getTarget() instanceof EntityPlayerMP && event.getEntityPlayer() instanceof EntityPlayerMP)
        {
            event.getTarget().getCapability(AugmentsAPI.PLAYER_PARTS_CAPABILITY, null).syncToOther((EntityPlayerMP) event.getTarget(), (EntityPlayerMP) event.getEntityPlayer());
            LogHelper.info("Synced Capabilities from "+event.getTarget().getName()+" to "+event.getEntityPlayer().getName());
        }
    }

    @SubscribeEvent
    public void onConstruct(EntityEvent.EntityConstructing event)
    {
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER && event.getEntity() instanceof EntityPlayer && !(event.getEntity() instanceof FakePlayer))
        {
            //Clear any offline data
        }
    }
}
