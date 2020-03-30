/*    */ package com.zixuan007.society.listener;
/*    */ 
/*    */ import cn.nukkit.Player;
/*    */ import cn.nukkit.event.EventHandler;
/*    */ import cn.nukkit.event.EventPriority;
/*    */ import cn.nukkit.event.Listener;
/*    */ import cn.nukkit.event.player.PlayerFormRespondedEvent;
/*    */ import cn.nukkit.event.player.PlayerSettingsRespondedEvent;
/*    */ import cn.nukkit.form.response.FormResponse;
/*    */ import cn.nukkit.form.window.FormWindow;
/*    */ import com.zixuan007.society.window.CustomWindow;
/*    */ import com.zixuan007.society.window.ModalWindow;
/*    */ import com.zixuan007.society.window.SimpleWindow;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResponseLister
/*    */   implements Listener
/*    */ {
/*    */   @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
/*    */   public void onResponded(PlayerFormRespondedEvent event) {
/* 23 */     handleResponse(event.getWindow(), event.getResponse(), event.getPlayer());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
/*    */   public void onSettingsResponded(PlayerSettingsRespondedEvent event) {
/* 32 */     handleResponse(event.getWindow(), event.getResponse(), event.getPlayer());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleResponse(FormWindow formWindow, FormResponse response, Player player) {
/* 43 */     if (ModalWindow.onEvent(formWindow, response, player)) {
/*    */       return;
/*    */     }
/* 46 */     if (CustomWindow.onEvent(formWindow, response, player)) {
/*    */       return;
/*    */     }
/* 49 */     if (SimpleWindow.onEvent(formWindow, response, player))
/*    */       return; 
/*    */   }
/*    */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\listener\ResponseLister.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */