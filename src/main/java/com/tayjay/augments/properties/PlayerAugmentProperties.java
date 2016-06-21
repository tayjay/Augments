package com.tayjay.augments.properties;

import com.tayjay.augments.augment.interfaces.IAugment;
import com.tayjay.augments.augment.interfaces.IBodyPart;
import com.tayjay.augments.inventory.InventoryAugmentPlayer;
import com.tayjay.augments.inventory.InventoryBodyPart;
import com.tayjay.augments.network.MessageSyncPlayerAugments;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.proxy.CommonProxy;
import com.tayjay.augments.util.LogHelper;
import com.tayjay.augments.util.ServerUtil;
import com.typesafe.config.ConfigException;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tayjm_000 on 2016-01-17.
 */
public class PlayerAugmentProperties implements IExtendedEntityProperties
{
    public static final String AUG_PROP_NAME = "PlayerAugmentProperties";
    private final EntityPlayer player;
    public final InventoryAugmentPlayer inventory;
    public ArrayList<ItemStack> augments;
    /** Whether properties have changed and need updating*/
    private boolean isDirty;
    /** Extra health to be added to this*/
    private double extraHealth;
    /** Bonus attack damage */
    private double attackBonus;
    /** Whether this is visible or not */
    private boolean visibility;
    /** Does the player experience hunger */
    private boolean noHunger;
    /** Can this fly */
    private boolean canFly;
    /** Does this have extra Lives, and can survive death. How many times. */
    private byte extraLives;
    /** Base armour points with nothing equip */
    private byte baseArmour;
    /** Force this to be immune to fire */
    private boolean immuneToFire;
    /** Aura around this entity. Such as a status effect. */
    private byte aura; //Could also use an object of aura type
    /** Size of this entity. 1f is normal. Scale from there. */
    private float size;
    /** Force this potion effect */
    private PotionEffect[] activeEffects;

    public PlayerAugmentProperties(EntityPlayer p)
    {
        this.player = p;
        this.inventory = new InventoryAugmentPlayer(p);
        augments = new ArrayList<ItemStack>();
        loadAugmentsFromInventory();
    }

    public static final void register(EntityPlayer p)
    {
        //LogHelper.info("Registering PlayerAugmentProperties to "+p.getCommandSenderName());
        p.registerExtendedProperties(PlayerAugmentProperties.AUG_PROP_NAME, new PlayerAugmentProperties(p));
    }

    public ArrayList<ItemStack> loadAugmentsFromInventory()
    {
        ArrayList<ItemStack> augmentsBackup = (ArrayList)augments.clone();
        augments.clear();
        resetAbilities();
        for(int i = 0; i < inventory.getSizeInventory();i++)
        {
            ItemStack bodyPart = inventory.getStackInSlot(i);
            if(bodyPart!=null)
            {
                InventoryBodyPart inv = new InventoryBodyPart(bodyPart);

                for (int j = 0; j < inv.getSizeInventory(); j++) //O(n^2) very inefficent
                {
                    this.augments.add(inv.getStackInSlot(j));
                    ((IAugment) inv.getStackInSlot(j).getItem()).onAdd(inv.getStackInSlot(j), this.player);
                }
            }
        }
        return augments;
    }

    public void resetAbilities()
    {
        extraHealth = 0;
        attackBonus = 0;
        visibility = true;
        noHunger = false;
        canFly = false;
        extraLives = 0;
        baseArmour = 0;
        immuneToFire = false;
        aura = -1;
        size = 1;
        activeEffects = null;
    }


    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        NBTTagCompound tag = new NBTTagCompound();
        compound.setTag(AUG_PROP_NAME, tag);
        tag.setBoolean("isDirty", isDirty);
        this.inventory.writeToNBT(compound);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        NBTTagCompound properties=null;

        if(compound!=null&&compound.hasKey(AUG_PROP_NAME))
            properties = (NBTTagCompound) compound.getTag(AUG_PROP_NAME);

        if(properties!=null)
        {
            this.isDirty = properties.getBoolean("isDirty");
            this.inventory.readFromNBT(compound);
            loadAugmentsFromInventory();
        }
    }

    @Override
    public void init(Entity entity, World world)
    {

    }

    public boolean isServerSide() {
        return this.player instanceof EntityPlayerMP;
    }

    public void syncAll()
    {
        if(this.isServerSide())
            NetworkHandler.INSTANCE.sendToAll(new MessageSyncPlayerAugments(this));
    }

    public void requestSyncAll()
    {
        if(!this.isServerSide())
            NetworkHandler.INSTANCE.sendToServer(new MessageSyncPlayerAugments());
    }

    /**
     * Returns PlayerAugmentProperties properties for player
     * This method is for convenience only; it will make your code look nicer
     */
    public static final PlayerAugmentProperties get(EntityPlayer e)
    {
        return (PlayerAugmentProperties) e.getExtendedProperties(AUG_PROP_NAME);
    }
}
