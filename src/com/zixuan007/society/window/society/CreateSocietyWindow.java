/*    */ package com.zixuan007.society.window.society;
/*    */ import cn.nukkit.Player;
/*    */ import cn.nukkit.event.Event;
/*    */ import cn.nukkit.form.element.Element;
/*    */ import cn.nukkit.form.element.ElementInput;
/*    */ import cn.nukkit.form.response.FormResponseCustom;
/*    */ import cn.nukkit.form.window.FormWindow;
/*    */ import com.zixuan007.society.SocietyPlugin;
/*    */ import com.zixuan007.society.domain.Society;
/*    */ import com.zixuan007.society.event.society.PlayerCreateSocietyEvent;
/*    */ import com.zixuan007.society.utils.SocietyUtils;
/*    */ import com.zixuan007.society.window.CustomWindow;
/*    */ import com.zixuan007.society.window.WindowManager;
/*    */ import java.util.HashMap;
/*    */ import me.onebone.economyapi.EconomyAPI;
/*    */ 
/*    */ public class CreateSocietyWindow extends CustomWindow {
/*    */   public CreateSocietyWindow() {
/* 19 */     super("创建公会窗口");
/* 20 */     addElement((Element)new ElementInput("", "公会名称"));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(FormResponseCustom response, Player player) {
/* 25 */     SocietyPlugin societyPlugin = SocietyPlugin.getInstance();
/* 26 */     String societyName = response.getInputResponse(0);
/* 27 */     Boolean societyNameExist = SocietyUtils.isSocietyNameExist(societyName);
/* 28 */     MessageWindow messageWindow = null;
/* 29 */     if (societyNameExist.booleanValue()) {
/* 30 */       messageWindow = WindowManager.getMessageWindow("§c此公会名称已经存在", (FormWindow)this, "返回上级");
/* 31 */       player.showFormWindow((FormWindow)messageWindow);
/*    */       return;
/*    */     } 
/* 34 */     Double createSocietyMoney = (Double)societyPlugin.getConfig().get("createSocietyMoney");
/* 35 */     double myMoney = EconomyAPI.getInstance().myMoney(player);
/* 36 */     if (myMoney < createSocietyMoney.doubleValue()) {
/* 37 */       messageWindow = WindowManager.getMessageWindow("§c当前余额不足,创建公会需要: §l§a" + createSocietyMoney, (FormWindow)this, "返回上级");
/* 38 */       player.showFormWindow((FormWindow)messageWindow);
/*    */       return;
/*    */     } 
/* 41 */     if (societyName == null || societyName.equals("")) {
/* 42 */       messageWindow = WindowManager.getMessageWindow("§c公会名称不能为空", (FormWindow)this, "返回上级");
/* 43 */       player.showFormWindow((FormWindow)messageWindow);
/*    */       return;
/*    */     } 
/* 46 */     EconomyAPI.getInstance().reduceMoney(player, createSocietyMoney.doubleValue());
/* 47 */     long count = SocietyUtils.getNextSid();
/* 48 */     Society society = new Society(count, societyName, player.getName(), SocietyUtils.getFormatDateTime(), Double.valueOf(0.0D), new HashMap<>());
/* 49 */     societyPlugin.getServer().getPluginManager().callEvent((Event)new PlayerCreateSocietyEvent(player, society));
/*    */   }
/*    */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\window\society\CreateSocietyWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */