/*    */ package com.zixuan007.society.window.title.admin;
/*    */ import cn.nukkit.Player;
/*    */ import cn.nukkit.form.element.Element;
/*    */ import cn.nukkit.form.element.ElementInput;
/*    */ import cn.nukkit.form.response.FormResponseCustom;
/*    */ import cn.nukkit.form.window.FormWindow;
/*    */ import com.zixuan007.society.SocietyPlugin;
/*    */ import com.zixuan007.society.utils.SocietyUtils;
/*    */ import com.zixuan007.society.utils.TitleUtils;
/*    */ import com.zixuan007.society.window.CustomWindow;
/*    */ import com.zixuan007.society.window.WindowManager;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class CreateShopWindow extends CustomWindow {
/*    */   public CreateShopWindow() {
/* 16 */     super((String)SocietyPlugin.getInstance().getLangConfig().get("创建称号商店窗口标题"));
/* 17 */     addElement((Element)new ElementInput("", "需要售卖的称号"));
/* 18 */     addElement((Element)new ElementInput("", "金额"));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(FormResponseCustom response, Player player) {
/* 23 */     final String title = response.getInputResponse(0);
/* 24 */     final String money = response.getInputResponse(1);
/* 25 */     if (title.equals("") || title.equals(" ")) {
/* 26 */       player.showFormWindow((FormWindow)WindowManager.getMessageWindow("§c售卖的称号不能为空", (FormWindow)this, "返回上级"));
/*    */       return;
/*    */     } 
/* 29 */     if (money.equals("") || !SocietyUtils.isNumeric(money)) {
/* 30 */       player.showFormWindow((FormWindow)WindowManager.getMessageWindow("§c输入的金额不是数字", (FormWindow)this, "返回上级"));
/*    */       return;
/*    */     } 
/* 33 */     if (SocietyPlugin.getInstance().getTitleShopConfig().get(title) != null) {
/* 34 */       player.showFormWindow((FormWindow)WindowManager.getMessageWindow("§c此称号已经存在", (FormWindow)this, "返回上级"));
/*    */       return;
/*    */     } 
/* 37 */     TitleUtils.onCreateName.put(player.getName(), new HashMap<String, Object>()
/*    */         {
/*    */         
/*    */         });
/*    */ 
/*    */     
/* 43 */     player.sendMessage("§e请点击贴在墙上的木牌");
/*    */   }
/*    */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\window\title\admin\CreateShopWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */