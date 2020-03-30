package com.zixuan007.society.event.title;

import cn.nukkit.Player;
import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;

public class CreateTitleShopEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private BlockEntitySign wallSign;

    public static HandlerList getHandlers() {
        return handlers;
    }

    public CreateTitleShopEvent(Player player, BlockEntitySign wallSign) {
        this.player = player;
        this.wallSign = wallSign;
    }

    public BlockEntitySign getWallSign() {
        return this.wallSign;
    }

    public void setWallSign(BlockEntitySign wallSign) {
        this.wallSign = wallSign;
    }
}