package com.zixuan007.society.event.title;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;

public class RemoveTitleShopEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private Block block;
    private String title;

    public static HandlerList getHandlers() {
        return handlers;
    }

    public RemoveTitleShopEvent(Player player, Block block, String title) {
        this.player = player;
        this.block = block;
        this.title = title;
    }

    public Block getBlock() {
        return this.block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}