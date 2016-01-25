package com.tayjay.augments.properties;

import com.tayjay.augments.augment.interfaces.IAugment;
import com.tayjay.augments.inventory.InventoryAugmentPlayer;
import com.tayjay.augments.network.MessageSyncPlayerAugments;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.proxy.CommonProxy;
import com.tayjay.augments.util.LogHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import java.util.List;

/**
 * Created by tayjm_000 on 2016-01-17.
 */
public class PlayerAugmentProperties implements IExtendedEntityProperties
{
    public static final String AUG_PROP_NAME = "PlayerAugmentProperties";
    private final EntityPlayer player;
    public final InventoryAugmentPlayer inventory;
    private boolean isDirty;

    public PlayerAugmentProperties(EntityPlayer p)
    {
        this.player = p;
        this.inventory = new InventoryAugmentPlayer();
    }

    public static final void register(EntityPlayer p)
    {
        //LogHelper.info("Registering PlayerAugmentProperties to "+p.getCommandSenderName());
        p.registerExtendedProperties(PlayerAugmentProperties.AUG_PROP_NAME, new PlayerAugmentProperties(p));
    }


    public static final void loadProxyData(EntityPlayer player) {
        PlayerAugmentProperties playerData = PlayerAugmentProperties.get(player);
        NBTTagCompound savedData = player.getEntityData();
        if (savedData != null) { playerData.loadNBTData(savedData); }
        // we are replacing the entire sync() method with a single line; more on packets later
        // data can by synced just by sending the appropriate packet, as everything is handled internally by the packet class
        NetworkHandler.INSTANCE.sendTo(new MessageSyncPlayerAugments(player), (EntityPlayerMP) player);
    }


    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("isDirty",isDirty);
        compound.setTag(AUG_PROP_NAME,tag);
        this.inventory.writeToNBT(compound);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        NBTTagCompound properties = (NBTTagCompound) compound.getTag(AUG_PROP_NAME);
        this.isDirty = properties.getBoolean("isDirty");
        this.inventory.readFromNBT(compound);
    }

    @Override
    public void init(Entity entity, World world)
    {

    }

    /**
     * Returns EntityAugmentPropeties properties for entity
     * This method is for convenience only; it will make your code look nicer
     */
    public static final PlayerAugmentProperties get(EntityPlayer e)
    {
        return (PlayerAugmentProperties) e.getExtendedProperties(AUG_PROP_NAME);
    }
}
