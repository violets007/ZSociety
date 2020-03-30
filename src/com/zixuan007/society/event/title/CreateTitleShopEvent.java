/*    */ package com.zixuan007.society.event.title;
/*    */ 
/*    */ import cn.nukkit.Player;
/*    */ import cn.nukkit.blockentity.BlockEntitySign;
/*    */ import cn.nukkit.event.HandlerList;
/*    */ import cn.nukkit.event.player.PlayerEvent;
/*    */ 
/*    */ 
/*    */ public class CreateTitleShopEvent
/*    */   extends PlayerEvent
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   public static HandlerList getHandlers() {
/* 15 */     return handlers;
/*    */   }
/*    */   private BlockEntitySign wallSign;
/*    */   public CreateTitleShopEvent(Player player, BlockEntitySign wallSign) {
/* 19 */     this.player = player;
/* 20 */     this.wallSign = wallSign;
/*    */   }
/*    */   
/*    */   public BlockEntitySign getWallSign() {
/* 24 */     return this.wallSign;
/*    */   }
/*    */   
/*    */   public void setWallSign(BlockEntitySign wallSign) {
/* 28 */     this.wallSign = wallSign;
/*    */   }
/*    */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\event\title\CreateTitleShopEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */