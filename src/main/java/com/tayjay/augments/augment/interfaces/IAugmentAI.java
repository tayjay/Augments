package com.tayjay.augments.augment.interfaces;

/**
 * This augment will change the AI of the attached entity
 * Created by tayjm_000 on 2016-01-18.
 */
public interface IAugmentAI
{
    /**
     * When fired, will change the AI of this entity.
     * @return Change was successful
     */
    boolean changeAI();
}
