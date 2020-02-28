package com.zixuan007.society.event;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;
import com.zixuan007.society.domain.Society;

public class PlayerApplyJoinSocietyEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private Society society;
    public static HandlerList getHandlers() {
        return handlers;
    }

    public PlayerApplyJoinSocietyEvent(Player player, Society society) {
        this.player = player;
        this.society = society;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Society getSociety() {
        return society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }

}
