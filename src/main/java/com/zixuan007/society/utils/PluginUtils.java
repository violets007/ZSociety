package com.zixuan007.society.utils;

import cn.nukkit.AdventureSettings;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Binary;
import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;
import me.onebone.economyapi.EconomyAPI;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * 插件工具类
 *
 * @author zixuan007
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


    public static String generateNumber() {
        String no = "";
        //初始化备选数组
        int[] defaultNums = new int[10];
        for (int i = 0; i < defaultNums.length; i++) {
            defaultNums[i] = i;
        }

        Random random = new Random();
        int[] nums = new int[LENGTH];
        //默认数组中可以选择的部分长度
        int canBeUsed = 10;
        //填充目标数组
        for (int i = 0; i < nums.length; i++) {
        //将随机选取的数字存入目标数组
            int index = random.nextInt(canBeUsed);
            nums[i] = defaultNums[index];
            //将已用过的数字扔到备选数组最后，并减小可选区域
            swap(index, canBeUsed - 1, defaultNums);
            canBeUsed--;
        }
        if (nums.length > 0) {
            for (int i = 0; i < nums.length; i++) {
                no += nums[i];
            }
        }

        return no;
    }


    private static void swap(int i, int j, int[] nums) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 检测玩家名字是否在线
     *
     * @param playerName
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
     * 格式化传入的文本,通过玩家
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
    public static Double getProposeMoney() {
        Object proposeMoneyOBJ = SocietyPlugin.getInstance().getConfig().get("proposeMoney");
        if (proposeMoneyOBJ instanceof Integer) {
            return ((Integer) proposeMoneyOBJ).doubleValue();
        } else {
            return (Double) proposeMoneyOBJ;
        }
    }

    /**
     * 解析Item根据List
     *
     * @param list
     * @return
     */
    public static Item parseItemByList(List<Object> list) {
        if (list == null) {
            return null;
        }
        String itemIDMetaStr = (String) list.get(0);
        String[] split = itemIDMetaStr.split("-");
        int itemID = Integer.parseInt(split[0]);
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
     * 检测配置文件
     *
     * @param pluginPath
     * @param path
     * @return
     */
    public static File checkConfig(String pluginPath, String path) {
        File file = new File(path);
        if (file.exists()) {
            Config config = new Config(file, Config.YAML);
            String version = (String) config.get("version");
            if (version == null || !version.equals(societyPlugin.getDescription().getVersion())) {
                societyPlugin.saveResource(pluginPath, true);
                societyPlugin.getLogger().info("检测到: " + path + " 文件版本过低,进行覆盖!");
            }
        } else {
            societyPlugin.saveResource(pluginPath, true);
        }
        return file;
    }


}