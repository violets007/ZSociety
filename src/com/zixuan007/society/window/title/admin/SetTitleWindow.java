/*    */ package com.zixuan007.society.window.title.admin;
/*    */ import cn.nukkit.Player;
/*    */ import cn.nukkit.form.element.Element;
/*    */ import cn.nukkit.form.element.ElementInput;
/*    */ import cn.nukkit.form.response.FormResponseCustom;
/*    */ import cn.nukkit.form.window.FormWindow;
/*    */ import cn.nukkit.utils.Config;
/*    */ import com.zixuan007.society.SocietyPlugin;
/*    */ import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowManager;
/*    */ 
/*    */ public class SetTitleWindow extends CustomWindow {
/*    */   public SetTitleWindow() {
/* 13 */     super((String)SocietyPlugin.getInstance().getLangConfig().get("设置称号窗口标题"));
/* 14 */     addElement((Element)new ElementInput("", "玩家名字"));
/* 15 */     addElement((Element)new ElementInput("", "称号"));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onClick(FormResponseCustom response, Player player) {
/* 21 */     String playerName = response.getInputResponse(0);
/* 22 */     String title = response.getInputResponse(1);
/* 23 */     SocietyPlugin societyPlugin = SocietyPlugin.getInstance();
/* 24 */     if (societyPlugin.getTitleConfig().get(playerName) == null) {
/* 25 */       player.showFormWindow((FormWindow)WindowManager.getMessageWindow("§c输入的玩家名字不存在", (FormWindow)this, "返回上级"));
/*    */       return;
/*    */     } 
/* 28 */     if (title.equals("") || title.equals(" ")) {
/* 29 */       player.showFormWindow((FormWindow)WindowManager.getMessageWindow("§c称号内容不能为空", (FormWindow)this, "返回上级"));
/*    */       return;
/*    */     } 
/* 32 */     Config titleConfig = societyPlugin.getTitleConfig();
/* 33 */     titleConfig.set(playerName, title);
/* 34 */     titleConfig.save();
/* 35 */     player.showFormWindow((FormWindow)WindowManager.getMessageWindow("§a成功设置 §b" + playerName + " §a的称号为 §e" + title, (FormWindow)this, "返回上级"));
/*    */   }
/*    */ }