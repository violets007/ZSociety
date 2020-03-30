/*     */ package com.zixuan007.society;
/*     */ 
/*     */ import cn.nukkit.command.Command;
/*     */ import cn.nukkit.event.Listener;
/*     */ import cn.nukkit.plugin.Plugin;
/*     */ import cn.nukkit.plugin.PluginBase;
/*     */ import cn.nukkit.scheduler.Task;
/*     */ import cn.nukkit.utils.Config;
/*     */ import com.zixuan007.society.command.MainCommand;
/*     */ import com.zixuan007.society.command.TitleCommand;
/*     */ import com.zixuan007.society.domain.Society;
/*     */ import com.zixuan007.society.listener.ResponseLister;
/*     */ import com.zixuan007.society.listener.SocietyListener;
/*     */ import com.zixuan007.society.listener.TitleListener;
/*     */ import com.zixuan007.society.task.BottomTask;
/*     */ import com.zixuan007.society.utils.SocietyUtils;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class SocietyPlugin extends PluginBase {
/*     */   private Config config;
/*  25 */   private List<Config> societyConfigList = new ArrayList<>();
/*     */   private Config titleConfig;
/*     */   private Config LangConfig;
/*     */   private Config titleShopConfig;
/*     */   private static SocietyPlugin instance;
/*  30 */   private ArrayList<Society> societies = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  38 */     init();
/*  39 */     getLogger().info("§2公会插件开启 §c作者§f:§bzixuan007");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  47 */     getLogger().info("§2公会插件关闭 §c数据保存中...");
/*  48 */     this.societies.forEach(society -> society.saveData());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  57 */     checkPlugin("EconomyAPI");
/*  58 */     checkPlugin("Tips");
/*  59 */     checkPlugin("FloatingText");
/*  60 */     if (instance == null) instance = this; 
/*  61 */     checkConfig();
/*  62 */     saveResource("cn_ language.yml", true);
/*  63 */     registerCommand();
/*  64 */     loadConfig();
/*  65 */     loadSocietyConfig();
/*  66 */     if (((Boolean)this.config.get("是否开启底部")).booleanValue() == true)
/*  67 */       getServer().getScheduler().scheduleRepeatingTask((Task)new BottomTask(this), 10); 
/*  68 */     getServer().getPluginManager().registerEvents((Listener)new ResponseLister(), (Plugin)this);
/*  69 */     getServer().getPluginManager().registerEvents((Listener)new SocietyListener(this), (Plugin)this);
/*  70 */     getServer().getPluginManager().registerEvents((Listener)new TitleListener(this), (Plugin)this);
/*     */   }
/*     */   
/*     */   public void registerCommand() {
/*  74 */     getServer().getCommandMap().register("society", (Command)new MainCommand(), "公会");
/*  75 */     getServer().getCommandMap().register("title", (Command)new TitleCommand(), "称号");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadSocietyConfig() {
/*  82 */     File societyFolder = new File(SocietyUtils.SOCIETYFOLDER);
/*  83 */     if (!societyFolder.exists()) societyFolder.mkdirs(); 
/*  84 */     File[] files = societyFolder.listFiles();
/*  85 */     for (File file : files) {
/*  86 */       Config config = new Config(file);
/*  87 */       this.societyConfigList.add(config);
/*  88 */       if (file.getName().endsWith(".yml")) this.societies.add(Society.init(config));
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadConfig() {
/*  96 */     String titleConfigPath = SocietyUtils.CONFIGFOLDER + "Title.yml";
/*  97 */     String langPath = SocietyUtils.CONFIGFOLDER + "cn_ language.yml";
/*  98 */     String titleShopPath = SocietyUtils.CONFIGFOLDER + "TitleShopData.yml";
/*  99 */     this.titleConfig = new Config(titleConfigPath);
/* 100 */     this.LangConfig = new Config(langPath);
/* 101 */     this.titleShopConfig = new Config(titleShopPath);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkPlugin(String pluginName) {
/* 108 */     Plugin economyAPI = getServer().getPluginManager().getPlugin(pluginName);
/* 109 */     if (economyAPI == null) {
/* 110 */       getLogger().error("§c检测到 §b" + pluginName + " §c插件不存在,请先安装");
/* 111 */       getServer().shutdown();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkConfig() {
/* 120 */     String path = SocietyUtils.CONFIGFOLDER + "Config.yml";
/* 121 */     File file = new File(path);
/* 122 */     if (!file.exists()) {
/* 123 */       saveResource("Config.yml");
/*     */     } else {
/* 125 */       Config config = new Config(file, 2);
/* 126 */       String version = (String)config.get("version");
/* 127 */       String pluginVersion = getDescription().getVersion();
/* 128 */       if (version == null || !version.equals(pluginVersion)) {
/* 129 */         saveResource("Config.yml", true);
/* 130 */         getLogger().info("§c检测到配置文件版本太低,自动进行覆盖");
/*     */       } else {
/* 132 */         saveResource("Config.yml");
/*     */       } 
/*     */     } 
/* 135 */     String configPath = SocietyUtils.CONFIGFOLDER + "Config.yml";
/* 136 */     this.config = new Config(configPath);
/*     */   }
/*     */ 
/*     */   
/*     */   public Config getConfig() {
/* 141 */     return this.config;
/*     */   }
/*     */   
/*     */   public void setConfig(Config config) {
/* 145 */     this.config = config;
/*     */   }
/*     */   
/*     */   public static SocietyPlugin getInstance() {
/* 149 */     return instance;
/*     */   }
/*     */   
/*     */   public static void setInstance(SocietyPlugin instance) {
/* 153 */     SocietyPlugin.instance = instance;
/*     */   }
/*     */   
/*     */   public ArrayList<Society> getSocieties() {
/* 157 */     return this.societies;
/*     */   }
/*     */   
/*     */   public void setSocieties(ArrayList<Society> societies) {
/* 161 */     this.societies = societies;
/*     */   }
/*     */   
/*     */   public Config getTitleConfig() {
/* 165 */     return this.titleConfig;
/*     */   }
/*     */   
/*     */   public void setTitleConfig(Config titleConfig) {
/* 169 */     this.titleConfig = titleConfig;
/*     */   }
/*     */   
/*     */   public Config getLangConfig() {
/* 173 */     return this.LangConfig;
/*     */   }
/*     */   
/*     */   public void setLangConfig(Config langConfig) {
/* 177 */     this.LangConfig = langConfig;
/*     */   }
/*     */   
/*     */   public List<Config> getSocietyConfigList() {
/* 181 */     return this.societyConfigList;
/*     */   }
/*     */   
/*     */   public void setSocietyConfigList(List<Config> societyConfigList) {
/* 185 */     this.societyConfigList = societyConfigList;
/*     */   }
/*     */   
/*     */   public Config getTitleShopConfig() {
/* 189 */     return this.titleShopConfig;
/*     */   }
/*     */   
/*     */   public void setTitleShopConfig(Config titleShopConfig) {
/* 193 */     this.titleShopConfig = titleShopConfig;
/*     */   }
/*     */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\SocietyPlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */