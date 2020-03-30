/*    */ package com.zixuan007.society.window.society.president;
/*    */ 
/*    */ import cn.nukkit.Player;
/*    */ import cn.nukkit.form.element.Element;
/*    */ import cn.nukkit.form.element.ElementDropdown;
/*    */ import cn.nukkit.form.element.ElementInput;
/*    */ import cn.nukkit.form.response.FormResponseCustom;
/*    */ import cn.nukkit.form.response.FormResponseData;
/*    */ import cn.nukkit.form.window.FormWindow;
/*    */ import com.zixuan007.society.SocietyPlugin;
/*    */ import com.zixuan007.society.domain.Society;
/*    */ import com.zixuan007.society.utils.SocietyUtils;
/*    */ import com.zixuan007.society.window.CustomWindow;
/*    */ import com.zixuan007.society.window.WindowManager;
/*    */ import com.zixuan007.society.window.society.MessageWindow;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ public class SetJobWindow
/*    */   extends CustomWindow
/*    */ {
/*    */   public SetJobWindow() {
/* 23 */     super((String)SocietyPlugin.getInstance().getLangConfig().get("设置玩家职位窗口标题"));
/* 24 */     addElement((Element)new ElementInput("", "§e玩家名称"));
/* 25 */     ElementDropdown elementDropdown = new ElementDropdown("§e职位列表", SocietyUtils.getAllPost());
/* 26 */     addElement((Element)elementDropdown);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(FormResponseCustom response, Player player) {
/* 31 */     String playerName = response.getInputResponse(0);
/* 32 */     FormResponseData dropdownResponse = response.getDropdownResponse(1);
/* 33 */     final String jobName = dropdownResponse.getElementContent();
/* 34 */     final int jobGrade = SocietyUtils.getPostGradeByName(jobName);
/* 35 */     if (player.getName().equals(playerName)) {
/* 36 */       MessageWindow messageWindow1 = WindowManager.getMessageWindow("§c设置职位的玩家不能是自己", (FormWindow)this, "返回上级");
/* 37 */       player.showFormWindow((FormWindow)messageWindow1);
/*    */       return;
/*    */     } 
/* 40 */     if (!SocietyUtils.isJoinSociety(playerName)) {
/* 41 */       MessageWindow messageWindow1 = WindowManager.getMessageWindow("§c设置的成员还没有加入公会", (FormWindow)this, "返回上级");
/* 42 */       player.showFormWindow((FormWindow)messageWindow1);
/*    */       return;
/*    */     } 
/* 45 */     Society society = SocietyUtils.getSocietyByPlayerName(playerName);
/* 46 */     if (!society.getPost().keySet().contains(playerName)) {
/* 47 */       MessageWindow messageWindow1 = WindowManager.getMessageWindow("§c此玩家不是本公会成员,无法设置职位", (FormWindow)this, "返回上级");
/* 48 */       player.showFormWindow((FormWindow)messageWindow1);
/*    */       return;
/*    */     } 
/* 51 */     society.getPost().put(playerName, new ArrayList()
/*    */         {
/*    */         
/*    */         });
/*    */ 
/*    */     
/* 57 */     society.saveData();
/* 58 */     MessageWindow messageWindow = WindowManager.getMessageWindow("§a成功设置 §b" + playerName + " §a职位为 §e" + jobName, (FormWindow)this, "返回上级");
/* 59 */     player.showFormWindow((FormWindow)messageWindow);
/*    */   }
/*    */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\window\society\president\SetJobWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */