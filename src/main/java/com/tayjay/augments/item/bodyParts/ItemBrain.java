package com.tayjay.augments.item.bodyParts;

import com.tayjay.augments.api.item.PartType;

/**
 * Created by tayjay on 2016-08-02.
 * The brain of the player. Can be removed to add augments to it. Player cant do anything without a brain though.
 */
public class ItemBrain extends ItemBodyPart
{
    public ItemBrain(String name, int tier, int storageSize, PartType type)
    {
        super(name, tier, 0, storageSize, "", "", PartType.BRAIN);
    }


}
