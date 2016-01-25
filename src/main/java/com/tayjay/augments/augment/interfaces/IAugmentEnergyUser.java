package com.tayjay.augments.augment.interfaces;

/**
 * This augment will use energy from an IAugmentEnergyStorage
 * Created by tayjm_000 on 2016-01-18.
 */
public interface IAugmentEnergyUser
{
    /**
     * Get how much energy this augment uses per operation
     * @return Energy used per operation
     */
    int getEnergyUsage();
}
