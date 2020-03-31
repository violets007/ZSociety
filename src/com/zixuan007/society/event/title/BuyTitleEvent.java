package com.zixuan007.society.event.title;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;

public class BuyTitleEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private String title;
    private double money;

    public static HandlerList getHandlers() {
        return handlers;
    }

    public BuyTitleEvent(Player player, String title, double money) {
        this.player = player;
        this.title = title;
        this.money = money;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}