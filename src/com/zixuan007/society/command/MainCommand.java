/*    */ package com.zixuan007.society.command;
/*    */ 
/*    */ import cn.nukkit.Player;
/*    */ import cn.nukkit.command.Command;
/*    */ import cn.nukkit.command.CommandSender;
/*    */ import cn.nukkit.form.window.FormWindow;
/*    */ import com.zixuan007.society.SocietyPlugin;
/*    */ import com.zixuan007.society.window.WindowManager;
/*    */ import com.zixuan007.society.window.society.SocietyWindow;
/*    */ 
/*    */ 
/*    */ public class MainCommand
/*    */   extends Command
/*    */ {
/* 15 */   private SocietyPlugin societyPlugin = SocietyPlugin.getInstance();
/*    */   
/*    */   public MainCommand() {
/* 18 */     super((String)SocietyPlugin.getInstance().getConfig().get("公会主命令"), "公会插件总命令", "/§b公会 §e帮助");
/* 19 */     setPermission("");
/* 20 */     getCommandParameters().clear();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String commandName, String[] args) {
/* 33 */     if (sender instanceof cn.nukkit.command.ConsoleCommandSender) {
/* 34 */       sender.sendMessage("§c进制控制台输入命令");
/* 35 */       return false;
/*    */     } 
/* 37 */     Player player = (Player)sender;
/* 38 */     if (commandName.equals(getName())) {
/* 39 */       SocietyWindow societyWindow = WindowManager.getSocietyWindow(player);
/* 40 */       player.showFormWindow((FormWindow)societyWindow);
/*    */     } 
/* 42 */     return false;
/*    */   }
/*    */ }