/*    */ package com.zixuan007.society.command;
/*    */ 
/*    */ import cn.nukkit.Player;
/*    */ import cn.nukkit.command.Command;
/*    */ import cn.nukkit.command.CommandSender;
/*    */ import cn.nukkit.form.window.FormWindow;
/*    */ import com.zixuan007.society.window.WindowManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TitleCommand
/*    */   extends Command
/*    */ {
/*    */   public TitleCommand() {
/* 16 */     super("称号", "设置称号主命令");
/* 17 */     getCommandParameters().clear();
/* 18 */     setPermission("op");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String name, String[] args) {
/* 23 */     Player player = (Player)sender;
/* 24 */     if (!player.isOp()) return false; 
/* 25 */     if (name.equals(getName())) {
/* 26 */       player.showFormWindow((FormWindow)WindowManager.getTitleWindow());
/* 27 */       return true;
/*    */     } 
/* 29 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\command\TitleCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */