package com.zixuan007.society.event.marry;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;
import com.zixuan007.society.domain.Marry;


/**
 * @author zixuan007
 */
public class DivorceMarryEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    private Marry marry;

    public static HandlerList getHandlers() {
        return handlers;
    }

    public DivorceMarryEvent(Player player,Marry marry) {
        this.player = player;
        this.marry=marry;
    }

    public Marry getMarry() {
        return marry;
    }

}
