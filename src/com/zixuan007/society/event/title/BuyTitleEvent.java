/*    */ package com.zixuan007.society.event.title;
/*    */ 
/*    */ import cn.nukkit.Player;
/*    */ import cn.nukkit.event.HandlerList;
/*    */ import cn.nukkit.event.player.PlayerEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuyTitleEvent
/*    */   extends PlayerEvent
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   private String title;
/*    */   
/*    */   public static HandlerList getHandlers() {
/* 16 */     return handlers;
/*    */   }
/*    */   private double money;
/*    */   public BuyTitleEvent(Player player, String title, double money) {
/* 20 */     this.player = player;
/* 21 */     this.title = title;
/* 22 */     this.money = money;
/*    */   }
/*    */   
/*    */   public String getTitle() {
/* 26 */     return this.title;
/*    */   }
/*    */   
/*    */   public void setTitle(String title) {
/* 30 */     this.title = title;
/*    */   }
/*    */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\event\title\BuyTitleEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */