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
/*    */ 
/*    */ public class ContributionRankingWindow
/*    */   extends SimpleWindow
/*    */ {
/*    */   public ContributionRankingWindow() {
/* 16 */     super((String)SocietyPlugin.getInstance().getLangConfig().get("公会经济排行榜"), "");
/* 17 */     SocietyPlugin societyPlugin = SocietyPlugin.getInstance();
/* 18 */     Collections.sort(SocietyPlugin.getInstance().getSocieties(), new Comparator<Society>()
/*    */         {
/*    */           public int compare(Society o1, Society o2) {
/* 21 */             return (o1.getSocietyMoney().doubleValue() < o2.getSocietyMoney().doubleValue()) ? 1 : ((o1.getSocietyMoney().doubleValue() > o2.getSocietyMoney().doubleValue()) ? -1 : 0);
/*    */           }
/*    */         });
/* 24 */     StringBuilder sb = new StringBuilder();
/* 25 */     sb.append("§l§d公会经济排名§f(§c前五§f)\n");
/* 26 */     for (int i = 0; i < societyPlugin.getSocieties().size() && i <= 4; i++) {
/* 27 */       Society society = societyPlugin.getSocieties().get(i);
/* 28 */       long sid = society.getSid();
/* 29 */       String societyName1 = society.getSocietyName();
/* 30 */       Double societyMoney = society.getSocietyMoney();
/* 31 */       sb.append("§f§l公会ID §c" + sid + " §f公会名称 §a" + societyName1 + " §f公会经济 §b" + societyMoney + "\n");
/*    */     } 
/* 33 */     setContent(sb.toString());
/*    */   }
/*    */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\window\society\ContributionRankingWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */