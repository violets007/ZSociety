/*     */ package com.zixuan007.society.domain;
/*     */ 
/*     */ import cn.nukkit.utils.Config;
/*     */ import com.zixuan007.society.utils.SocietyUtils;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Society
/*     */ {
/*     */   private long sid;
/*     */   private String societyName;
/*     */   private String presidentName;
/*     */   private String createTime;
/*     */   private Double societyMoney;
/*     */   private HashMap<String, ArrayList<Object>> psots;
/*  21 */   private int grade = 1;
/*  22 */   private ArrayList<String> tempApply = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public Society() {}
/*     */ 
/*     */   
/*     */   public Society(long sid, String societyName, String presidentName, String createTime, Double societyMoney, HashMap<String, ArrayList<Object>> psots) {
/*  29 */     this.sid = sid;
/*  30 */     this.societyName = societyName;
/*  31 */     this.presidentName = presidentName;
/*  32 */     this.createTime = createTime;
/*  33 */     this.societyMoney = societyMoney;
/*  34 */     this.psots = psots;
/*     */   }
/*     */   
/*     */   public static Society init(Config config) {
/*  38 */     if (config.get("sid") == null) return null; 
/*  39 */     Society society = new Society();
/*  40 */     if (config.get("sid") instanceof Integer) society.sid = ((Integer)config.get("sid")).longValue(); 
/*  41 */     if (config.get("sid") instanceof Long) society.sid = ((Long)config.get("sid")).longValue(); 
/*  42 */     society.societyName = (String)config.get("societyName");
/*  43 */     society.presidentName = (String)config.get("presidentName");
/*  44 */     society.createTime = (String)config.get("createTime");
/*  45 */     society.societyMoney = (Double)config.get("societyMoney");
/*  46 */     society.psots = (HashMap<String, ArrayList<Object>>)config.get("psots");
/*  47 */     society.grade = ((Integer)config.get("grade")).intValue();
/*  48 */     society.tempApply = (ArrayList<String>)config.get("tempApply");
/*  49 */     return society;
/*     */   }
/*     */   
/*     */   public void saveData() {
/*  53 */     String societyFilePath = SocietyUtils.SOCIETYFOLDER + this.societyName + ".yml";
/*  54 */     File file = new File(societyFilePath);
/*  55 */     if (!file.exists()) {
/*     */       try {
/*  57 */         file.createNewFile();
/*  58 */       } catch (IOException e) {
/*  59 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*  62 */     Config config = new Config(file);
/*  63 */     config.set("sid", Long.valueOf(this.sid));
/*  64 */     config.set("societyName", this.societyName);
/*  65 */     config.set("presidentName", this.presidentName);
/*  66 */     config.set("createTime", this.createTime);
/*  67 */     config.set("societyMoney", this.societyMoney);
/*  68 */     config.set("psots", this.psots);
/*  69 */     config.set("grade", Integer.valueOf(this.grade));
/*  70 */     config.set("tempApply", this.tempApply);
/*  71 */     config.save();
/*     */   }
/*     */   
/*     */   public String getSocietyName() {
/*  75 */     return this.societyName;
/*     */   }
/*     */   
/*     */   public void setSocietyName(String societyName) {
/*  79 */     this.societyName = societyName;
/*     */   }
/*     */   
/*     */   public long getSid() {
/*  83 */     return this.sid;
/*     */   }
/*     */   
/*     */   public void setSid(long sid) {
/*  87 */     this.sid = sid;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPresidentName() {
/*  92 */     return this.presidentName;
/*     */   }
/*     */   
/*     */   public void setPresidentName(String presidentName) {
/*  96 */     this.presidentName = presidentName;
/*     */   }
/*     */   
/*     */   public Double getSocietyMoney() {
/* 100 */     return this.societyMoney;
/*     */   }
/*     */   
/*     */   public void setSocietyMoney(Double societyMoney) {
/* 104 */     this.societyMoney = societyMoney;
/*     */   }
/*     */   
/*     */   public HashMap<String, ArrayList<Object>> getPost() {
/* 108 */     return this.psots;
/*     */   }
/*     */   
/*     */   public void setPsots(HashMap<String, ArrayList<Object>> psots) {
/* 112 */     this.psots = psots;
/*     */   }
/*     */   
/*     */   public ArrayList<String> getTempApply() {
/* 116 */     return this.tempApply;
/*     */   }
/*     */   
/*     */   public void setTempApply(ArrayList<String> tempApply) {
/* 120 */     this.tempApply = tempApply;
/*     */   }
/*     */   
/*     */   public int getGrade() {
/* 124 */     return this.grade;
/*     */   }
/*     */   
/*     */   public void setGrade(int grade) {
/* 128 */     this.grade = grade;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 133 */     return "Society{sid=" + this.sid + ", societyName='" + this.societyName + '\'' + ", presidentName='" + this.presidentName + '\'' + ", createTime='" + this.createTime + '\'' + ", societyMoney=" + this.societyMoney + ", psots=" + this.psots + ", grade=" + this.grade + ", tempApply=" + this.tempApply + '}';
/*     */   }
/*     */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\domain\Society.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */