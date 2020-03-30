/*    */ package com.zixuan007.society.window.society;
/*    */ 
/*    */ import cn.nukkit.Player;
/*    */ import cn.nukkit.form.element.ElementButton;
/*    */ import cn.nukkit.form.window.FormWindow;
/*    */ import com.zixuan007.society.SocietyPlugin;
/*    */ import com.zixuan007.society.utils.SocietyUtils;
/*    */ import com.zixuan007.society.window.SimpleWindow;
/*    */ import com.zixuan007.society.window.WindowManager;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class PlayerApplyListWindow
/*    */   extends SimpleWindow
/*    */ {
/*    */   private List<String> tempApply;
/*    */   private long sid;
/*    */   
/*    */   public PlayerApplyListWindow(List<String> tempApply, long sid) {
/* 20 */     super((String)SocietyPlugin.getInstance().getLangConfig().get("玩家申请加入公会标题"), "§e申请加入公会人员");
/* 21 */     this.tempApply = tempApply;
/* 22 */     this.sid = sid;
/* 23 */     tempApply.forEach(name -> addButton(new ElementButton(name)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onClick(int id, Player player) {
/* 30 */     String playerName = this.tempApply.get(id);
/* 31 */     if (SocietyUtils.isJoinSociety(playerName)) {
/* 32 */       MessageWindow messageWindow1 = WindowManager.getMessageWindow("§c玩家 " + playerName + " 已经加入公会", getParent(), "返回管理界面");
/*    */       return;
/*    */     } 
/* 35 */     SocietyUtils.addMember(playerName, SocietyUtils.getSocietysByID(this.sid));
/* 36 */     MessageWindow messageWindow = WindowManager.getMessageWindow("§a成功同意 §b" + playerName + " §a加入公会", getParent(), "返回管理界面");
/* 37 */     player.showFormWindow((FormWindow)messageWindow);
/*    */   }
/*    */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\window\society\PlayerApplyListWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */