/*    */ package com.zixuan007.society.window.society;
/*    */ import cn.nukkit.Player;
/*    */ import cn.nukkit.form.element.Element;
/*    */ import cn.nukkit.form.element.ElementInput;
/*    */ import cn.nukkit.form.response.FormResponseCustom;
/*    */ import cn.nukkit.form.window.FormWindow;
/*    */ import com.zixuan007.society.SocietyPlugin;
/*    */ import com.zixuan007.society.domain.Society;
/*    */ import com.zixuan007.society.utils.SocietyUtils;
/*    */ import com.zixuan007.society.window.CustomWindow;
/*    */ import com.zixuan007.society.window.WindowManager;
/*    */ import me.onebone.economyapi.EconomyAPI;
/*    */ 
/*    */ public class ContributionWindow extends CustomWindow {
/*    */   public ContributionWindow(long sid) {
/* 16 */     super((String)SocietyPlugin.getInstance().getLangConfig().get("贡献窗口标题"));
/* 17 */     this.sid = sid;
/* 18 */     addElement((Element)new ElementInput("", "贡献金额"));
/*    */   }
/*    */   private long sid;
/*    */   
/*    */   public void onClick(FormResponseCustom response, Player player) {
/* 23 */     String strMoney = response.getInputResponse(0);
/* 24 */     MessageWindow messageWindow = null;
/* 25 */     if (strMoney.equals("") || !SocietyUtils.isNumeric(strMoney)) {
/* 26 */       messageWindow = WindowManager.getMessageWindow("§c输入的不是数字", (FormWindow)this, "返回上级");
/* 27 */       player.showFormWindow((FormWindow)messageWindow);
/*    */       return;
/*    */     } 
/* 30 */     if (!SocietyUtils.isJoinSociety(player.getName())) {
/* 31 */       messageWindow = WindowManager.getMessageWindow("§c您当前还没有加入公会,请先加入公会", (FormWindow)this, "返回上级");
/* 32 */       player.showFormWindow((FormWindow)messageWindow);
/*    */       return;
/*    */     } 
/* 35 */     int money = Integer.parseInt(strMoney);
/* 36 */     double myMoney = EconomyAPI.getInstance().myMoney(player);
/* 37 */     if (myMoney < money) {
/* 38 */       messageWindow = WindowManager.getMessageWindow("§c当前金币不足", (FormWindow)this, "返回上级");
/*    */     } else {
/* 40 */       Society society = SocietyUtils.getSocietysByID(this.sid);
/* 41 */       society.setSocietyMoney(Double.valueOf(society.getSocietyMoney().doubleValue() + money));
/* 42 */       messageWindow = WindowManager.getMessageWindow("§a贡献成功,当前公会经济 §b" + society.getSocietyMoney(), getParent(), "返回上级");
/*    */     } 
/* 44 */     player.showFormWindow((FormWindow)messageWindow);
/*    */   }
/*    */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\window\society\ContributionWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */