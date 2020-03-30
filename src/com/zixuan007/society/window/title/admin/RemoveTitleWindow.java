/*    */ package com.zixuan007.society.window.title.admin;
/*    */ 
/*    */ import cn.nukkit.Player;
/*    */ import cn.nukkit.form.element.Element;
/*    */ import cn.nukkit.form.element.ElementInput;
/*    */ import cn.nukkit.form.response.FormResponseCustom;
/*    */ import cn.nukkit.form.window.FormWindow;
/*    */ import cn.nukkit.utils.Config;
/*    */ import com.zixuan007.society.SocietyPlugin;
/*    */ import com.zixuan007.society.window.CustomWindow;
/*    */ import com.zixuan007.society.window.WindowManager;
/*    */ 
/*    */ public class RemoveTitleWindow
/*    */   extends CustomWindow {
/*    */   public RemoveTitleWindow() {
/* 16 */     super((String)SocietyPlugin.getInstance().getLangConfig().get("移除玩家称号窗口标题"));
/* 17 */     addElement((Element)new ElementInput("", "玩家名字"));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(FormResponseCustom response, Player player) {
/* 22 */     String playerName = response.getInputResponse(0);
/* 23 */     Config titleConfig = SocietyPlugin.getInstance().getTitleConfig();
/* 24 */     String title = (String)titleConfig.get(playerName);
/* 25 */     if (title == null) {
/* 26 */       player.showFormWindow((FormWindow)WindowManager.getMessageWindow("§c输入的玩家名字不存在", (FormWindow)this, "返回上级"));
/*    */     } else {
/* 28 */       titleConfig.set(playerName, "无称号");
/* 29 */       titleConfig.save();
/* 30 */       player.showFormWindow((FormWindow)WindowManager.getMessageWindow("§a移除玩家 §b" + playerName + " §a称号成功", (FormWindow)this, "返回上级"));
/*    */     } 
/*    */   }
/*    */ }


