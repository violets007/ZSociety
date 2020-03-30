/*    */ package com.zixuan007.society.window.society.president;
/*    */ 
/*    */ import cn.nukkit.Player;
/*    */ import cn.nukkit.form.element.ElementButton;
/*    */ import cn.nukkit.form.window.FormWindow;
/*    */ import com.zixuan007.society.SocietyPlugin;
/*    */ import com.zixuan007.society.domain.Society;
/*    */ import com.zixuan007.society.utils.SocietyUtils;
/*    */ import com.zixuan007.society.window.SimpleWindow;
/*    */ import com.zixuan007.society.window.WindowManager;
/*    */ import com.zixuan007.society.window.society.MessageWindow;
/*    */ import java.util.List;
/*    */ 
/*    */ public class RemoveMemberWindow
/*    */   extends SimpleWindow {
/*    */   public RemoveMemberWindow(long sid, List<String> memberList) {
/* 17 */     super((String)SocietyPlugin.getInstance().getLangConfig().get("移除人员窗口标题"), "");
/* 18 */     this.sid = sid;
/* 19 */     Society society = SocietyUtils.getSocietysByID(sid);
/* 20 */     for (String name : memberList) {
/* 21 */       if (name.equals(society.getPresidentName()))
/*    */         continue; 
/* 23 */       addButton(new ElementButton(name));
/*    */     } 
/*    */   }
/*    */   private long sid;
/*    */   
/*    */   public void onClick(int id, Player player) {
/* 29 */     String playerName = getResponse().getClickedButton().getText();
/* 30 */     Society society = SocietyUtils.getSocietysByID(this.sid);
/* 31 */     society.getPost().remove(playerName);
/* 32 */     society.saveData();
/* 33 */     MessageWindow messageWindow = WindowManager.getMessageWindow("§a成功移除成员 §b" + playerName, getParent(), "返回上级");
/* 34 */     player.showFormWindow((FormWindow)messageWindow);
/*    */   }
/*    */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\window\society\president\RemoveMemberWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */