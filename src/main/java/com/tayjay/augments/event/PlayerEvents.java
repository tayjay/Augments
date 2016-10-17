package com.tayjay.augments.event;

import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.capabilities.IPlayerBodyProvider;
import com.tayjay.augments.capability.PlayerDataImpl;
import com.tayjay.augments.capability.PlayerBodyImpl;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.network.packets.PacketREQSyncParts;
import com.tayjay.augments.util.CapHelper;
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
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by tayjay on 2016-06-24.<br/>
 * Events that are run on the player. Registering properties and such.
 */
public class PlayerEvents
{
    public static final int SYNC_TICKS = 20;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void playerSyncClient(TickEvent.PlayerTickEvent event)
    {
        if(!event.player.worldObj.isRemote)
            return;
        long time = (event.player.ticksExisted);
        if(time % SYNC_TICKS == 0)
        {
            NetworkHandler.sendToServer(new PacketREQSyncParts(event.player));
        }
    }


    @SubscribeEvent
    public void cloneEvent(PlayerEvent.Clone event)
    {

        NBTTagCompound data = CapHelper.getPlayerDataCap(event.getOriginal()).serializeNBT();
        NBTTagCompound parts = CapHelper.getPlayerBodyCap(event.getOriginal()).serializeNBT();
        CapHelper.getPlayerDataCap(event.getEntityPlayer()).deserializeNBT(data);
        CapHelper.getPlayerDataCap(event.getEntityPlayer()).reboot();
        CapHelper.getPlayerBodyCap(event.getEntityPlayer()).deserializeNBT(parts);

        LogHelper.debug("Cloned Body Parts for "+event.getEntityPlayer().getName());
    }

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent.Entity event)
    {
        if(event.getEntity() instanceof EntityPlayer)
        {
            event.addCapability(PlayerDataImpl.Provider.NAME,new PlayerDataImpl.Provider((EntityPlayer) event.getEntity()));
            event.addCapability(PlayerBodyImpl.Provider.NAME,new PlayerBodyImpl.Provider((EntityPlayer)event.getEntity()));
            LogHelper.debug("Player Capabilities added to player");
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if(!event.getEntity().worldObj.isRemote && event.getEntity() instanceof EntityPlayerMP)
        {
            EntityPlayerMP player = ((EntityPlayerMP) event.getEntity());
            IPlayerBodyProvider parts = CapHelper.getPlayerBodyCap(player);
            IPlayerDataProvider data = CapHelper.getPlayerDataCap(player);
            parts.sync((EntityPlayerMP)event.getEntity());
            data.sync((EntityPlayerMP)event.getEntity());
            LogHelper.info("Synced own Capabilities to client");
            //todo: Not including this yet. Hopefully "StartTracking" event will handle this.
            //NetworkHandler.INSTANCE.sendToAllAround(new PacketSyncPlayerBody(parts.serializeNBT(),player),new NetworkRegistry.TargetPoint(player.dimension,player.posX,player.posY,player.posZ,40));
        }
    }

    @SubscribeEvent
    public void startTracking(PlayerEvent.StartTracking event)
    {
        if(!event.getEntityPlayer().worldObj.isRemote && event.getTarget() instanceof EntityPlayerMP && event.getEntityPlayer() instanceof EntityPlayerMP)
        {
            CapHelper.getPlayerBodyCap(event.getEntityPlayer()).syncToOther((EntityPlayerMP) event.getTarget(), (EntityPlayerMP) event.getEntityPlayer());
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


    /*
    @SubscribeEvent
    public void renderHand(RenderHandEvent event)
    {
        EntityPlayer clientPlayer = Minecraft.getMinecraft().thePlayer;
        ItemStack rightArm = CapHelper.getPlayerBodyCap(clientPlayer).getStackByPart(PartType.ARM_RIGHT);

        if(rightArm!=null)
        {
            GL11.glColor4d(1,1,1,1);
            if(clientPlayer.isSneaking())
                GL11.glTranslated(0,-0.2,0);
            RenderPlayer renderPlayer = ReflectionHelper.getPrivateValue(RenderManager.class,Minecraft.getMinecraft().getRenderManager(),"playerRenderer");
            ((IBodyPart) rightArm.getItem()).renderOnPlayer(rightArm,clientPlayer, renderPlayer);
        }
    }
    */



}
