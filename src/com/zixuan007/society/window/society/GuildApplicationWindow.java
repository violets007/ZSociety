/*    */ package com.zixuan007.society.window.society;
/*    */ 
/*    */ import cn.nukkit.Player;
/*    */ import cn.nukkit.event.Event;
/*    */ import cn.nukkit.form.element.Element;
/*    */ import cn.nukkit.form.element.ElementDropdown;
/*    */ import cn.nukkit.form.element.ElementLabel;
/*    */ import cn.nukkit.form.response.FormResponseCustom;
/*    */ import cn.nukkit.form.response.FormResponseData;
/*    */ import cn.nukkit.form.window.FormWindow;
/*    */ import com.zixuan007.society.SocietyPlugin;
/*    */ import com.zixuan007.society.domain.Society;
/*    */ import com.zixuan007.society.event.society.PlayerApplyJoinSocietyEvent;
/*    */ import com.zixuan007.society.utils.SocietyUtils;
/*    */ import com.zixuan007.society.window.CustomWindow;
/*    */ import com.zixuan007.society.window.WindowManager;
/*    */ 
/*    */ public class GuildApplicationWindow extends CustomWindow {
/* 19 */   private static SocietyPlugin societyPlugin = SocietyPlugin.getInstance();
/*    */   
/*    */   public GuildApplicationWindow() {
/* 22 */     super((String)societyPlugin.getLangConfig().get("玩家申请公会窗口标题"));
/* 23 */     addElement((Element)new ElementLabel("§c建议先查看公会列表"));
/* 24 */     ElementDropdown elementDropdown = new ElementDropdown("§b公会SID");
/* 25 */     SocietyPlugin.getInstance().getSocieties().forEach(society -> elementDropdown.addOption(society.getSid() + ""));
/*    */ 
/*    */     
/* 28 */     addElement((Element)elementDropdown);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(FormResponseCustom response, Player player) {
/* 33 */     FormResponseData dropdownResponse = response.getDropdownResponse(1);
/* 34 */     String elementContent = dropdownResponse.getElementContent();
/* 35 */     Long sid = Long.valueOf(Long.parseLong(elementContent));
/* 36 */     Society society = SocietyUtils.getSocietysByID(sid.longValue());
/* 37 */     societyPlugin.getServer().getPluginManager().callEvent((Event)new PlayerApplyJoinSocietyEvent(player, society));
/* 38 */     MessageWindow messageWindow = WindowManager.getMessageWindow("§a成功申请SID §b" + society.getSid() + " §a公会名称为 §b" + society.getSocietyName(), getParent(), "返回主界面");
/* 39 */     player.showFormWindow((FormWindow)messageWindow);
/*    */   }
/*    */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\window\society\GuildApplicationWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */