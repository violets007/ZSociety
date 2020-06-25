package com.zixuan007.society.event.society;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import com.zixuan007.society.domain.Society;

public class PlayerQuitSocietyEvent extends SocietyEvent {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public PlayerQuitSocietyEvent(Player player, Society society) {
        super(player, society);
    }

}