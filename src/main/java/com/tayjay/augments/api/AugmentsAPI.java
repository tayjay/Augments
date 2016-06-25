package com.tayjay.augments.api;

import com.tayjay.augments.api.capabilities.IPlayerPartsProvider;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

/**
 * Created by tayjay on 2016-06-24.
 */
public class AugmentsAPI
{
    private AugmentsAPI(){}

    @CapabilityInject(IPlayerPartsProvider.class)
    public static final Capability<IPlayerPartsProvider> PLAYER_PARTS_CAPABILITY = null;
}
