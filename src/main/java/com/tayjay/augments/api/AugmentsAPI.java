package com.tayjay.augments.api;

import com.tayjay.augments.api.capabilities.IAugHolderProvider;
import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
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

    @CapabilityInject(IAugHolderProvider.class)
    public static final Capability<IAugHolderProvider> AUGMENT_HOLDER_CAPABILITY = null;

    @CapabilityInject(IPlayerDataProvider.class)
    public static final Capability<IPlayerDataProvider> PLAYER_DATA_CAPABILITY = null;
}
