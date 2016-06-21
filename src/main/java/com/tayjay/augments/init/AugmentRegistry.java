package com.tayjay.augments.init;

import com.tayjay.augments.augment.*;
import com.tayjay.augments.augment.interfaces.*;
import com.tayjay.augments.util.LogHelper;
import com.tayjay.augments.util.ServerUtil;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

import java.util.*;


/**
 * Created by tayjm_000 on 2016-04-04.
 */
public class AugmentRegistry
{

    private static int augmentCurrentId=0;
    /*
    private LinkedHashMap<Integer,IAugment> augmentMap = new LinkedHashMap<Integer, IAugment>();
    private LinkedHashMap<String, Integer> augmentNameToId = new LinkedHashMap<String, Integer>();
    */
    public static Map<Integer,IAugment> augments = new HashMap<Integer, IAugment>();
    public static List<IAugment> augmentsRegistered = new ArrayList<IAugment>();


    private int bodyPartCurrentId=0;
    public static LinkedHashMap<Integer,IBodyPart> bodyPartMap = new LinkedHashMap<Integer, IBodyPart>();
    private LinkedHashMap<String, Integer> bodyPartNameToId = new LinkedHashMap<String, Integer>();

    /*
    private int componentCurrentId=0;
    private LinkedHashMap<Integer,IComponent> componentMap = new LinkedHashMap<Integer, IComponent>();
    private LinkedHashMap<String, Integer> componentNameToId = new LinkedHashMap<String, Integer>();
    */


    public static void registerAugment(IAugment augment)
    {
        if(augment==null)
            return;
        augments.put(Item.getIdFromItem((Item)augment),augment);

        augmentsRegistered.add(augment);


        LogHelper.debug("Registered Augment "+augment.getName()+", ID: "+Item.getIdFromItem((Item)augment));
    }

    public static void registerBodyPart(IBodyPart bodyPart)
    {
        if(bodyPart==null)
            return;
        bodyPartMap.put(Item.getIdFromItem((Item)bodyPart),bodyPart);
        LogHelper.debug("Registered BodyPart "+bodyPart.getName()+", ID: "+Item.getIdFromItem((Item)bodyPart));
    }

    /*
    public IAugment getAugment(String name)
    {
        if(augmentNameToId.containsKey(name))
            return getAugment(augmentNameToId.get(name));
        LogHelper.warn("Augment with name "+name+" not found!");
        return null;
    }

    public IAugment getAugment(int id)
    {
        if(augmentMap.containsKey(id))
            return augmentMap.get(id);
        LogHelper.warn("Augment with ID "+id+" not found!");
        return null;
    }

    public IBodyPart getBodyPart(String name)
    {
        if(bodyPartNameToId.containsKey(name))
            return getBodyPart(bodyPartNameToId.get(name));
        LogHelper.warn("BodyPart with name "+name+" not found!");
        return null;
    }
    */



    public void init()
    {
        //Register Augments here
        

        //Register BodyParts here


        //Register Components here

        //All custom augments/bodyparts/components can be registered using this event.
    }



}
