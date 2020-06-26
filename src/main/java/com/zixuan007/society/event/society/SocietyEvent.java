package com.zixuan007.society.event.society;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;
import com.zixuan007.society.domain.Society;

/**
 * @author zixuan007
 */
public abstract class SocietyEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private Society society;

    public static HandlerList getHandlers() {
        return handlers;
    }

    public SocietyEvent(Player player, Society society) {
        this.player = player;
        this.society = society;
    }

    public Society getSociety() {
        return this.society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }

}