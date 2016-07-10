package com.tayjay.augments.util;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by tayjay on 2016-07-03.
 */
public class EntityUtil
{
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
}
