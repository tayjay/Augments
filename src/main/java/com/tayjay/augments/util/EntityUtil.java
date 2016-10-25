package com.tayjay.augments.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by tayjay on 2016-07-03.
 */
public class EntityUtil
{
    public static DataParameter<Float> ENERGY = EntityDataManager.createKey(Entity.class, DataSerializers.FLOAT);


    public static void registerParameter(Entity entity, DataParameter parameter,Object defaultValue)
    {
        entity.getDataManager().register(parameter,defaultValue);
    }

    public static <T extends Object> T getFromManager(Entity entity,DataParameter dataParameter,T defaultValue)
    {
        return (T) entity.getDataManager().get(dataParameter);
    }

    public static void setFlag(Entity entity, int flag, boolean set)
    {
        Method setFlag = ReflectionHelper.findMethod(Entity.class,entity, new String[]{"setFlag"},int.class,boolean.class);
        try
        {
            setFlag.invoke(entity,flag,set);
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        } catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    public static Object getFlag(Entity entity,int flag)
    {
        Method getFlag = ReflectionHelper.findMethod(Entity.class,entity,new String[]{"getFlag"},int.class);
        try
        {

            return getFlag.invoke(entity, flag);
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        } catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static int getTicksSinceLastSwing(EntityLivingBase entity)
    {
        return ReflectionHelper.getPrivateValue(EntityLivingBase.class,entity,"ticksSinceLastSwing");
    }
}
