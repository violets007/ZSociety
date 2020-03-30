/*    */ package com.zixuan007.society.window.society;
/*    */ 
/*    */ import cn.nukkit.Player;
/*    */ import cn.nukkit.event.Event;
/*    */ import cn.nukkit.form.element.ElementButton;
/*    */ import cn.nukkit.form.window.FormWindow;
/*    */ import com.zixuan007.society.SocietyPlugin;
/*    */ import com.zixuan007.society.domain.Society;
/*    */ import com.zixuan007.society.event.society.PlayerApplyJoinSocietyEvent;
/*    */ import com.zixuan007.society.utils.SocietyUtils;
/*    */ import com.zixuan007.society.window.ModalWindow;
/*    */ import com.zixuan007.society.window.SimpleWindow;
/*    */ import com.zixuan007.society.window.WindowManager;
/*    */ import java.util.List;
/*    */ 
/*    */ public class SocietyListWindow
/*    */   extends SimpleWindow
/*    */ {
/*    */   private List<Society> societyList;
/*    */   private int cuurentPage;
/* 21 */   private int limit = 10;
/*    */   private int totalPage;
/*    */   
/*    */   public SocietyListWindow(String title, String content, int cuurentPage, int totalPage, List<Society> societyList) {
/* 25 */     super(title, content);
/* 26 */     this.cuurentPage = cuurentPage;
/* 27 */     this.totalPage = totalPage;
/* 28 */     if (cuurentPage != 1) addButton(new ElementButton("上一页")); 
/* 29 */     this.societyList = societyList;
/* 30 */     for (Society society : societyList) {
/* 31 */       addButton(new ElementButton("§e公会ID §b" + society.getSid() + " §e公会名称 §b" + society.getSocietyName() + " §e会长 §b" + society.getPresidentName()));
/*    */     }
/* 33 */     if (cuurentPage < totalPage) addButton(new ElementButton("下一页"));
/*    */   
/*    */   }
/*    */   
/*    */   public void nextPage(int cuurentPage, Player player) {
/* 38 */     SocietyListWindow societyListWindow = WindowManager.getSocietyListWindow(++cuurentPage, getParent());
/* 39 */     player.showFormWindow((FormWindow)societyListWindow);
/*    */   }
/*    */   
/*    */   public void upPage(int cuurentPage, Player player) {
/* 43 */     SocietyListWindow societyListWindow = WindowManager.getSocietyListWindow(--cuurentPage, getParent());
/* 44 */     player.showFormWindow((FormWindow)societyListWindow);
/*    */   }
/*    */   
/*    */   public void onClick(int id, Player player) {
/*    */     Society society;
/* 49 */     if (id == 0 && this.cuurentPage != 1) {
/* 50 */       upPage(this.cuurentPage, player);
/*    */       return;
/*    */     } 
/* 53 */     if (id == 10) {
/* 54 */       nextPage(this.cuurentPage, player);
/*    */       return;
/*    */     } 
/* 57 */     if (SocietyUtils.isJoinSociety(player.getName())) {
/* 58 */       MessageWindow messageWindow = WindowManager.getMessageWindow("§c您当前已经加入过公会,请勿二次点击加入", getParent(), "返回上级");
/* 59 */       player.showFormWindow((FormWindow)messageWindow);
/*    */       return;
/*    */     } 
/* 62 */     ModalWindow affirmWindow = null;
/*    */     
/* 64 */     if (this.cuurentPage == 1) {
/* 65 */       society = this.societyList.get(id);
/* 66 */       affirmWindow = WindowManager.getAffrimWindow("§e您确定要加入 §b" + ((Society)this.societyList.get(id)).getSocietyName() + " §e公会吗?", "§a确认加入", "§c取消加入");
/*    */     } else {
/* 68 */       society = this.societyList.get(id - 1);
/* 69 */       affirmWindow = WindowManager.getAffrimWindow("§e您确定要加入 §b" + ((Society)this.societyList.get(id - 1)).getSocietyName() + " §e公会吗?", "§a确认加入", "§c取消加入");
/*    */     } 
/* 71 */     affirmWindow.setButtonClickedListener((affirm, player1) -> {
/*    */           if (affirm.booleanValue()) {
/*    */             SocietyPlugin.getInstance().getServer().getPluginManager().callEvent((Event)new PlayerApplyJoinSocietyEvent(player1, society));
/*    */           } else {
/*    */             player1.showFormWindow(getParent());
/*    */           } 
/*    */         });
/*    */     
/* 79 */     player.showFormWindow((FormWindow)affirmWindow);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLimit() {
/* 84 */     return this.limit;
/*    */   }
/*    */   
/*    */   public void setLimit(int limit) {
/* 88 */     this.limit = limit;
/*    */   }
/*    */   
/*    */   public int getTotalPage() {
/* 92 */     return this.totalPage;
/*    */   }
/*    */   
/*    */   public void setTotalPage(int totalPage) {
/* 96 */     this.totalPage = totalPage;
/*    */   }
/*    */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\window\society\SocietyListWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */