/*    */ package com.zixuan007.society.window.society;
/*    */ 
/*    */ import com.zixuan007.society.SocietyPlugin;
/*    */ import com.zixuan007.society.domain.Society;
/*    */ import com.zixuan007.society.window.SimpleWindow;
/*    */ import java.util.Collections;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LevelRankWindow
/*    */   extends SimpleWindow
/*    */ {
/*    */   public LevelRankWindow() {
/* 15 */     super((String)SocietyPlugin.getInstance().getLangConfig().get("公会等级排行榜"), "");
/* 16 */     StringBuilder sb = new StringBuilder();
/* 17 */     SocietyPlugin societyPlugin = SocietyPlugin.getInstance();
/* 18 */     Collections.sort(societyPlugin.getSocieties(), new Comparator<Society>()
/*    */         {
/*    */           public int compare(Society o1, Society o2) {
/* 21 */             return (o1.getGrade() < o2.getGrade()) ? 1 : ((o1.getGrade() > o2.getGrade()) ? -1 : 0);
/*    */           }
/*    */         });
/* 24 */     sb.append("§l§d公会等级排名§f(§c前五§f)\n");
/* 25 */     for (int i = 0; i < societyPlugin.getSocieties().size() && i <= 4; i++) {
/* 26 */       Society society = societyPlugin.getSocieties().get(i);
/* 27 */       long sid = society.getSid();
/* 28 */       String societyName1 = society.getSocietyName();
/* 29 */       int grade = society.getGrade();
/* 30 */       sb.append("§f公会名称 §a" + societyName1 + " §f公会等级 §b" + grade + "\n");
/*    */     } 
/* 32 */     setContent(sb.toString());
/*    */   }
/*    */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\window\society\LevelRankWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */