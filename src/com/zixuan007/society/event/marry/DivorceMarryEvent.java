package com.zixuan007.society.event.marry;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;

import java.util.Date;

public class DivorceMarryEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    public static HandlerList getHandlers() {
        return handlers;
    }

    public DivorceMarryEvent(Player player) {
        this.player = player;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
