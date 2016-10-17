package com.tayjay.augments.api;

import com.tayjay.augments.api.capabilities.IAugDataProvider;
import com.tayjay.augments.api.capabilities.IAugHolderProvider;
import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.capabilities.IPlayerBodyProvider;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

/**
 * Created by tayjay on 2016-06-24.
 */
public class AugmentsAPI
{
    private AugmentsAPI(){}

    @CapabilityInject(IAugDataProvider.class)
    public static final Capability<IAugDataProvider> AUGMENT_DATA_CAPABILITY = null;
    @CapabilityInject(IPlayerBodyProvider.class)
    public static final Capability<IPlayerBodyProvider> PLAYER_BODY_CAPABILITY = null;

    @Deprecated
    @CapabilityInject(IAugHolderProvider.class)
    public static final Capability<IAugHolderProvider> AUGMENT_HOLDER_CAPABILITY = null;

    @CapabilityInject(IPlayerDataProvider.class)
    public static final Capability<IPlayerDataProvider> PLAYER_DATA_CAPABILITY = null;
}
