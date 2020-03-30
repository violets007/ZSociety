/*    */ package com.zixuan007.society.window.society;
/*    */ 
/*    */ import com.zixuan007.society.SocietyPlugin;
/*    */ import com.zixuan007.society.domain.Society;
/*    */ import com.zixuan007.society.utils.SocietyUtils;
/*    */ import com.zixuan007.society.window.SimpleWindow;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MemberListWindow
/*    */   extends SimpleWindow {
/*    */   private List<String> memberList;
/*    */   private Society society;
/*    */   
/*    */   public MemberListWindow(Society society, List<String> memberList) {
/* 15 */     super((String)SocietyPlugin.getInstance().getLangConfig().get("成员列表窗口标题"), "");
/* 16 */     this.society = society;
/* 17 */     StringBuilder sb = new StringBuilder();
/* 18 */     sb.append("§l§d公会成员列表\n");
/* 19 */     memberList.forEach(name -> {
/*    */           String postByName = SocietyUtils.getPostByName(name, society);
/*    */           sb.append("职位>> §c" + postByName + " §f名称>> §b§l" + name + "\n");
/*    */         });
/* 23 */     setContent(sb.toString());
/*    */   }
/*    */   
/*    */   public List<String> getMemberList() {
/* 27 */     return this.memberList;
/*    */   }
/*    */   
/*    */   public void setMemberList(List<String> memberList) {
/* 31 */     this.memberList = memberList;
/*    */   }
/*    */   
/*    */   public Society getSociety() {
/* 35 */     return this.society;
/*    */   }
/*    */   
/*    */   public void setSociety(Society society) {
/* 39 */     this.society = society;
/*    */   }
/*    */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\window\society\MemberListWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */