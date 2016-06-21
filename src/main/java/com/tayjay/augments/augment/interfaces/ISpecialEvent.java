package com.tayjay.augments.augment.interfaces;

import cpw.mods.fml.common.eventhandler.Event;

/**
 * Created by tayjay on 2016-06-18.<br/>
 * If an augment needs to be triggered in a way not supported by other means use this.<br/>
 * **Not sure if this is a good idea!**
 */
public interface ISpecialEvent<T extends Event>
{
    void trigger(T event);
}
