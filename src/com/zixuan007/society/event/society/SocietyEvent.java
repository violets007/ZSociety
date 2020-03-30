/*    */ package com.zixuan007.society.event.society;
/*    */ 
/*    */ import cn.nukkit.Player;
/*    */ import cn.nukkit.event.HandlerList;
/*    */ import cn.nukkit.event.player.PlayerEvent;
/*    */ import com.zixuan007.society.domain.Society;
/*    */ 
/*    */ public class SocietyEvent extends PlayerEvent {
/*  9 */   private static final HandlerList handlers = new HandlerList();
/*    */   private Society society;
/*    */   
/*    */   public static HandlerList getHandlers() {
/* 13 */     return handlers;
/*    */   }
/*    */   
/*    */   public SocietyEvent(Player player, Society society) {
/* 17 */     this.player = player;
/* 18 */     this.society = society;
/*    */   }
/*    */ 
/*    */   
/*    */   public Society getSociety() {
/* 23 */     return this.society;
/*    */   }
/*    */   
/*    */   public void setSociety(Society society) {
/* 27 */     this.society = society;
/*    */   }
/*    */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\event\society\SocietyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */