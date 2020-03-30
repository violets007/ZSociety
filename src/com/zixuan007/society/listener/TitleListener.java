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
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.Config;
import com.zixuan007.api.FloatingTextAPI;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.event.title.BuyTitleEvent;
import com.zixuan007.society.event.title.CreateTitleShopEvent;
import com.zixuan007.society.event.title.RemoveTitleShopEvent;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.utils.TitleUtils;
import java.util.ArrayList;
import java.util.Map;
import me.onebone.economyapi.EconomyAPI;

public class TitleListener implements Listener {
    private SocietyPlugin societyPlugin;
    private ArrayList<String> affirmBuyTitlePlayer = new ArrayList<>();

    public TitleListener(SocietyPlugin societyPlugin) {
        this.societyPlugin = societyPlugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Config titleConfig = this.societyPlugin.getTitleConfig();
        String title = (String) titleConfig.get(player.getName());
        if (title == null) {
            titleConfig.set(player.getName(), "无称号");
            titleConfig.save();
        }
        Config config = this.societyPlugin.getConfig();
        if (config.getBoolean("是否开启更改头部", false)) {
            String configNameTag = (String) config.get("头部更改");
            String formatNameTag = SocietyUtils.formatText(configNameTag, player);
            player.setNameTag(formatNameTag);
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
            if (id == 68) {
                if (blockEntity instanceof BlockEntitySign) {
                    ArrayList<String> titleText = (ArrayList<String>) this.societyPlugin.getConfig().getList("称号商店木牌格式");
                    ArrayList<String> tempList = new ArrayList<>();
                    String title = (String) TitleUtils.onCreateName.get(playerName).get("title");
                    String money = (String) TitleUtils.onCreateName.get(playerName).get("money");
                    tempList.addAll(titleText);
                    tempList.set(1, ((String) titleText.get(1)).replaceAll("\\$\\{title\\}", title));
                    tempList.set(2, ((String) titleText.get(2)).replaceAll("\\$\\{money\\}", money));
                    ((BlockEntitySign) blockEntity).setText(tempList.<String>toArray(new String[tempList.size()]));
                    SocietyPlugin.getInstance().getServer().getPluginManager().callEvent((Event) new CreateTitleShopEvent(player, (BlockEntitySign) blockEntity));
                }
            } else {
                player.sendMessage(">> §c请点击贴在墙上的木牌");
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onClickTitleShop(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        int id = block.getId();
        int x = block.getFloorX();
        int y = block.getFloorY();
        int z = block.getFloorZ();
        String levelName = block.getLevel().getName();
        Config titleShopConfig = SocietyPlugin.getInstance().getTitleShopConfig();
        if (id == 68) {
            for (Map.Entry<String, Object> map : (Iterable<Map.Entry<String, Object>>) titleShopConfig.getAll().entrySet()) {
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
                            player.sendMessage(">> §c余额不足无法购买");
                            return;
                        }
                        EconomyAPI.getInstance().reduceMoney(player, money);
                        SocietyPlugin.getInstance().getServer().getPluginManager().callEvent(new BuyTitleEvent(player, key, money));
                        continue;
                    }
                    this.affirmBuyTitlePlayer.add(player.getName());
                    player.sendMessage(">> §c请再次点击一次木牌");
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBuyTitle(BuyTitleEvent event) {
        Player player = event.getPlayer();
        String title = event.getTitle();
        Config titleConfig = this.societyPlugin.getTitleConfig();
        titleConfig.set(player.getName(), title);
        titleConfig.save();
        this.affirmBuyTitlePlayer.remove(player.getName());
        player.sendMessage(">> §a成功购买 §b" + title + " §a称号");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCreateTitleShop(CreateTitleShopEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        BlockEntitySign wallSign = event.getWallSign();
        double x = wallSign.getFloorX();
        double y = wallSign.getY() + 1.0D;
        double z = wallSign.getFloorZ();
        int damage = wallSign.getBlock().getDamage();
        if (damage % 5 == 0) {
            z += 0.5D;
        } else if (damage % 5 == 1) {
            z += 0.5D;
        }
        if (damage % 5 == 2) {
            z += 0.6D;
            x += 0.5D;
        } else if (damage % 5 == 3) {
            x += 0.5D;
        } else if (damage % 5 == 4) {
            x += 0.6D;
            z += 0.5D;
        }
        Config titleShopConfig = this.societyPlugin.getTitleShopConfig();
        String title = (String) TitleUtils.onCreateName.get(playerName).get("title");
        String money = (String) TitleUtils.onCreateName.get(playerName).get("money");
        ArrayList<Object> list = new ArrayList<Object>();
        list.add(wallSign.getFloorX());
        list.add(wallSign.getFloorY());
        list.add(wallSign.getFloorZ());
        list.add(wallSign.getLevel().getName());
        list.add(money);
        titleShopConfig.set(title, list);
        titleShopConfig.save();
        StringBuilder sb = new StringBuilder();
        for (String line : wallSign.getText()) {
            sb.append(line + "\n");
        }
        String key = wallSign.getText()[1];
        FloatingTextAPI.addFloatingText(sb.toString(), new Position(x, y, z, player.getLevel()), key, player.getName());
        player.sendMessage(">> §a称号商店创建成功");
        TitleUtils.onCreateName.remove(player.getName());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        String playerName = event.getPlayer().getName();
        TitleUtils.onCreateName.remove(playerName);
        this.affirmBuyTitlePlayer.remove(playerName);
    }

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
            System.out.println(levelName.equals(titleShopLevelName));
            boolean isX = (x + 1 == titleShopX || x - 1 == titleShopX || x == titleShopX);
            boolean isZ = (z + 1 == titleShopZ || z - 1 == titleShopZ || z == titleShopZ);
            if (isX && isZ && levelName.equals(titleShopLevelName)) {
                System.out.println("执行进入");
                if (!player.isOp()) {
                    player.sendMessage(">> §c你没有权限移除称号商店");
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
        ArrayList<String> textList = (ArrayList) config.getList("称号商店木牌格式");
        String signTitle = textList.get(1);
        System.out.println(signTitle);
        signTitle = signTitle.replaceAll("\\$\\{title\\}", event.getTitle());
        FloatingTextAPI.removeFloatingText(player, signTitle);
        player.sendMessage(">> §a移除商店成功");
    }

}