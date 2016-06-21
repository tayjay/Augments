package com.tayjay.augments.augment.interfaces;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;

/**
 * Created by tayjay on 2016-06-18.<br/>
 * This augment will perform an action when the player interacts with something.
 */
public interface IPlayerInteract
{
    void onInteract(PlayerInteractEvent event);
}
