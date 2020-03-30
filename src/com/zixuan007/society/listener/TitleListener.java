/*     */ package com.zixuan007.society.listener;
/*     */ 
/*     */ import cn.nukkit.Player;
/*     */ import cn.nukkit.block.Block;
/*     */ import cn.nukkit.blockentity.BlockEntity;
/*     */ import cn.nukkit.blockentity.BlockEntitySign;
/*     */ import cn.nukkit.event.Event;
/*     */ import cn.nukkit.event.EventHandler;
/*     */ import cn.nukkit.event.EventPriority;
/*     */ import cn.nukkit.event.Listener;
/*     */ import cn.nukkit.event.block.BlockBreakEvent;
/*     */ import cn.nukkit.event.player.PlayerInteractEvent;
/*     */ import cn.nukkit.event.player.PlayerJoinEvent;
/*     */ import cn.nukkit.event.player.PlayerQuitEvent;
/*     */ import cn.nukkit.level.Level;
/*     */ import cn.nukkit.level.Position;
/*     */ import cn.nukkit.math.Vector3;
/*     */ import cn.nukkit.utils.Config;
/*     */ import com.zixuan007.api.FloatingTextAPI;
/*     */ import com.zixuan007.society.SocietyPlugin;
/*     */ import com.zixuan007.society.event.title.BuyTitleEvent;
/*     */ import com.zixuan007.society.event.title.CreateTitleShopEvent;
/*     */ import com.zixuan007.society.event.title.RemoveTitleShopEvent;
/*     */ import com.zixuan007.society.utils.SocietyUtils;
/*     */ import com.zixuan007.society.utils.TitleUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import me.onebone.economyapi.EconomyAPI;
/*     */ 
/*     */ public class TitleListener
/*     */   implements Listener
/*     */ {
/*     */   private SocietyPlugin societyPlugin;
/*  35 */   private ArrayList<String> affirmBuyTitlePlayer = new ArrayList<>();
/*     */   
/*     */   public TitleListener(SocietyPlugin societyPlugin) {
/*  38 */     this.societyPlugin = societyPlugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR)
/*     */   public void onJoin(PlayerJoinEvent event) {
/*  48 */     Player player = event.getPlayer();
/*     */     
/*  50 */     Config titleConfig = this.societyPlugin.getTitleConfig();
/*  51 */     String title = (String)titleConfig.get(player.getName());
/*  52 */     if (title == null) {
/*  53 */       titleConfig.set(player.getName(), "无称号");
/*  54 */       titleConfig.save();
/*     */     } 
/*     */     
/*  57 */     Config config = this.societyPlugin.getConfig();
/*  58 */     boolean isSetNameTag = ((Boolean)config.get("是否开启更改头部")).booleanValue();
/*  59 */     if (isSetNameTag) {
/*  60 */       String configNameTag = (String)config.get("头部更改");
/*  61 */       String formatNameTag = SocietyUtils.formatText(configNameTag, player);
/*  62 */       player.setNameTag(formatNameTag);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
/*     */   public void onSetTitleShop(PlayerInteractEvent event) {
/*  73 */     Player player = event.getPlayer();
/*  74 */     String playerName = player.getName();
/*  75 */     Level level = player.getLevel();
/*  76 */     Block block = event.getBlock();
/*  77 */     int id = block.getId();
/*  78 */     BlockEntity blockEntity = level.getBlockEntity(new Vector3(block.getX(), block.getY(), block.getZ()));
/*  79 */     if (TitleUtils.onCreateName.containsKey(playerName)) {
/*  80 */       if (id == 68) {
/*  81 */         if (blockEntity instanceof BlockEntitySign) {
/*  82 */           ArrayList<String> titleText = (ArrayList<String>)this.societyPlugin.getConfig().get("称号商店木牌格式");
/*  83 */           ArrayList<String> tempList = new ArrayList<>();
/*  84 */           String title = (String)((HashMap)TitleUtils.onCreateName.get(playerName)).get("title");
/*  85 */           String money = (String)((HashMap)TitleUtils.onCreateName.get(playerName)).get("money");
/*  86 */           tempList.addAll(titleText);
/*  87 */           tempList.set(1, ((String)titleText.get(1)).replaceAll("\\$\\{title\\}", title));
/*  88 */           tempList.set(2, ((String)titleText.get(2)).replaceAll("\\$\\{money\\}", money));
/*  89 */           ((BlockEntitySign)blockEntity).setText(tempList.<String>toArray(new String[tempList.size()]));
/*  90 */           SocietyPlugin.getInstance().getServer().getPluginManager().callEvent((Event)new CreateTitleShopEvent(player, (BlockEntitySign)blockEntity));
/*     */         } 
/*     */       } else {
/*  93 */         player.sendMessage(">> §c请点击贴在墙上的木牌");
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
/*     */   public void onClickTitleShop(PlayerInteractEvent event) {
/* 103 */     Player player = event.getPlayer();
/* 104 */     Block block = event.getBlock();
/* 105 */     int id = block.getId();
/* 106 */     int x = block.getFloorX();
/* 107 */     int y = block.getFloorY();
/* 108 */     int z = block.getFloorZ();
/* 109 */     String levelName = block.getLevel().getName();
/* 110 */     Config titleShopConfig = SocietyPlugin.getInstance().getTitleShopConfig();
/* 111 */     if (id == 68) {
/* 112 */       for (Map.Entry<String, Object> map : (Iterable<Map.Entry<String, Object>>)titleShopConfig.getAll().entrySet()) {
/* 113 */         String key = map.getKey();
/* 114 */         ArrayList<Object> list = (ArrayList<Object>)map.getValue();
/* 115 */         int signX = ((Integer)list.get(0)).intValue();
/* 116 */         int signY = ((Integer)list.get(1)).intValue();
/* 117 */         int signZ = ((Integer)list.get(2)).intValue();
/* 118 */         String signLevelName = (String)list.get(3);
/* 119 */         String str_Money = (String)list.get(4);
/* 120 */         int money = Integer.parseInt(str_Money);
/* 121 */         if (x == signX && y == signY && z == signZ && levelName.equals(signLevelName)) {
/* 122 */           if (this.affirmBuyTitlePlayer.contains(player.getName())) {
/* 123 */             if (EconomyAPI.getInstance().myMoney(player) < money) {
/* 124 */               player.sendMessage(">> §c余额不足无法购买");
/*     */               return;
/*     */             } 
/* 127 */             EconomyAPI.getInstance().reduceMoney(player, money);
/* 128 */             SocietyPlugin.getInstance().getServer().getPluginManager().callEvent((Event)new BuyTitleEvent(player, key, money)); continue;
/*     */           } 
/* 130 */           this.affirmBuyTitlePlayer.add(player.getName());
/* 131 */           player.sendMessage(">> §c请再次点击一次木牌");
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR)
/*     */   public void onBuyTitle(BuyTitleEvent event) {
/* 140 */     Player player = event.getPlayer();
/* 141 */     String title = event.getTitle();
/* 142 */     Config titleConfig = this.societyPlugin.getTitleConfig();
/* 143 */     titleConfig.set(player.getName(), title);
/* 144 */     titleConfig.save();
/* 145 */     this.affirmBuyTitlePlayer.remove(player.getName());
/* 146 */     player.sendMessage(">> §a成功购买 §b" + title + " §a称号");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR)
/*     */   public void onCreateTitleShop(CreateTitleShopEvent event) {
/* 156 */     Player player = event.getPlayer();
/* 157 */     String playerName = player.getName();
/* 158 */     BlockEntitySign wallSign = event.getWallSign();
/* 159 */     double x = wallSign.getFloorX();
/* 160 */     double y = wallSign.getY() + 1.0D;
/* 161 */     double z = wallSign.getFloorZ();
/* 162 */     int damage = wallSign.getBlock().getDamage();
/* 163 */     if (damage % 5 == 0) {
/* 164 */       z += 0.5D;
/* 165 */     } else if (damage % 5 == 1) {
/* 166 */       z += 0.5D;
/*     */     } 
/* 168 */     if (damage % 5 == 2) {
/* 169 */       z += 0.6D;
/* 170 */       x += 0.5D;
/* 171 */     } else if (damage % 5 == 3) {
/* 172 */       x += 0.5D;
/* 173 */     } else if (damage % 5 == 4) {
/* 174 */       x += 0.6D;
/* 175 */       z += 0.5D;
/*     */     } 
/* 177 */     Config titleShopConfig = this.societyPlugin.getTitleShopConfig();
/* 178 */     String title = (String)((HashMap)TitleUtils.onCreateName.get(playerName)).get("title");
/* 179 */     String money = (String)((HashMap)TitleUtils.onCreateName.get(playerName)).get("money");
/* 180 */     ArrayList<Object> list = new ArrayList();
/* 181 */     list.add(Integer.valueOf(wallSign.getFloorX()));
/* 182 */     list.add(Integer.valueOf(wallSign.getFloorY()));
/* 183 */     list.add(Integer.valueOf(wallSign.getFloorZ()));
/* 184 */     list.add(wallSign.getLevel().getName());
/* 185 */     list.add(money);
/* 186 */     titleShopConfig.set(title, list);
/* 187 */     titleShopConfig.save();
/* 188 */     StringBuilder sb = new StringBuilder();
/* 189 */     for (String line : wallSign.getText()) {
/* 190 */       sb.append(line + "\n");
/*     */     }
/* 192 */     String key = wallSign.getText()[1];
/* 193 */     FloatingTextAPI.addFloatingText(sb.toString(), new Position(x, y, z, player.getLevel()), key, player.getName());
/* 194 */     player.sendMessage(">> §a称号商店创建成功");
/* 195 */     TitleUtils.onCreateName.remove(player.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR)
/*     */   public void onQuit(PlayerQuitEvent event) {
/* 205 */     String playerName = event.getPlayer().getName();
/* 206 */     TitleUtils.onCreateName.remove(playerName);
/* 207 */     this.affirmBuyTitlePlayer.remove(playerName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR)
/*     */   public void onBreak(BlockBreakEvent event) {
/* 217 */     Player player = event.getPlayer();
/* 218 */     Block block = event.getBlock();
/* 219 */     int x = block.getFloorX();
/* 220 */     int y = block.getFloorY();
/* 221 */     int z = block.getFloorZ();
/* 222 */     String levelName = block.getLevel().getName();
/* 223 */     Config titleShopConfig = SocietyPlugin.getInstance().getTitleShopConfig();
/* 224 */     int id = block.getId();
/* 225 */     titleShopConfig.getAll().forEach((title, list) -> {
/*     */           ArrayList<Object> titleShopDataList = (ArrayList<Object>)list;
/*     */           int titleShopX = ((Integer)titleShopDataList.get(0)).intValue();
/*     */           int titleShopY = ((Integer)titleShopDataList.get(1)).intValue();
/*     */           int titleShopZ = ((Integer)titleShopDataList.get(2)).intValue();
/*     */           String titleShopLevelName = (String)titleShopDataList.get(3);
/*     */           System.out.println(levelName.equals(titleShopLevelName));
/* 232 */           boolean isX = (x + 1 == titleShopX || x - 1 == titleShopX || x == titleShopX);
/* 233 */           boolean isZ = (z + 1 == titleShopZ || z - 1 == titleShopZ || z == titleShopZ);
/*     */           if (isX && isZ && levelName.equals(titleShopLevelName)) {
/*     */             System.out.println("执行进入");
/*     */             if (!player.isOp()) {
/*     */               player.sendMessage(">> §c你没有权限移除称号商店");
/*     */               event.setCancelled();
/*     */             } else {
/*     */               SocietyPlugin.getInstance().getServer().getPluginManager().callEvent((Event)new RemoveTitleShopEvent(player, block, title));
/*     */             } 
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR)
/*     */   public void onRemoveTitleShop(RemoveTitleShopEvent event) {
/* 252 */     Player player = event.getPlayer();
/* 253 */     Block block = event.getBlock();
/* 254 */     Config titleShopConfig = this.societyPlugin.getTitleShopConfig();
/* 255 */     titleShopConfig.remove(event.getTitle());
/* 256 */     titleShopConfig.save();
/* 257 */     Config config = SocietyPlugin.getInstance().getConfig();
/* 258 */     ArrayList<String> textList = (ArrayList)config.get("称号商店木牌格式");
/* 259 */     String signTitle = textList.get(1);
/* 260 */     System.out.println(signTitle);
/* 261 */     signTitle = signTitle.replaceAll("\\$\\{title\\}", event.getTitle());
/* 262 */     FloatingTextAPI.removeFloatingText(player, signTitle);
/* 263 */     player.sendMessage(">> §a移除商店成功");
/*     */   }
/*     */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\listener\TitleListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */