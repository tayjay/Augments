package com.tayjay.augments.augment.interfaces;

/**
 * Augment that holds energy to be used by other augments. <br/>
 * Using RF energy system
 * Created by tayjm_000 on 2016-01-18.
 */
public interface IAugmentEnergyStorage
{
    int getMaxEnergy();

    int getCurrentEnergy();

    void setCurrentEnergy();
}
