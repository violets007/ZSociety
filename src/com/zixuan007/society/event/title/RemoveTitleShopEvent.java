/*    */ package com.zixuan007.society.event.title;
/*    */ 
/*    */ import cn.nukkit.Player;
/*    */ import cn.nukkit.block.Block;
/*    */ import cn.nukkit.event.HandlerList;
/*    */ import cn.nukkit.event.player.PlayerEvent;
/*    */ 
/*    */ public class RemoveTitleShopEvent extends PlayerEvent {
/*  9 */   private static final HandlerList handlers = new HandlerList();
/*    */   private Block block;
/*    */   
/*    */   public static HandlerList getHandlers() {
/* 13 */     return handlers;
/*    */   }
/*    */   private String title;
/*    */   public RemoveTitleShopEvent(Player player, Block block, String title) {
/* 17 */     this.player = player;
/* 18 */     this.block = block;
/* 19 */     this.title = title;
/*    */   }
/*    */   
/*    */   public Block getBlock() {
/* 23 */     return this.block;
/*    */   }
/*    */   
/*    */   public void setBlock(Block block) {
/* 27 */     this.block = block;
/*    */   }
/*    */   
/*    */   public String getTitle() {
/* 31 */     return this.title;
/*    */   }
/*    */   
/*    */   public void setTitle(String title) {
/* 35 */     this.title = title;
/*    */   }
/*    */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\event\title\RemoveTitleShopEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */