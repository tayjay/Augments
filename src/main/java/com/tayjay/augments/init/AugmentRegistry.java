package com.tayjay.augments.init;

import com.tayjay.augments.augment.interfaces.IAddon;
import com.tayjay.augments.augment.interfaces.IAugment;
import com.tayjay.augments.augment.interfaces.IComponent;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by tayjm_000 on 2016-04-04.
 */
public class AugmentRegistry
{

    public static void register(Object o)
    {
        if(o instanceof IAugment)
            Augment.register((IAugment)o);
        else if(o instanceof IComponent)
            Component.register((IComponent)o);
        else if(o instanceof IAddon)
            Addon.register((IAddon)o);
        return;
    }
    public static class Augment
    {
        private static LinkedHashMap<String,IAugment> augmentMap = new LinkedHashMap<String, IAugment>();

        public static IAugment getAugment(String name)
        {
            if(augmentMap.containsKey(name))
                return augmentMap.get(name);
            return null;
        }

        private static void register(IAugment augment)
        {
            if(augment==null)
                return;
            augmentMap.put(augment.getAugmentName(),augment);
        }
    }

    public static class Component
    {
        private static LinkedHashMap<String,IComponent> componentMap = new LinkedHashMap<String, IComponent>();

        public static IComponent getComponent(String name)
        {
            if(componentMap.containsKey(name))
                return componentMap.get(name);
            return null;
        }

        private static void register(IComponent component)
        {
            if(component==null)
                return;
            componentMap.put(component.getComponentName(),component);
        }
    }

    public static class Addon
    {
        private static LinkedHashMap<String, IAddon> addonMap = new LinkedHashMap<String, IAddon>();

        public static IAddon getAddon(String name)
        {
            if(addonMap.containsKey(name))
                return addonMap.get(name);
            return null;
        }

        private static void register(IAddon addon)
        {
            if(addon==null)
                return;
            addonMap.put(addon.getAddonName(),addon);
        }
    }
}
