/*    */ package com.zixuan007.society.listener;
/*    */ 
/*    */ import cn.nukkit.Player;
/*    */ import cn.nukkit.event.EventHandler;
/*    */ import cn.nukkit.event.EventPriority;
/*    */ import cn.nukkit.event.Listener;
/*    */ import cn.nukkit.event.player.PlayerChatEvent;
/*    */ import cn.nukkit.form.window.FormWindow;
/*    */ import cn.nukkit.utils.Config;
/*    */ import com.zixuan007.society.SocietyPlugin;
/*    */ import com.zixuan007.society.domain.Society;
/*    */ import com.zixuan007.society.event.society.PlayerApplyJoinSocietyEvent;
/*    */ import com.zixuan007.society.event.society.PlayerCreateSocietyEvent;
/*    */ import com.zixuan007.society.event.society.PlayerQuitSocietyEvent;
/*    */ import com.zixuan007.society.utils.SocietyUtils;
/*    */ import com.zixuan007.society.window.WindowManager;
/*    */ import com.zixuan007.society.window.society.MessageWindow;
/*    */ import com.zixuan007.society.window.society.SocietyWindow;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ public class SocietyListener
/*    */   implements Listener
/*    */ {
/*    */   private SocietyPlugin societyPlugin;
/*    */   
/*    */   public SocietyListener(SocietyPlugin societyPlugin) {
/* 29 */     this.societyPlugin = societyPlugin;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @EventHandler(priority = EventPriority.MONITOR)
/*    */   public void onCreate(PlayerCreateSocietyEvent event) {
/* 38 */     Player player = event.getPlayer();
/* 39 */     Society society = event.getSociety();
/* 40 */     final Config config = this.societyPlugin.getConfig();
/* 41 */     society.getPost().put(player.getName(), new ArrayList()
/*    */         {
/*    */         
/*    */         });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 49 */     society.saveData();
/* 50 */     SocietyPlugin.getInstance().getSocieties().add(event.getSociety());
/* 51 */     SocietyPlugin.getInstance().getLogger().info("§a玩家: §b" + player.getName() + " §a创建公会名称: §e" + society.getSocietyName());
/* 52 */     MessageWindow messageWindow = WindowManager.getMessageWindow("§a创建 §l§b" + society.getSocietyName() + " §a公会成功", (FormWindow)new SocietyWindow(player), "返回上级");
/* 53 */     player.showFormWindow((FormWindow)messageWindow);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @EventHandler(priority = EventPriority.MONITOR)
/*    */   public void onJoin(PlayerApplyJoinSocietyEvent event) {
/* 62 */     Player player = event.getPlayer();
/* 63 */     Society society = event.getSociety();
/* 64 */     Config config = this.societyPlugin.getConfig();
/* 65 */     society.getTempApply().remove(player.getName());
/* 66 */     society.getTempApply().add(player.getName());
/* 67 */     society.saveData();
/* 68 */     MessageWindow messageWindow = WindowManager.getMessageWindow(" §a成功申请加入 §l§b" + society.getSocietyName() + " §a公会,请耐心等待§c会长进行处理", (FormWindow)new SocietyWindow(player), "返回上级");
/* 69 */     player.showFormWindow((FormWindow)messageWindow);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @EventHandler(priority = EventPriority.MONITOR)
/*    */   public void onQuic(PlayerQuitSocietyEvent event) {
/* 79 */     Player player = event.getPlayer();
/* 80 */     Society society = event.getSociety();
/* 81 */     society.getPost().remove(player.getName());
/* 82 */     society.saveData();
/* 83 */     MessageWindow messageWindow = WindowManager.getMessageWindow("§a成功退出 §l§c" + society.getSocietyName() + " §a公会", (FormWindow)new SocietyWindow(player), "返回上级");
/* 84 */     player.showFormWindow((FormWindow)messageWindow);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
/*    */   public void onChat(PlayerChatEvent event) {
/* 94 */     Player player = event.getPlayer();
/* 95 */     String message = event.getMessage();
/* 96 */     boolean isChat = ((Boolean)this.societyPlugin.getConfig().get("是否更改聊天")).booleanValue();
/* 97 */     if (isChat) {
/* 98 */       message = SocietyUtils.formatChat(player, message);
/* 99 */       event.setFormat(message);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\listener\SocietyListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */