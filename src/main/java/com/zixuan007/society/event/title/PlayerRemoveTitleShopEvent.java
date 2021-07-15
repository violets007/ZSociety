package com.zixuan007.society.event.title;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;

/**
 * @author zixuan007
 */
public class PlayerRemoveTitleShopEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private Block block;
    private String title;

    public static HandlerList getHandlers() {
        return handlers;
    }

    public PlayerRemoveTitleShopEvent(Player player, Block block, String title) {
        this.player = player;
        this.block = block;
        this.title = title;
    }

    public Block getBlock() {
        return this.block;
    }

    public String getTitle() {
        return this.title;
    }

}
