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
/*    */ import com.zixuan007.society.window.society.PlayerApplyListWindow;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ public class PresidentWindow
/*    */   extends SimpleWindow
/*    */ {
/*    */   private long sid;
/*    */   
/*    */   public PresidentWindow(long sid) {
/* 23 */     super("", "");
/* 24 */     this.sid = sid;
/* 25 */     addButton(new ElementButton("设置成员职位"));
/* 26 */     addButton(new ElementButton("查看玩家申请"));
/* 27 */     addButton(new ElementButton("升级公会"));
/* 28 */     addButton(new ElementButton("移除人员"));
/* 29 */     addButton(new ElementButton("解散"));
/* 30 */     Society society = SocietyUtils.getSocietysByID(sid);
/* 31 */     setTitle(((String)SocietyPlugin.getInstance().getLangConfig().get("会长公会管理窗口标题")).replaceAll("\\$\\{societyName\\}", society.getSocietyName())); } public void onClick(int id, Player player) { SetJobWindow setJobWindow; ArrayList<String> tempApply; PlayerApplyListWindow playerApplyListWindow; ArrayList<Object> list;
/*    */     Double societyMoney;
/*    */     int updateMoney;
/*    */     List<String> memberList;
/*    */     RemoveMemberWindow removeMemberWindow;
/* 36 */     int clickedButtonId = getResponse().getClickedButtonId();
/* 37 */     SocietyPlugin societyPlugin = SocietyPlugin.getInstance();
/* 38 */     Society society = SocietyUtils.getSocietysByID(this.sid);
/* 39 */     switch (clickedButtonId) {
/*    */       
/*    */       case 0:
/* 42 */         setJobWindow = WindowManager.getSetJobWindow((FormWindow)this);
/* 43 */         player.showFormWindow((FormWindow)setJobWindow);
/*    */         break;
/*    */       case 1:
/* 46 */         tempApply = society.getTempApply();
/* 47 */         if (tempApply == null || tempApply.size() <= 0) {
/* 48 */           MessageWindow messageWindow = WindowManager.getMessageWindow("§c当前还没有玩家申请加入公会", (FormWindow)this, "返回上级");
/* 49 */           player.showFormWindow((FormWindow)messageWindow);
/*    */           return;
/*    */         } 
/* 52 */         playerApplyListWindow = WindowManager.getPlayerApplyListWindow(tempApply, society.getSid());
/* 53 */         player.showFormWindow((FormWindow)playerApplyListWindow);
/*    */         break;
/*    */       case 2:
/* 56 */         list = (ArrayList<Object>)societyPlugin.getConfig().get("等级" + society.getGrade());
/* 57 */         if (list == null || list.size() <= 0) {
/* 58 */           MessageWindow messageWindow = WindowManager.getMessageWindow("§c当前公会已经是最顶级", (FormWindow)this, "返回上级");
/* 59 */           player.showFormWindow((FormWindow)messageWindow);
/*    */           return;
/*    */         } 
/* 62 */         societyMoney = society.getSocietyMoney();
/* 63 */         updateMoney = ((Integer)list.get(1)).intValue();
/* 64 */         if (societyMoney.doubleValue() < updateMoney) {
/* 65 */           MessageWindow messageWindow = WindowManager.getMessageWindow("§c公会升级需要 §b" + updateMoney + "§c,公会当前资金 §b" + societyMoney, (FormWindow)this, "返回上级");
/* 66 */           player.showFormWindow((FormWindow)messageWindow);
/*    */           
/*    */           return;
/*    */         } 
/* 70 */         society.setSocietyMoney(Double.valueOf(societyMoney.doubleValue() - updateMoney));
/* 71 */         society.setGrade(society.getGrade() + 1);
/* 72 */         society.saveData();
/* 73 */         player.showFormWindow((FormWindow)WindowManager.getMessageWindow("§a公会升级成功", (FormWindow)this, "返回上级"));
/*    */         break;
/*    */       
/*    */       case 3:
/* 77 */         if (society.getPost().size() == 1) {
/* 78 */           MessageWindow messageWindow = WindowManager.getMessageWindow("§c当前没有可以移除的人员", (FormWindow)this, "返回上级");
/* 79 */           player.showFormWindow((FormWindow)messageWindow);
/*    */           return;
/*    */         } 
/* 82 */         memberList = Arrays.asList((String[])society.getPost().keySet().toArray((Object[])new String[society.getPost().keySet().size()]));
/* 83 */         removeMemberWindow = WindowManager.getRemoveMemberWindow(society.getSid(), memberList);
/* 84 */         removeMemberWindow.setParent((FormWindow)this);
/* 85 */         player.showFormWindow((FormWindow)removeMemberWindow);
/*    */         break;
/*    */       case 4:
/* 88 */         player.showFormWindow((FormWindow)WindowManager.getMessageWindow("§a成功解散 §b" + society.getSocietyName(), (FormWindow)WindowManager.getSocietyWindow(player), "返回主界面"));
/* 89 */         societyPlugin.getSocieties().remove(society);
/* 90 */         SocietyUtils.removeSociety(society.getSocietyName());
/*    */         break;
/*    */     }  }
/*    */ 
/*    */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\window\society\president\PresidentWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */