package com.zixuan007.society.event.marry;

import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;

import java.util.Date;

public class DivorceMarryEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

}
