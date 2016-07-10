package com.tayjay.augments.event;

import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.capabilities.IPlayerPartsProvider;
import com.tayjay.augments.capability.PlayerDataImpl;
import com.tayjay.augments.capability.PlayerPartsImpl;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.network.packets.PacketREQSyncParts;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.LogHelper;
import com.tayjay.augments.util.RenderUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;

/**
 * Created by tayjay on 2016-06-24.<br/>
 * Events that are run on the player. Registering properties and such.
 */
public class PlayerEvents
{
    @SideOnly(Side.CLIENT)
    public static HashMap<Integer,PacketREQSyncParts> toREQSync = new HashMap<Integer,PacketREQSyncParts>();
    public static final int SYNC_TICKS = 20;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void playerSync(TickEvent.PlayerTickEvent event)
    {
        if(event.player.worldObj.getTotalWorldTime() % SYNC_TICKS == 0 && toREQSync.containsKey(event.player.getEntityId()))
        {
            NetworkHandler.INSTANCE.sendToServer(toREQSync.get(event.player.getEntityId()));
            toREQSync.remove(event.player.getEntityId());
        }
    }


    @SubscribeEvent
    public void cloneEvent(PlayerEvent.Clone event)
    {
        NBTTagCompound parts = CapHelper.getPlayerPartsCap(event.getOriginal()).serializeNBT();
        NBTTagCompound data = CapHelper.getPlayerDataCap(event.getOriginal()).serializeNBT();
        CapHelper.getPlayerPartsCap(event.getEntityPlayer()).deserializeNBT(parts);
        CapHelper.getPlayerDataCap(event.getEntityPlayer()).deserializeNBT(data);
        CapHelper.getPlayerDataCap(event.getEntityPlayer()).reboot();

        LogHelper.debug("Cloned Body Parts for "+event.getEntityPlayer().getName());
    }

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent.Entity event)
    {
        if(event.getEntity() instanceof EntityPlayer)
        {
            event.addCapability(PlayerPartsImpl.Provider.NAME,new PlayerPartsImpl.Provider());
            event.addCapability(PlayerDataImpl.Provider.NAME,new PlayerDataImpl.Provider((EntityPlayer) event.getEntity()));
            LogHelper.debug("Player Capabilities added to player");
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if(!event.getEntity().worldObj.isRemote && event.getEntity() instanceof EntityPlayerMP)
        {
            EntityPlayerMP player = ((EntityPlayerMP) event.getEntity());
            IPlayerPartsProvider parts = CapHelper.getPlayerPartsCap(player);
            IPlayerDataProvider data = CapHelper.getPlayerDataCap(player);
            parts.sync((EntityPlayerMP)event.getEntity());
            data.sync((EntityPlayerMP)event.getEntity());
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
            CapHelper.getPlayerPartsCap(event.getEntityPlayer()).syncToOther((EntityPlayerMP) event.getTarget(), (EntityPlayerMP) event.getEntityPlayer());
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
