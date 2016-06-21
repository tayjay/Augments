package com.tayjay.augments.properties;

import com.tayjay.augments.augment.interfaces.IAugment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

/**
 * Created by tayjm_000 on 2016-01-17.
 */
public class ExtendedProperties implements IExtendedEntityProperties
{
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
    private byte aura; //Could also use an Enum of aura types
    /** Size of this entity. 1f is normal. Scale from there. */
    private float size;
    /** Force this potion effect */
    private PotionEffect[] activeEffects;


    @Override
    public void saveNBTData(NBTTagCompound compound)
    {

    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {

    }

    @Override
    public void init(Entity entity, World world)
    {

    }


}
