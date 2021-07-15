package com.zixuan007.society.event.title;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;

/**
 * @author zixuan007
 */
public class PlayerBuyTitleEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    private String title;
    private double money;

    public static HandlerList getHandlers() {
        return handlers;
    }

    public PlayerBuyTitleEvent(Player player, String title, double money) {
        this.player = player;
        this.title = title;
        this.money = money;
    }

    public String getTitle() {
        return this.title;
    }


    public double getMoney() {
        return money;
    }

}
