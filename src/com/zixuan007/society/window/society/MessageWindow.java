/*    */ package com.zixuan007.society.window.society;
/*    */ 
/*    */ import cn.nukkit.Player;
/*    */ import com.zixuan007.society.window.SimpleWindow;
/*    */ 
/*    */ public class MessageWindow extends SimpleWindow {
/*    */   public MessageWindow(String title, String content) {
/*  8 */     super(title, content);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(int id, Player player) {
/* 13 */     if (getButtons().get(0) != null && getParent() != null)
/* 14 */       player.showFormWindow(getParent()); 
/*    */   }
/*    */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\window\society\MessageWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */