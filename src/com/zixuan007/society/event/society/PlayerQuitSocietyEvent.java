/*    */ package com.zixuan007.society.event.society;
/*    */ 
/*    */ import cn.nukkit.Player;
/*    */ import cn.nukkit.event.HandlerList;
/*    */ import com.zixuan007.society.domain.Society;
/*    */ 
/*    */ public class PlayerQuitSocietyEvent extends SocietyEvent {
/*  8 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   public static HandlerList getHandlers() {
/* 11 */     return handlers;
/*    */   }
/*    */   
/*    */   public PlayerQuitSocietyEvent(Player player, Society society) {
/* 15 */     super(player, society);
/*    */   }
/*    */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\event\society\PlayerQuitSocietyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */