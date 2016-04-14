package com.tayjay.augments.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/**
 * Created by tayjm_000 on 2016-03-26.
 */
public class EntityCyborg extends EntityTameable implements ITieredEntity, IAugmentable
{
    public EntityCyborg(World p_i1738_1_)
    {
        super(p_i1738_1_);
        this.setSize(1.1F,4.2F);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        //this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, getMoveSpeed(), true));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, getMoveSpeed(), 10.0F, 2.0F));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.tasks.addTask(5, new EntityAIAttackOnCollide(this, EntityPlayer.class, getMoveSpeed(), false));
        this.targetTasks.addTask(6, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, true, IMob.mobSelector));
    }

    public EntityCyborg(World world,EntityPlayer owner)
    {
        this(world);
        func_152115_b(owner.getUniqueID().toString());
        setTamed(true);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity)
    {
        return entity.attackEntityFrom(DamageSource.causeMobDamage(this),6.0F);
    }

    public double getMoveSpeed()
    {
        return getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
    }

    @Override
    public EntityAgeable createChild(EntityAgeable p_90011_1_)
    {
        return null;
    }

    public boolean isAIEnabled()
    {
        return true;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.52F);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.getDataWatcher().addObject(27, (byte) 1);//Watch style/tier of cyborg
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompound)
    {
        super.readEntityFromNBT(tagCompound);
        if(tagCompound.hasKey("Tier"))
            setTier(tagCompound.getByte("Tier"));
        else
            setTier((byte)1);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setByte("Tier",getTier());
    }

    @Override
    public byte getTier()
    {
        return this.getDataWatcher().getWatchableObjectByte(27);
    }

    @Override
    public void setTier(byte tier)
    {
        if(!this.worldObj.isRemote)
        {
            this.getDataWatcher().updateObject(27, tier);
            switch (tier)
            {
                case 1:
                    this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100);
                    break;
                case 2:
                    this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200);
                    break;
                case 3:
                    this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_)
    {
        setTier((byte)2);
        return super.onSpawnWithEgg(p_110161_1_);
    }
}
