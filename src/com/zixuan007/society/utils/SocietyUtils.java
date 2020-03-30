/*     */ package com.zixuan007.society.utils;
/*     */ 
/*     */ import cn.nukkit.Player;
/*     */ import cn.nukkit.item.Item;
/*     */ import cn.nukkit.utils.Config;
/*     */ import com.zixuan007.society.SocietyPlugin;
/*     */ import com.zixuan007.society.domain.Society;
/*     */ import java.io.File;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import me.onebone.economyapi.EconomyAPI;
/*     */ import tip.utils.Api;
/*     */ 
/*     */ public class SocietyUtils
/*     */ {
/*  21 */   public static final String FILE_SEPARATOR = System.getProperty("file.separator");
/*  22 */   public static final String SOCIETYFOLDER = SocietyPlugin.getInstance().getServer().getPluginPath() + SocietyPlugin.getInstance().getName() + FILE_SEPARATOR + "Society" + FILE_SEPARATOR;
/*  23 */   public static final String CONFIGFOLDER = SocietyPlugin.getInstance().getServer().getPluginPath() + SocietyPlugin.getInstance().getName() + FILE_SEPARATOR;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Boolean isSocietyNameExist(String societyName) {
/*  32 */     String filePath = SOCIETYFOLDER + societyName + ".yml";
/*  33 */     File societyFile = new File(filePath);
/*  34 */     return Boolean.valueOf(societyFile.exists());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getFormatDateTime() {
/*  43 */     long nowTime = System.currentTimeMillis();
/*  44 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
/*  45 */     return sdf.format(Long.valueOf(nowTime));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isJoinSociety(String playerName) {
/*  55 */     ArrayList<Society> societies = SocietyPlugin.getInstance().getSocieties();
/*  56 */     for (Society society : societies) {
/*  57 */       for (Map.Entry<String, ArrayList<Object>> entry : (Iterable<Map.Entry<String, ArrayList<Object>>>)society.getPost().entrySet()) {
/*  58 */         String name = entry.getKey();
/*  59 */         if (name.equals(playerName)) {
/*  60 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*  64 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Society getSocietyByPlayerName(String playerName) {
/*  74 */     ArrayList<Society> societies = SocietyPlugin.getInstance().getSocieties();
/*  75 */     for (Society society : societies) {
/*  76 */       for (Map.Entry<String, ArrayList<Object>> entry : (Iterable<Map.Entry<String, ArrayList<Object>>>)society.getPost().entrySet()) {
/*  77 */         String name = entry.getKey();
/*  78 */         if (name.equals(playerName)) {
/*  79 */           return society;
/*     */         }
/*     */       } 
/*     */     } 
/*  83 */     return null;
/*     */   }
/*     */   
/*     */   public static List<String> getMemberList(Society society, int currentPage) {
/*  87 */     return getMemberList(society, currentPage, 10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<String> getMemberList(Society society, int currentPage, int limit) {
/*  98 */     HashMap<String, ArrayList<Object>> postMap = society.getPost();
/*  99 */     ArrayList<HashMap<String, Object>> postList = new ArrayList<>();
/* 100 */     ArrayList<String> tempList = new ArrayList<>();
/* 101 */     for (Map.Entry<String, ArrayList<Object>> entry : postMap.entrySet()) {
/* 102 */       ArrayList<Object> value = entry.getValue();
/* 103 */       final String playerName = entry.getKey();
/* 104 */       final Integer grade = (Integer)value.get(1);
/* 105 */       postList.add(new HashMap<String, Object>()
/*     */           {
/*     */           
/*     */           });
/*     */     } 
/*     */ 
/*     */     
/* 112 */     Collections.sort(postList, new Comparator<HashMap<String, Object>>()
/*     */         {
/*     */           public int compare(HashMap<String, Object> map1, HashMap<String, Object> map2) {
/* 115 */             Integer grade = (Integer)map1.get("grade");
/* 116 */             Integer grade1 = (Integer)map2.get("grade");
/* 117 */             return (grade.intValue() < grade1.intValue()) ? 1 : ((grade.intValue() > grade1.intValue()) ? -1 : (grade.equals(grade1) ? 0 : -1));
/*     */           }
/*     */         });
/* 120 */     postList.forEach(map -> {
/*     */           String name = (String)map.get("name");
/*     */           tempList.add(name);
/*     */         });
/* 124 */     ArrayList<String> members = tempList;
/* 125 */     int pageNumber = (members.size() % limit == 0) ? (society.getPost().size() / limit) : (society.getPost().size() / limit + 1);
/* 126 */     if (currentPage > pageNumber) return null; 
/* 127 */     if (currentPage == 1) {
/* 128 */       if (members.size() <= limit) return members; 
/* 129 */       if (members.size() > limit) return members.subList(0, limit); 
/*     */     } else {
/* 131 */       int pageNumberSize = --currentPage * limit;
/* 132 */       List<String> subMembers = members.subList(pageNumberSize, members.size());
/* 133 */       if (subMembers.size() < limit) {
/* 134 */         return members.subList(pageNumberSize, pageNumberSize + subMembers.size());
/*     */       }
/* 136 */       return members.subList(pageNumberSize, pageNumberSize + limit);
/*     */     } 
/*     */     
/* 139 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isNumeric(String str) {
/* 149 */     for (int i = 0; i < str.length(); i++) {
/* 150 */       if (!Character.isDigit(str.charAt(i))) {
/* 151 */         return false;
/*     */       }
/*     */     } 
/* 154 */     return true;
/*     */   }
/*     */   
/*     */   public static int getTotalMemberPage(Society society) {
/* 158 */     return getTotalMemberPage(society, 10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getTotalMemberPage(Society society, int limit) {
/* 168 */     return (society.getPost().size() % limit == 0) ? (society.getPost().size() / limit) : (society.getPost().size() / limit + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Society> getSocietyList(int currentPage) {
/* 178 */     ArrayList<Society> societies = SocietyPlugin.getInstance().getSocieties();
/* 179 */     int totalPage = (societies.size() % 10 == 0) ? (societies.size() / 10) : (societies.size() / 10 + 1);
/* 180 */     if (currentPage > totalPage) return null; 
/* 181 */     if (currentPage == 1) {
/* 182 */       if (societies.size() <= 10) return societies; 
/* 183 */       if (societies.size() > 10) return societies.subList(0, 10); 
/*     */     } else {
/* 185 */       int pageNumberSize = --currentPage * 10;
/* 186 */       List<Society> subMembers = societies.subList(pageNumberSize, societies.size());
/* 187 */       if (subMembers.size() < 10) {
/* 188 */         return societies.subList(pageNumberSize, pageNumberSize + subMembers.size());
/*     */       }
/* 190 */       return societies.subList(pageNumberSize, pageNumberSize + 10);
/*     */     } 
/*     */     
/* 193 */     return null;
/*     */   }
/*     */   
/*     */   public static int getSocietyListTotalPage(int currentPage, int limit) {
/* 197 */     ArrayList<Society> societies = SocietyPlugin.getInstance().getSocieties();
/* 198 */     int totalPage = (societies.size() % limit == 0) ? (societies.size() / limit) : (societies.size() / limit + 1);
/* 199 */     return totalPage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getTotalSocietiesPage() {
/* 208 */     return (SocietyPlugin.getInstance().getSocieties().size() % 10 == 0) ? (SocietyPlugin.getInstance().getSocieties().size() / 10) : (SocietyPlugin.getInstance().getSocieties().size() / 10 + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Society getSocietysByID(long sid) {
/* 218 */     for (Society society : SocietyPlugin.getInstance().getSocieties()) {
/* 219 */       if (society.getSid() == sid) {
/* 220 */         return society;
/*     */       }
/*     */     } 
/* 223 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getPostGradeByName(String playerName) {
/* 233 */     Config config = SocietyPlugin.getInstance().getConfig();
/* 234 */     ArrayList<HashMap<String, Object>> post = (ArrayList<HashMap<String, Object>>)config.get("post");
/* 235 */     for (HashMap<String, Object> map : post) {
/* 236 */       Integer grade = (Integer)map.get("grade");
/* 237 */       String name1 = (String)map.get("name");
/* 238 */       if (name1.equals(playerName))
/* 239 */         return grade.intValue(); 
/*     */     } 
/* 241 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isChairman(String playerName) {
/* 251 */     for (Society society : SocietyPlugin.getInstance().getSocieties()) {
/* 252 */       if (society.getPresidentName().equals(playerName)) return true; 
/*     */     } 
/* 254 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeSociety(String societyName) {
/* 263 */     String path = SOCIETYFOLDER + societyName + ".yml";
/* 264 */     File file = new File(path);
/* 265 */     System.gc();
/* 266 */     boolean isdelete = file.delete();
/* 267 */     if (isdelete) {
/* 268 */       SocietyPlugin.getInstance().getLogger().info("§a公会 §b" + file.getName() + " §a删除成功");
/*     */     } else {
/* 270 */       SocietyPlugin.getInstance().getLogger().info("§c公会 §4" + file.getName() + " §c删除失败");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getPostByName(String playerName, Society society) {
/* 281 */     ArrayList<Object> list = (ArrayList<Object>)society.getPost().get(playerName);
/* 282 */     return (String)list.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String formatButtomText(String tipText, Player player) {
/* 292 */     return formatText(tipText, player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String formatChat(Player player, String message) {
/* 302 */     Config config = SocietyPlugin.getInstance().getConfig();
/* 303 */     String chatText = (String)config.get("聊天信息格式");
/* 304 */     chatText = chatText.replaceAll("\\$\\{message\\}", message);
/* 305 */     return formatText(chatText, player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String formatText(String text, Player player) {
/* 315 */     Item itemInHand = player.getInventory().getItemInHand();
/* 316 */     String itemID = itemInHand.getId() + ":" + itemInHand.getDamage();
/* 317 */     Double myMoney = Double.valueOf(EconomyAPI.getInstance().myMoney(player));
/* 318 */     Society society = getSocietyByPlayerName(player.getName());
/* 319 */     String societyNam = (society != null) ? ("§9" + society.getSocietyName()) : "无公会";
/* 320 */     String societyGrade = (society != null) ? (society.getGrade() + "") : "无等级";
/* 321 */     String postName = (society != null) ? (new StringBuilder()).append(((ArrayList)society.getPost().get(player.getName())).get(0)).append("").toString() : "无职位";
/* 322 */     String title = (String)SocietyPlugin.getInstance().getTitleConfig().get(player.getName());
/* 323 */     String name = player.getLevel().getName();
/* 324 */     float ticksPerSecond = SocietyPlugin.getInstance().getServer().getTicksPerSecond();
/* 325 */     text = Api.strReplace(text, player);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 334 */     text = text.replaceAll("\\$\\{world\\}", name).replaceAll("\\$\\{societyName\\}", societyNam).replaceAll("\\$\\{societyGrade\\}", societyGrade).replaceAll("\\$\\{playerName\\}", player.getName()).replaceAll("\\$\\{post\\}", postName).replaceAll("\\$\\{tps\\}", ticksPerSecond + "").replaceAll("\\$\\{money\\}", myMoney.toString()).replaceAll("\\$\\{itemID\\}", itemID).replaceAll("\\$\\{title\\}", title);
/* 335 */     return text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long getNextSid() {
/* 344 */     ArrayList<Society> societies = SocietyPlugin.getInstance().getSocieties();
/* 345 */     int size = SocietyPlugin.getInstance().getSocieties().size();
/* 346 */     if (size == 0) return 1L; 
/* 347 */     long max = 0L;
/* 348 */     for (Society society : societies) {
/* 349 */       if (society.getSid() > max) {
/* 350 */         max = society.getSid();
/*     */       }
/*     */     } 
/* 353 */     return ++max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<String> getAllPost() {
/* 360 */     List<Map<String, Object>> post = (List<Map<String, Object>>)SocietyPlugin.getInstance().getConfig().get("post");
/* 361 */     ArrayList<String> arrayList = new ArrayList<>();
/* 362 */     for (Map<String, Object> map : post) {
/* 363 */       String name = (String)map.get("name");
/* 364 */       if (name.equals("会长"))
/* 365 */         continue;  arrayList.add(name);
/*     */     } 
/* 367 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addMember(String playerNmae, Society society) {
/* 377 */     SocietyPlugin.getInstance().getSocieties().forEach(society1 -> society1.getTempApply().remove(playerNmae));
/*     */ 
/*     */     
/* 380 */     society.getPost().put(playerNmae, new ArrayList()
/*     */         {
/*     */         
/*     */         });
/*     */ 
/*     */     
/* 386 */     society.saveData();
/*     */   }
/*     */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\societ\\utils\SocietyUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */