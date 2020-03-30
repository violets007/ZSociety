/*    */ package com.zixuan007.society.window.title.admin;
/*    */ import cn.nukkit.Player;
/*    */ import cn.nukkit.form.element.ElementButton;
/*    */ import cn.nukkit.form.window.FormWindow;
/*    */ import com.zixuan007.society.SocietyPlugin;
/*    */ import com.zixuan007.society.window.SimpleWindow;
/*    */ import com.zixuan007.society.window.WindowManager;
/*    */ 
/*    */ public class TitleWindow extends SimpleWindow {
/*    */   public TitleWindow() {
/* 11 */     super((String)SocietyPlugin.getInstance().getLangConfig().get("称号管理窗口标题"), "");
/* 12 */     addButton(new ElementButton("设置玩家称号"));
/* 13 */     addButton(new ElementButton("移除玩家称号"));
/* 14 */     addButton(new ElementButton("创建称号商店"));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(int id, Player player) {
/* 19 */     switch (id) {
/*    */       case 0:
/* 21 */         player.showFormWindow((FormWindow)WindowManager.getSetTitleWindow((FormWindow)this));
/*    */         break;
/*    */       case 1:
/* 24 */         player.showFormWindow((FormWindow)WindowManager.getRemoveTitleWindow((FormWindow)this));
/*    */         break;
/*    */       case 2:
/* 27 */         player.showFormWindow((FormWindow)WindowManager.getCreateTitleShopWindow());
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\window\title\admin\TitleWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */