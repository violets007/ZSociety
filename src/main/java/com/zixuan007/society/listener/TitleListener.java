package com.zixuan007.society.listener;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.event.Event;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.event.title.BuyTitleEvent;
import com.zixuan007.society.event.title.CreateTitleShopEvent;
import com.zixuan007.society.event.title.RemoveTitleShopEvent;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.utils.TitleUtils;

import java.util.ArrayList;
import java.util.Map;

import me.onebone.economyapi.EconomyAPI;

/**
 * @author zixuan007
 */
public class TitleListener implements Listener {

    private final SocietyPlugin societyPlugin;
    private final ArrayList<String> affirmBuyTitlePlayer = new ArrayList<>();

    public TitleListener(SocietyPlugin societyPlugin) {
        this.societyPlugin = societyPlugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Config titleConfig = this.societyPlugin.getTitleConfig();
        ArrayList<String> title = (ArrayList<String>) titleConfig.getStringList(player.getName());
        if (title == null) {
            titleConfig.set(player.getName(), new ArrayList<String>());
            titleConfig.save();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onSetTitleShop(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        Level level = player.getLevel();
        Block block = event.getBlock();
        int id = block.getId();
        BlockEntity blockEntity = level.getBlockEntity(new Vector3(block.getX(), block.getY(), block.getZ()));
        if (TitleUtils.onCreateName.containsKey(playerName)) {
            if (id == Block.WALL_SIGN) {
                if (blockEntity instanceof BlockEntitySign) {
                    if (SocietyUtils.isSetShop(block)) {
                        player.sendMessage(PluginUtils.getLanguageInfo("message.createSocietyShopWindow.alreadSetShop"));
                        return;
                    }
                    ArrayList<String> titleText = (ArrayList<String>) this.societyPlugin.getConfig().getList("titleShopFormat");
                    ArrayList<String> tempList = new ArrayList<>();
                    String title = (String) TitleUtils.onCreateName.get(playerName).get("title");
                    String money = (String) TitleUtils.onCreateName.get(playerName).get("money");
                    tempList.addAll(titleText);
                    tempList.set(1, (titleText.get(1)).replaceAll("\\$\\{title\\}", title));
                    tempList.set(2, (titleText.get(2)).replaceAll("\\$\\{money\\}", money));
                    ((BlockEntitySign) blockEntity).setText(tempList.<String>toArray(new String[tempList.size()]));
                    SocietyPlugin.getInstance().getServer().getPluginManager().callEvent((Event) new CreateTitleShopEvent(player, (BlockEntitySign) blockEntity));
                    event.setCancelled(true);
                }
            } else {

                player.sendMessage(PluginUtils.getLanguageInfo("message.clickWallSign"));
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClickTitleShop(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        int id = block.getId();
        int x = block.getFloorX();
        int y = block.getFloorY();
        int z = block.getFloorZ();
        String levelName = block.getLevel().getName();
        Config titleShopConfig = SocietyPlugin.getInstance().getTitleShopConfig();
        if (id == Block.WALL_SIGN) {
            for (Map.Entry<String, Object> map : titleShopConfig.getAll().entrySet()) {
                String key = map.getKey();
                ArrayList<Object> list = (ArrayList<Object>) map.getValue();
                int signX = (Integer) list.get(0);
                int signY = (Integer) list.get(1);
                int signZ = (Integer) list.get(2);
                String signLevelName = (String) list.get(3);
                String str_Money = (String) list.get(4);
                int money = Integer.parseInt(str_Money);
                if (x == signX && y == signY && z == signZ && levelName.equals(signLevelName)) {

                    if (this.affirmBuyTitlePlayer.contains(player.getName())) {
                        if (EconomyAPI.getInstance().myMoney(player) < money) {
                            player.sendMessage(PluginUtils.getLanguageInfo("message.buyTitle.rarelyCoin"));
                            return;
                        }

                        if (TitleUtils.isExistTitle(player.getName(), key)) {
                            player.sendMessage(PluginUtils.getLanguageInfo("message.buyTitle.existTitle"));
                            return;
                        }


                        EconomyAPI.getInstance().reduceMoney(player, money);
                        SocietyPlugin.getInstance().getServer().getPluginManager().callEvent(new BuyTitleEvent(player, key, money));
                        return;
                    }

                    if (!this.affirmBuyTitlePlayer.contains(player.getName()) && !SocietyUtils.onCreatePlayer.containsKey(player.getName()) && !TitleUtils.onCreateName.containsKey(player.getName())) {
                        this.affirmBuyTitlePlayer.add(player.getName());
                        player.sendMessage(PluginUtils.getLanguageInfo("message.buyTitle.affrimBuy"));
                    }

                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBuyTitle(BuyTitleEvent event) {
        Player player = event.getPlayer();
        String title = event.getTitle();
        Config titleConfig = this.societyPlugin.getTitleConfig();
        TitleUtils.addTitle(player.getName(), title);
        titleConfig.set(player.getName(), TitleUtils.titleList.get(player.getName()));
        titleConfig.save();
        this.affirmBuyTitlePlayer.remove(player.getName());
        player.sendMessage(PluginUtils.getLanguageInfo("message.buyTitle.success", new String[]{"${title}"}, new String[]{title}));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCreateTitleShop(CreateTitleShopEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        BlockEntitySign wallSign = event.getWallSign();

        Config titleShopConfig = this.societyPlugin.getTitleShopConfig();
        String title = (String) TitleUtils.onCreateName.get(playerName).get("title");
        String money = (String) TitleUtils.onCreateName.get(playerName).get("money");
        ArrayList<Object> list = new ArrayList<Object>();
        list.add(wallSign.getFloorX());
        list.add(wallSign.getFloorY());
        list.add(wallSign.getFloorZ());
        list.add(wallSign.getLevel().getName());
        list.add(money);

        SocietyPlugin.getInstance().getLogger().debug("创建称号商店的内容: " + list);
        titleShopConfig.set(title, list);
        titleShopConfig.save();
        StringBuilder sb = new StringBuilder();
        for (String line : wallSign.getText()) {
            sb.append(line + "\n");
        }

        player.sendMessage(PluginUtils.getLanguageInfo("message.createTitleShop.success"));
        TitleUtils.onCreateName.remove(player.getName());
    }

    /**
     * 玩家退出游戏
     *
     * @param event
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        String playerName = event.getPlayer().getName();
        TitleUtils.onCreateName.remove(playerName);
        this.affirmBuyTitlePlayer.remove(playerName);
    }

    /**
     * 移除称号商店方块
     *
     * @param event
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        int x = block.getFloorX();
        int y = block.getFloorY();
        int z = block.getFloorZ();
        String levelName = block.getLevel().getName();
        Config titleShopConfig = SocietyPlugin.getInstance().getTitleShopConfig();
        int id = block.getId();
        titleShopConfig.getAll().forEach((title, list) -> {
            ArrayList<Object> titleShopDataList = (ArrayList<Object>) list;
            int titleShopX = (Integer) titleShopDataList.get(0);
            int titleShopY = (Integer) titleShopDataList.get(1);
            int titleShopZ = (Integer) titleShopDataList.get(2);
            String titleShopLevelName = (String) titleShopDataList.get(3);
            boolean isX = (x + 1 == titleShopX || x - 1 == titleShopX || x == titleShopX);
            boolean isZ = (z + 1 == titleShopZ || z - 1 == titleShopZ || z == titleShopZ);
            if (isX && isZ && levelName.equals(titleShopLevelName)) {
                if (!player.isOp()) {
                    player.sendMessage(PluginUtils.getLanguageInfo("message.createTitleShop.permissions"));
                    event.setCancelled();
                } else {
                    SocietyPlugin.getInstance().getServer().getPluginManager().callEvent(new RemoveTitleShopEvent(player, block, title));
                }
            }
        });
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRemoveTitleShop(RemoveTitleShopEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Config titleShopConfig = this.societyPlugin.getTitleShopConfig();
        titleShopConfig.remove(event.getTitle());
        titleShopConfig.save();
        Config config = SocietyPlugin.getInstance().getConfig();
        ArrayList<String> textList = (ArrayList) config.getList("titleShopFormat");
        String signTitle = textList.get(1);
        signTitle = signTitle.replaceAll("\\$\\{title\\}", event.getTitle());
        player.sendMessage(PluginUtils.getLanguageInfo("message.createTitleShop.remove"));
    }

}