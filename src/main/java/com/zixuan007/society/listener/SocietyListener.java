package com.zixuan007.society.listener;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockWallSign;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.TextPacket;
import cn.nukkit.utils.Binary;
import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.event.society.PlayerApplyJoinSocietyEvent;
import com.zixuan007.society.event.society.PlayerCreateSocietyEvent;
import com.zixuan007.society.event.society.PlayerQuitSocietyEvent;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.utils.TitleUtils;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;
import me.onebone.economyapi.EconomyAPI;

import java.util.*;

/**
 * @author zixuan007
 */
public class SocietyListener implements Listener {
    private SocietyPlugin societyPlugin;
    private final List<String> affrimBuyPlayer = new ArrayList<>();

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
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");
        ArrayList<Object> post = (ArrayList<Object>) config.get("post");
        HashMap<String, Object> postInfo = (HashMap<String, Object>) post.get(0);
        SocietyUtils.getSocieties().add(society);
        SocietyUtils.addMember(player.getName(), society, "会长", (Integer) postInfo.get("grade"));
        SocietyPlugin.getInstance().getLogger().info("§a玩家: §b§l" + player.getName() + " §r§a创建公会名称: §e" + society.getSocietyName());
        FormWindow societyWindow = WindowManager.getFormWindow(WindowType.SOCIETY_WINDOW, player);
        player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, "§a创建 §l§b" + society.getSocietyName() + " §a公会成功", societyWindow, backButtonName, backButtonImage));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerApplyJoinSocietyEvent event) {
        Player player = event.getPlayer();
        Society society = event.getSociety();
        Config config = this.societyPlugin.getConfig();
        society.getTempApply().remove(player.getName());
        society.getTempApply().add(player.getName());
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");
        SocietyUtils.saveSociety(society);
        FormWindow societyWindow = WindowManager.getFormWindow(WindowType.SOCIETY_WINDOW, player);

        player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, "§a成功申请加入 §l§b" + society.getSocietyName() + " §a公会,请耐心等待§c会长进行处理", societyWindow, backButtonName, backButtonImage));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitSocietyEvent event) {
        Player player = event.getPlayer();
        Society society = event.getSociety();
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");
        society.getPost().remove(player.getName());
        SocietyUtils.saveSociety(society);
        FormWindow societyWindow = WindowManager.getFormWindow(WindowType.SOCIETY_WINDOW, player);

        player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, "§a成功退出 §l§c" + society.getSocietyName() + " §a公会", societyWindow, backButtonName, backButtonImage));

        //检测到玩家正在创建公会商店
        if (SocietyUtils.onCreatePlayer.containsKey(player.getName())) {
            ArrayList<Object> list = SocietyUtils.onCreatePlayer.get(player);
            Item item = (Item) list.get(1);
            player.getInventory().addItem(item);
            SocietyUtils.onCreatePlayer.remove(player.getName());
        }

        //移除玩家在此公会创建过的商店
        SocietyUtils.removeCreateShop(society, player.getName());
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

        if (SocietyUtils.onCreatePlayer.containsKey(player.getName())) {
            if (block instanceof BlockWallSign) {
                if (!SocietyUtils.isJoinSociety(player.getName())) {
                    player.sendMessage(PluginUtils.getLanguageInfo("message.createSocietyShopWindow.isJoin"));
                    SocietyUtils.onCreatePlayer.remove(player.getName());
                    return;
                }

                if (SocietyUtils.isSetShop(block)) {
                    player.sendMessage(PluginUtils.getLanguageInfo("message.createSocietyShopWindow.alreadSetShop"));
                    return;
                }

                if (player.isCreative() && !player.isOp()) {
                    player.sendMessage(PluginUtils.getLanguageInfo("message.createSocietyShopWindow.isCreative"));
                    return;
                }


                Society society = SocietyUtils.getSocietyByPlayerName(player.getName());
                //进行数据的存储
                Config societyShopConfig = societyPlugin.getSocietyShopConfig();
                String levelName = block.getLevel().getName();
                int blockX = block.getFloorX();
                int blockZ = block.getFloorZ();
                String key = player.getName() + "-" + levelName + "-" + blockX + "-" + blockZ;
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
                        put("sid", (int) society.getSid());
                        put("price", price);
                        put("itemName", item.getCustomName());
                        put("itemID-Meta", item.getId() + "-" + item.getDamage());
                        put("count", item.getCount());
                        put("nbt", Binary.bytesToHexString(item.getCompoundTag()));
                    }
                });
                societyShopConfig.save();
                //进行木牌内容设置
                BlockEntity blockEntity = block.getLevel().getBlockEntity(new Vector3(block.getFloorX(), block.getFloorY(), block.getFloorZ()));
                if (blockEntity instanceof BlockEntitySign) {
                    Config config = societyPlugin.getConfig();
                    ArrayList<String> lineText = new ArrayList<>();
                    List<String> societyShopFormat = (List<String>) config.get("societyShopFormat");

                    for (int i = 0; i < societyShopFormat.size(); i++) {
                        String text = societyShopFormat.get(i);
                        text = PluginUtils.formatText(text, player);
                        text = text.replaceAll("\\$\\{itemName\\}", item.getCustomName());
                        text = text.replaceAll("\\$\\{itemPrice\\}", price + "");
                        text = text.replaceAll("\\$\\{count\\}", item.getCount() + "");
                        lineText.add(text);
                    }
                    ((BlockEntitySign) blockEntity).setText(lineText.toArray(new String[lineText.size()]));
                    SocietyUtils.onCreatePlayer.remove(player.getName());
                    player.sendMessage(PluginUtils.getLanguageInfo("message.createSocietyShopWindow.success"));
                    affrimBuyPlayer.remove(player.getName());
                    event.setCancelled(true);
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

                    boolean flag = (blockX == shopX && blockY == shopY && blockZ == shopZ);

                    if (flag) {
                        event.setCancelled();
                        double myMoney = EconomyAPI.getInstance().myMoney(player);
                        if (myMoney < price) {
                            player.sendMessage(PluginUtils.getLanguageInfo("message.createSocietyShopWindow.rarelyCoin"));
                            return;
                        }
                        if (!SocietyUtils.isJoinSociety(player.getName())) {
                            player.sendMessage(PluginUtils.getLanguageInfo("message.createSocietyShopWindow.unableBuy"));
                            return;
                        }
                        Society society = SocietyUtils.getSocietyByPlayerName(player.getName());
                        if (!(society.getSid() == sid)) {
                            player.sendMessage(PluginUtils.getLanguageInfo("message.createSocietyShopWindow.unableBuy1"));
                            return;
                        }

                        if (dissolve != null) {
                            player.sendMessage(PluginUtils.getLanguageInfo("message.createSocietyShopWindow.alreadDestroy"));
                            return;
                        }


                        if (!affrimBuyPlayer.contains(player.getName()) && !TitleUtils.onCreateName.containsKey(player.getName())) {
                            player.sendMessage(PluginUtils.getLanguageInfo("message.createSocietyShopWindow.affrimBuy"));
                            affrimBuyPlayer.add(player.getName());
                            return;
                        }

                        EconomyAPI.getInstance().reduceMoney(player, price);
                        EconomyAPI.getInstance().addMoney(creator, price);
                        String nbtDataStr = (String) value.get("nbt");
                        byte[] bytes = Binary.hexStringToBytes(nbtDataStr);
                        int count = (int) value.get("count");
                        String itemIDStr = (String) value.get("itemID-Meta");
                        String[] split = itemIDStr.split("-");
                        String itemID = split[0];
                        String meta = split[1];
                        player.getInventory().addItem(Item.get(Integer.parseInt(itemID), Integer.parseInt(meta), count, bytes));
                        block.onBreak(Item.get(0));
                        societyShopConfig.remove(key);
                        societyShopConfig.save();
                        player.sendMessage(PluginUtils.getLanguageInfo("message.createSocietyShopWindow.buySuccess"));
                    }
                }
            }
        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Config societyShopConfig = SocietyPlugin.getInstance().getSocietyShopConfig();
        for (Map.Entry<String, Object> entry : societyShopConfig.getAll().entrySet()) {
            String key = entry.getKey();
            HashMap<String, Object> value = (HashMap<String, Object>) entry.getValue();
            Object dissolve = value.get("dissolve");
            ArrayList<Object> itemData = new ArrayList<>();
            if (dissolve != null) {
                String creator = (String) value.get("creator");
                if (player.getName().equals(creator)) {

                    String itemID = (String) value.get("itemID-Meta");
                    itemID = itemID.replace("-", "负数");


                    itemData.add(itemID);
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
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        //检测到玩家已经创建过公会商店
        Config societyShopConfig = societyPlugin.getSocietyShopConfig();
        for (Map.Entry<String, Object> entry : societyShopConfig.getAll().entrySet()) {
            String key = entry.getKey();
            HashMap<String, Object> value = (HashMap<String, Object>) entry.getValue();
            String creator = (String) value.get("creator");
            Object dissolve = value.get("dissolve");
            ArrayList<Object> itemData = new ArrayList<>();
            if (player.getName().equals(creator) && dissolve != null) {
                String itemID = (String) value.get("itemID-Meta");
                itemID = itemID.replace("-", "负数");
                itemData.add(itemID);
                itemData.add(value.get("count"));
                itemData.add(value.get("nbt"));

                Item item = PluginUtils.parseItemByList(itemData);
                player.getInventory().addItem(item);
                player.sendMessage(PluginUtils.getLanguageInfo("message.createSocietyShopWindow.returnItem"));
                societyShopConfig.remove(key);
            }
        }
    }

    @EventHandler
    public void onBreakShop(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        int blockFloorX = block.getFloorX();
        int blockFloorY = block.getFloorY();
        int blockFloorZ = block.getFloorZ();
        Level level = player.getLevel();
        Config societyShopConfig = SocietyPlugin.getInstance().getSocietyShopConfig();
        for (Map.Entry<String, Object> entry : societyShopConfig.getAll().entrySet()) {
            String key = entry.getKey();
            HashMap<String, Object> value = (HashMap<String, Object>) entry.getValue();
            int x = (int) value.get("x");
            int y = (int) value.get("y");
            int z = (int) value.get("z");
            String levelName = (String) value.get("levelName");
            if (player.getLevel().getName().equals(levelName)) {
                Block signBlock = level.getBlock(new Vector3(x, y, z));
                boolean isX = (blockFloorX + 1 == x || blockFloorX - 1 == x || blockFloorX == x);
                boolean isZ = (blockFloorZ + 1 == z || blockFloorZ - 1 == z || blockFloorZ == z);
                if ((isX && isZ && !player.isOp())) {
                    event.setCancelled();
                    player.sendMessage(PluginUtils.getLanguageInfo("message.createSocietyShopWindow.destroyPermissions"));
                    return;
                }

                if (isX && isZ && player.isOp()) {
                    value.put("dissolve", true);
                    societyShopConfig.set(key, value);
                    societyShopConfig.save();
                    player.sendMessage(PluginUtils.getLanguageInfo("message.createSocietyShopWindow.destroy"));
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onQuitGame(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        affrimBuyPlayer.remove(player.getName());
    }

    @EventHandler
    public void onReceive(DataPacketReceiveEvent event) {
        DataPacket packet = event.getPacket();
        Player player = event.getPlayer();
        Society society = SocietyUtils.getSocietyByPlayerName(player.getName());
        if (SocietyUtils.isJoinSociety(player.getName())) {
            if (packet instanceof TextPacket) {
                if (((TextPacket) packet).type == TextPacket.TYPE_CHAT && society != null) {
                    if (SocietyUtils.societyChatPlayers.containsKey(player.getName())) {
                        HashMap<String, ArrayList<Object>> post = society.getPost();
                        for (String playerName : post.keySet()) {
                            Server server = SocietyPlugin.getInstance().getServer();
                            Player societyMember = server.getPlayer(playerName);

                            if (societyMember != null && SocietyUtils.societyChatPlayers.containsKey(societyMember.getName())) {
                                societyMember.sendMessage("§f[§e公会频道§f]§f[§9" + SocietyUtils.getPostByName(playerName, society) + "§f] §l§b" + playerName + "§r§f>> " + ((TextPacket) packet).message);
                                event.setCancelled();
                            }
                        }
                    }
                }
            }
        }
    }
}
