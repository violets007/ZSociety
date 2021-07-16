package com.zixuan007.society.utils;

import cn.nukkit.AdventureSettings;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Binary;
import cn.nukkit.utils.Config;
import com.google.gson.Gson;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.pojo.Society;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;
import me.onebone.economyapi.EconomyAPI;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zixuan007
 * @description: 插件工具类
 * @date: 2021/7/14 下午2:56
 */
public class PluginUtils {

    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String SOCIETY_FOLDER = SocietyPlugin.getInstance().getDataFolder().getAbsolutePath() + FILE_SEPARATOR + "Society" + FILE_SEPARATOR;
    public static final String CONFIG_FOLDER = SocietyPlugin.getInstance().getDataFolder().getAbsolutePath() + FILE_SEPARATOR;
    public static final String MARRY_FOLDER = SocietyPlugin.getInstance().getDataFolder().getAbsolutePath() + FILE_SEPARATOR + "Marry" + FILE_SEPARATOR;
    public static final String PRIVILEGE_FOLDER = SocietyPlugin.getInstance().getDataFolder().getAbsolutePath() + FILE_SEPARATOR + "Vip" + FILE_SEPARATOR;
    public static final String WAR_FOLDER = SocietyPlugin.getInstance().getDataFolder().getAbsolutePath() + FILE_SEPARATOR + "war" + FILE_SEPARATOR;

    private static final int LENGTH = 8;
    public static SocietyPlugin societyPlugin = SocietyPlugin.getInstance();


    private static void swap(int i, int j, int[] nums) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 检测玩家名字是否在线
     *
     * @param playerName 玩家名字
     * @return
     */
    public static boolean isOnlineByName(String playerName) {
        for (Player player : Server.getInstance().getOnlinePlayers().values()) {
            if (player.getName().equals(playerName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 玩家Tip
     *
     * @param text
     * @param player
     * @return
     */
    public static String formatText(String text, Player player) {
        Item itemInHand = player.getInventory().getItemInHand();
        String itemID = itemInHand.getId() + ":" + itemInHand.getDamage();
        Double myMoney = EconomyAPI.getInstance().myMoney(player);
        Society society = SocietyUtils.getSocietyByPlayerName(player.getName());
        String societyNam = society != null ? "§9" + society.getSocietyName() : "无公会";
        String societyGrade = society != null ? society.getGrade() + "" : "无等级";
        String postName = SocietyUtils.getPostByName(player.getName(), society);
        String title = TitleUtils.getTitles(player.getName()).size() <= 0 ? "无称号" : TitleUtils.getTitles(player.getName()).get(0);
        HashMap<String, String> societyChatPlayers = SocietyUtils.societyChatPlayers;
        String societyChat = societyChatPlayers.containsKey(player.getName()) ? "§a开启" : "§c关闭";
        String privilege = "";
        if (PrivilegeUtils.isVIP(player.getName()) || PrivilegeUtils.isSvip(player.getName())) {
            privilege = PrivilegeUtils.isVIP(player.getName()) ? "§l§eVIP§f+" : "§l§cS§aV§l§bIP§f+";
        }

        String marry = MarryUtils.isMarry(player.getName()) ? "已结婚" : "单身";
        String name = player.getLevel().getName();
        boolean allowFlight = player.getAdventureSettings().get(AdventureSettings.Type.ALLOW_FLIGHT);
        float ticksPerSecond = SocietyPlugin.getInstance().getServer().getTicksPerSecond();

        Calendar now = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
        now.setTimeZone(timeZone);
        now.setTime(new Date());

        text = text.replace("${world}", name)
                .replace("${societyName}", societyNam)
                .replace("${societyGrade}", societyGrade)
                .replace("${societyChat}", societyChat)
                .replace("${playerName}", player.getName())
                .replace("${post}", postName)
                .replace("${tps}", ticksPerSecond + "")
                .replace("${money}", myMoney.toString())
                .replace("${itemID}", itemID)
                .replace("${title}", title)
                .replace("${marry}", marry)
                .replace("${privilege}", privilege)
                .replace("${flight}", allowFlight ? "§c关闭" : "§a开启§r")
                .replace("${create}", player.isCreative() ? "§c关闭" : "§a开启")
                .replace("${年}", now.get(1) + "")
                .replace("${name}", player.getName())
                .replace("${月}", now.get(2) + 1 + "")
                .replace("${日}", now.get(5) + "")
                .replace("${时}", now.get(11) + "")
                .replace("${分}", now.get(12) + "")
                .replace("${秒}", now.get(13) + "")
                .replace("${星期}", now.get(4) + "")
                .replace("${ms}", player.getPing() + "ms")
                .replace("${levelName}", player.getLevel().getFolderName())
                .replace("${x}", Math.round(player.getX()) + "")
                .replace("${y}", Math.round(player.getY()) + "")
                .replace("${z}", Math.round(player.getZ()) + "")
                .replace("${tps}", Server.getInstance().getTicksPerSecond() + "")
                .replace("${money}", String.format("%.2f", EconomyAPI.getInstance().myMoney(player)))
                .replace("${online}", Server.getInstance().getOnlinePlayers().size() + "")
                .replace("${maxplayer}", Server.getInstance().getMaxPlayers() + "")
                .replace("${h}", player.getHealth() + "").replace("{mh}", player.getMaxHealth() + "");
        return text;
    }


    /**
     * 获取求婚所需要的金额
     *
     * @return
     */
    public static Integer getProposeMoney() {
        Object proposeMoneyOBJ = SocietyPlugin.getInstance().getConfig().get("求婚金额");
        return ((Integer) proposeMoneyOBJ);
    }

    /**
     * 解析Item根据List
     *
     * @param list
     * @return
     */
    public static Item parseItemByList(List<Object> list) {
        if (list == null && list.size() < 1) {
            return null;
        }
        String itemIDMetaStr = (String) list.get(0);
        String[] split = itemIDMetaStr.split("-");
        int itemID = Integer.parseInt(split[0].replace("负数", "-"));
        int meta = Integer.parseInt(split[1]);
        int count = (int) list.get(1);
        String nbtHexString = (String) list.get(2);
        byte[] bytes = Binary.hexStringToBytes(nbtHexString);

        return Item.get(itemID, meta, count, bytes);
    }

    /**
     * 注册表单类型
     *
     * @param windowType
     * @param clazz
     */
    public static void addWindowClass(WindowType windowType, Class clazz) {
        HashMap<WindowType, Class> registerWindow = WindowManager.getRegisterWindow();
        if (!registerWindow.containsKey(windowType)) {
            registerWindow.put(windowType, clazz);
            SocietyPlugin.getInstance().getLogger().debug("成功注册窗口 " + windowType.windowName + " 绑定的Class " + clazz.getPackage().getName() + "." + clazz.getSimpleName());
        }
    }

    public static String getLanguageInfo(String key) {
        return getLanguageInfo(null, key);
    }

    public static String getLanguageInfo(String key, String[] parameterName, String[] parameterVal) {
        String languageInfo = getLanguageInfo(key);
        if (parameterName != null && parameterName.length >= 1) {
            for (int i = 0; i < parameterName.length; i++) {
                languageInfo = languageInfo.replace(parameterName[i], parameterVal[i]);
            }
        }
        return languageInfo;
    }

    public static String getLanguageInfo(Player player, String key) {
        if (player != null) {
            String languageInfo = societyPlugin.getLanguageConfig().getString(key, key);
            return formatText(languageInfo, player);
        }
        return societyPlugin.getLanguageConfig().getString(key, key);
    }

    public static String getWindowConfigInfo(String key) {
        return getWindowConfigInfo(null, key);
    }

    public static String getWindowConfigInfo(Player player, String key) {
        if (player != null) {
            String windowInfo = societyPlugin.getWindowConfig().getString(key, key);
            return formatText(windowInfo, player);
        }
        return societyPlugin.getWindowConfig().getString(key, key);
    }

    /**
     * 检测插件配置版本
     *
     * @param pluginPath
     * @param path
     * @return
     */
    public static File checkConfig(String pluginPath, String path) {
        File file = new File(path);
        if (file.exists()) {
            Config config = new Config(file, Config.YAML);
            String version = (String) config.get("版本");
            if (version == null || !version.equals(societyPlugin.getDescription().getVersion())) {
                societyPlugin.saveResource(pluginPath, true);
                societyPlugin.getLogger().info("检测到: " + path + " 文件版本过低,进行覆盖!");
            }
        } else {
            societyPlugin.saveResource(pluginPath, true);
        }
        return file;
    }

    /**
     * @param nowDate   要比较的时间
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return true在时间段内，false不在时间段内
     * @throws Exception
     */
    public static boolean hourMinuteBetween(String nowDate, String startDate, String endDate) throws Exception {

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

        Date now = format.parse(nowDate);
        Date start = format.parse(startDate);
        Date end = format.parse(endDate);

        long nowTime = now.getTime();
        long startTime = start.getTime();
        long endTime = end.getTime();

        return nowTime >= startTime && nowTime <= endTime;
    }

}
