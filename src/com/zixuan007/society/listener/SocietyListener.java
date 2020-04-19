package com.zixuan007.society.listener;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockWallSign;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.Binary;
import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.event.society.PlayerApplyJoinSocietyEvent;
import com.zixuan007.society.event.society.PlayerCreateSocietyEvent;
import com.zixuan007.society.event.society.PlayerQuitSocietyEvent;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.society.MessageWindow;
import com.zixuan007.society.window.society.SocietyWindow;
import me.onebone.economyapi.EconomyAPI;

import java.util.*;

public class SocietyListener implements Listener {
    private SocietyPlugin societyPlugin;
    private List<String> affrimBuyPlayer = new ArrayList<>();

    public SocietyListener(SocietyPlugin societyPlugin) {
        this.societyPlugin = societyPlugin;
    }

    /**
     * 当创建公会的时候调用此事件
     *
     * @param event
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onCreate(PlayerCreateSocietyEvent event) {
        Player player = event.getPlayer();
        Society society = event.getSociety();
        final Config config = this.societyPlugin.getConfig();
        society.getPost().put(player.getName(), new ArrayList<Object>() {
            {
                ArrayList<Object> post = (ArrayList) config.get("post");
                HashMap<String, Object> map = (HashMap) post.get(0);
                this.add(map.get("name"));
                this.add(map.get("grade"));
            }
        });
        society.saveData();
        SocietyPlugin.getInstance().getSocieties().add(event.getSociety());
        SocietyPlugin.getInstance().getLogger().info("§a玩家: §b" + player.getName() + " §a创建公会名称: §e" + society.getSocietyName());
        MessageWindow messageWindow = WindowManager.getMessageWindow("§a创建 §l§b" + society.getSocietyName() + " §a公会成功", new SocietyWindow(player), "返回上级");
        player.showFormWindow(messageWindow);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerApplyJoinSocietyEvent event) {
        Player player = event.getPlayer();
        Society society = event.getSociety();
        Config config = this.societyPlugin.getConfig();
        society.getTempApply().remove(player.getName());
        society.getTempApply().add(player.getName());
        society.saveData();
        MessageWindow messageWindow = WindowManager.getMessageWindow(" §a成功申请加入 §l§b" + society.getSocietyName() + " §a公会,请耐心等待§c会长进行处理", new SocietyWindow(player), "返回上级");
        player.showFormWindow(messageWindow);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuic(PlayerQuitSocietyEvent event) {
        Player player = event.getPlayer();
        Society society = event.getSociety();
        society.getPost().remove(player.getName());
        society.saveData();
        MessageWindow messageWindow = WindowManager.getMessageWindow("§a成功退出 §l§c" + society.getSocietyName() + " §a公会", new SocietyWindow(player), "返回上级");
        player.showFormWindow(messageWindow);

        //检测到玩家正在创建公会商店
        if(SocietyUtils.onCreatePlayer.containsKey(player.getName())){
            ArrayList<Object> list = SocietyUtils.onCreatePlayer.get(player);
            Item item = (Item) list.get(1);
            player.getInventory().addItem(item);
            SocietyUtils.onCreatePlayer.remove(player.getName());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (this.societyPlugin.getConfig().getBoolean("isChat")) {
            message = SocietyUtils.formatChat(player, message);
            event.setFormat(message);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onSetShop(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (SocietyUtils.onCreatePlayer.keySet().contains(player.getName())) {
            if (block instanceof BlockWallSign) {
                if (!SocietyUtils.isJoinSociety(player.getName())) {
                    player.sendMessage(">> §c检测到你当前还没有加入公会");
                    SocietyUtils.onCreatePlayer.remove(player.getName());
                    return;
                }

                if(SocietyUtils.isSetShop(block)){
                    player.sendMessage(">> §c此木牌已经设置了商店,请勿重复设置");
                    return;
                }

                Society society = SocietyUtils.getSocietyByPlayerName(player.getName());
                //进行数据的存储
                Config societyShopConfig = societyPlugin.getSocietyShopConfig();
                String levelName = block.getLevel().getName();
                int blockX = block.getFloorX();
                int blockZ = block.getFloorZ();
                String key = player.getName() + "-" + levelName +"-"+ blockX + "-" + blockZ;
                ArrayList<Object> list = SocietyUtils.onCreatePlayer.get(player.getName());
                int price = (int) list.get(0);
                Item item = (Item) list.get(1);
                societyShopConfig.set(key, new HashMap<String, Object>() {
                    {
                        put("x", block.getFloorX());
                        put("y", block.getFloorY());
                        put("z", block.getFloorZ());
                        put("levelName", levelName);
                        put("creator", player.getName());
                        put("sid", (int)society.getSid());
                        put("price", price);
                        put("itemName", item.getCustomName());
                        put("itemID-Meta", item.getId()+"-"+item.getDamage());
                        put("count", item.getCount());
                        put("nbt", Binary.bytesToHexString(item.getCompoundTag()));
                    }
                });
                societyShopConfig.save();
                //进行木牌内容设置
                BlockWallSign signWall = (BlockWallSign) block;
                BlockEntity blockEntity = block.getLevel().getBlockEntity(new Vector3(block.getFloorX(), block.getFloorY(), block.getFloorZ()));
                if (blockEntity instanceof BlockEntitySign) {
                    Config config = societyPlugin.getConfig();
                    ArrayList<String> lineText = new ArrayList<>();
                    List<String> societyShopFormat = (List<String>) config.get("societyShopFormat");

                    for (int i = 0; i < societyShopFormat.size(); i++) {
                        String text = societyShopFormat.get(i);
                        text = PluginUtils.formatText(text, player);
                        text=text.replaceAll("\\$\\{itemName\\}", item.getCustomName());
                        text=text.replaceAll("\\$\\{itemPrice\\}", price+"");
                        text=text.replaceAll("\\$\\{count\\}", item.getCount()+"");
                        lineText.add(text);
                    }
                    ((BlockEntitySign) blockEntity).setText(lineText.toArray(new String[lineText.size()]));
                    SocietyUtils.onCreatePlayer.remove(player.getName());
                    player.sendMessage(">> §a商店创建成功");
                    return;
                }
            }
        }

        //当玩家点击的是公会商店
        if (block instanceof BlockWallSign) {
            BlockEntity blockEntity = block.getLevel().getBlockEntity(new Vector3(block.getFloorX(), block.getFloorY(), block.getFloorZ()));
            if (blockEntity instanceof BlockEntitySign) {
                Config societyShopConfig = SocietyPlugin.getInstance().getSocietyShopConfig();
                for (Map.Entry<String, Object> entry : societyShopConfig.getAll().entrySet()) {
                    String key = entry.getKey();
                    HashMap<String, Object> value = (HashMap<String, Object>) entry.getValue();
                    int shopX = (int) value.get("x");
                    int shopY = (int) value.get("y");
                    int shopZ = (int) value.get("z");
                    int price = (int) value.get("price");
                    int sid = (int) value.get("sid");
                    String creator = (String) value.get("creator");
                    String levelName = (String) value.get("levelName");
                    Object dissolve = value.get("dissolve");

                    int blockX = block.getFloorX();
                    int blockY = block.getFloorY();
                    int blockZ = block.getFloorZ();
                    String blockLevelName = block.getLevel().getName();

                    boolean flag=(blockX == shopX && blockY == shopY && blockZ == shopZ);

                    if(flag){
                        double myMoney = EconomyAPI.getInstance().myMoney(player);
                        if(myMoney < price){
                            player.sendMessage(">> §a当前余额不足无法进行购买");
                            return;
                        }
                        if(!SocietyUtils.isJoinSociety(player.getName())){
                            player.sendMessage(">> §c你还没有加入公会无法进行购买");
                            return;
                        }
                        Society society = SocietyUtils.getSocietyByPlayerName(player.getName());
                        if(!(society.getSid() == sid)){
                            player.sendMessage(">> §c你不是本公会成员无法进行购买");
                            return;
                        }

                        if(dissolve != null){
                            player.sendMessage(">> §c此木牌商店功能或者公会已解散,等待人员销毁!");
                            return;
                        }

                        if(!affrimBuyPlayer.contains(player.getName())){
                            player.sendMessage(">> §c请再点击一次购买");
                            affrimBuyPlayer.add(player.getName());
                            return;
                        }
                        EconomyAPI.getInstance().reduceMoney(player,price);
                        EconomyAPI.getInstance().addMoney(creator,price);
                        String nbtDataStr = (String) value.get("nbt");
                        byte[] bytes = Binary.hexStringToBytes(nbtDataStr);
                        int count = (int) value.get("count");
                        String itemIDStr = (String) value.get("itemID-Meta");
                        String[] split = itemIDStr.split("-");
                        String itemID = split[0];
                        String meta = split[1];
                        player.getInventory().addItem(Item.get(Integer.parseInt(itemID),Integer.parseInt(meta),count,bytes));
                        block.onBreak(Item.get(0));
                        societyShopConfig.remove(key);
                        societyShopConfig.save();
                        player.sendMessage(">> §a购买成功,进行木牌的销毁");
                    }
                }
            }
        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        Config societyShopConfig = SocietyPlugin.getInstance().getSocietyShopConfig();
        for (Map.Entry<String, Object> entry : societyShopConfig.getAll().entrySet()) {
            String key = entry.getKey();
            HashMap<String,Object> value = (HashMap<String, Object>) entry.getValue();
            Object dissolve = value.get("dissolve");
            ArrayList<Object> itemData = new ArrayList<>();
            if(dissolve != null){
                String creator = (String) value.get("creator");
                if(player.getName().equals(creator)){
                    itemData.add(value.get("itemID-Meta"));
                    itemData.add(value.get("count"));
                    itemData.add(value.get("nbt"));
                    Item item = PluginUtils.parseItemByList(itemData);
                    player.getInventory().addItem(item);
                    societyShopConfig.remove(key);
                    societyShopConfig.save();
                }
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        //检测到玩家已经创建过公会商店
        Config societyShopConfig = societyPlugin.getSocietyShopConfig();
        for (Map.Entry<String, Object> entry : societyShopConfig.getAll().entrySet()) {
            String key = entry.getKey();
            HashMap<String,Object> value = (HashMap<String, Object>) entry.getValue();
            String creator = (String) value.get("creator");
            Object dissolve = value.get("dissolve");
            ArrayList<Object> itemData = new ArrayList<>();
            if(player.getName().equals(creator) && dissolve != null){
                itemData.add(value.get("itemID-Meta"));
                itemData.add(value.get("count"));
                itemData.add(value.get("nbt"));
                Item item = PluginUtils.parseItemByList(itemData);
                player.getInventory().addItem(item);
                player.sendMessage(">> §a公会商店物品已返还!");
                societyShopConfig.remove(key);
            }
        }
    }

    @EventHandler
    public void onBreakShop(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        int blockFloorX = block.getFloorX();
        int blockFloorY = block.getFloorY();
        int blockFloorZ = block.getFloorZ();
        Level level = player.getLevel();
        Config societyShopConfig = SocietyPlugin.getInstance().getSocietyShopConfig();
        for (Map.Entry<String, Object> entry : societyShopConfig.getAll().entrySet()) {
            String key = entry.getKey();
            HashMap<String,Object> value = (HashMap<String, Object>) entry.getValue();
            int x = (int) value.get("x");
            int y = (int) value.get("y");
            int z = (int) value.get("z");
            String levelName = (String) value.get("levelName");
            if (player.getLevel().getName().equals(levelName)) {
                Block signBlock = level.getBlock(new Vector3(x, y, z));
                boolean isX = (blockFloorX + 1 == x || blockFloorX - 1 == x || blockFloorX == x);
                boolean isZ = (blockFloorZ + 1 == z || blockFloorZ - 1 == z || blockFloorZ == z);
                if((isX && isZ && !player.isOp())){
                    event.setCancelled();
                    player.sendMessage(">> §c你没有权限移除本公会商店!");
                    return;
                }

                if(isX && isZ && player.isOp()){
                    value.put("dissolve",true);
                    societyShopConfig.set(key,value);
                    societyShopConfig.save();
                    player.sendMessage(">> §a商店移除成功");
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onQuitGame(PlayerQuitEvent event){
        Player player = event.getPlayer();
        affrimBuyPlayer.remove(player.getName());
    }
}