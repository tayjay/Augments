package com.tayjay.augments.init;

import com.tayjay.augments.AugmentsMod;
import com.tayjay.augments.entity.EntityCyborg;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * Created by tayjm_000 on 2016-03-26.
 */
public class ModEntities
{

    public static void init()
    {
        registerEntity();
    }

    public static void registerEntity()
    {
        createEntity(EntityCyborg.class, "Cyborg",0x000000,0xFFFFFF);
    }

    public static void createEntity(Class entityClass, String entityName, int solidColour, int spotColour)
    {
        int randomId = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(entityClass,entityName,randomId);
        EntityRegistry.registerModEntity(entityClass,entityName,randomId, AugmentsMod.instance,64,1,true);
        EntityRegistry.addSpawn(entityClass,2,0,1, EnumCreatureType.creature, BiomeGenBase.forest);

        createEgg(randomId,solidColour,spotColour);
    }

    private static void createEgg(int randomId, int solidColour, int spotColour)
    {
        EntityList.entityEggs.put(Integer.valueOf(randomId),new EntityList.EntityEggInfo(randomId,solidColour,spotColour));
    }
}
