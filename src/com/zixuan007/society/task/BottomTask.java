/*    */ package com.zixuan007.society.task;
/*    */ 
/*    */ import cn.nukkit.Player;
/*    */ import cn.nukkit.scheduler.Task;
/*    */ import com.zixuan007.society.SocietyPlugin;
/*    */ import com.zixuan007.society.utils.SocietyUtils;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class BottomTask
/*    */   extends Task {
/* 11 */   private SocietyPlugin societyPlugin = null;
/*    */   
/*    */   public BottomTask(SocietyPlugin societyPlugin) {
/* 14 */     this.societyPlugin = societyPlugin;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRun(int i) {
/* 19 */     Boolean isShowTip = (Boolean)this.societyPlugin.getConfig().get("是否开启底部");
/* 20 */     if (isShowTip.booleanValue()) {
/* 21 */       Collection<Player> players = this.societyPlugin.getServer().getOnlinePlayers().values();
/* 22 */       String tipText = (String)SocietyPlugin.getInstance().getConfig().get("tipText");
/* 23 */       for (Player player : players) {
/* 24 */         tipText = SocietyUtils.formatButtomText(tipText, player);
/* 25 */         player.sendTip(tipText);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\task\BottomTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */