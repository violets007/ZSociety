package com.zixuan007.society.utils;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Binary;
import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Lang;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;
import me.onebone.economyapi.EconomyAPI;


import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * 插件工具类
 * @author zixuan007
 */
public class PluginUtils {
    /**
     * 系统文件分隔符
     */
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    /**
     * 公会数据文件路径
     */
    public static final String SOCIETY_FOLDER = SocietyPlugin.getInstance().getDataFolder().getAbsolutePath() + FILE_SEPARATOR + "Society" + FILE_SEPARATOR;

    /**
     * 公会配置文件夹
     */
    public static final String CONFIG_FOLDER = SocietyPlugin.getInstance().getDataFolder().getAbsolutePath() + FILE_SEPARATOR;
    public static final String MARRY_FOLDER =SocietyPlugin.getInstance().getDataFolder().getAbsolutePath()+FILE_SEPARATOR+"Marry"+FILE_SEPARATOR;
    public static final String PRIVILEGE_FOLDER =SocietyPlugin.getInstance().getDataFolder().getAbsolutePath()+FILE_SEPARATOR+"Vip"+FILE_SEPARATOR;

    public static SocietyPlugin society=SocietyPlugin.getInstance();
    /**
     * 加载jar包
     * @param jarPath
     * @throws MalformedURLException
     */
    public static void loadJar(String jarPath) throws MalformedURLException {
        File jarFile = new File(jarPath);
        if (!jarFile.exists()) {
            System.out.println("jar file not found.");
            return;
        }
        Method method = null;
        try {
            method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        } catch (NoSuchMethodException | SecurityException e1) {
            e1.printStackTrace();
        }
        boolean accessible = method.isAccessible();
        try {
            if (!accessible) {
                method.setAccessible(true);
            }
            URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            URL url = jarFile.toURI().toURL();
            method.invoke(classLoader, url);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.setAccessible(accessible);
        }
    }

    /**
     * 检测玩家名字是否在线
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
     * 通过反射加载配置文件
     */
    public static Lang getLang(){
        Lang lang = new Lang();//方便植入静态字段
        Field[] declaredFields = Lang.class.getDeclaredFields();
        Config langConfig = SocietyPlugin.getInstance().getLangConfig();
        for (Field declaredField : declaredFields) {
            String fieldName = declaredField.getName();
            String hintMessage = (String) langConfig.get(fieldName);
            if(hintMessage != null){
                try {
                    declaredField.set(lang,hintMessage);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    SocietyPlugin.getInstance().getLogger().info("植入语言文件提示信息出错");
                }
            }else{
                SocietyPlugin.getInstance().getLogger().info("语言配置文件信息更改有误");
                String language = (String) SocietyPlugin.getInstance().getConfig().get("language");
                String langPath = PluginUtils.CONFIG_FOLDER +language+"_language.yml";
                SocietyPlugin.getInstance().saveResource(langPath);
            }
        }
        return null;
    }

    /**
     * 格式化传入的文本,通过玩家
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
        String postName = SocietyUtils.getPostByName(player.getName(),society);
        String title = TitleUtils.getTitles(player.getName()).size()<=0?"无称号":TitleUtils.getTitles(player.getName()).get(0);
        String privilege="";
        if(PrivilegeUtils.isVIP(player.getName()) || PrivilegeUtils.isSvip(player.getName())) {
            privilege=PrivilegeUtils.isVIP(player.getName()) ? "§l§eVIP§f+":"§l§cS§aV§l§bIP§f+";
        }
        String marry= MarryUtils.isMarry(player.getName())?"已结婚":"单身";
        String name = player.getLevel().getName();
        float ticksPerSecond = SocietyPlugin.getInstance().getServer().getTicksPerSecond();
        text = text.replaceAll("\\$\\{world\\}", name)
                .replaceAll("\\$\\{societyName\\}", societyNam)
                .replaceAll("\\$\\{societyGrade\\}", societyGrade)
                .replaceAll("\\$\\{playerName\\}", player.getName())
                .replaceAll("\\$\\{post\\}", postName)
                .replaceAll("\\$\\{tps\\}", ticksPerSecond + "")
                .replaceAll("\\$\\{money\\}", myMoney.toString())
                .replaceAll("\\$\\{itemID\\}", itemID)
                .replaceAll("\\$\\{title\\}", title)
                .replaceAll("\\$\\{zmarry\\}", marry)
                .replaceAll("\\$\\{privilege\\}", privilege);
        boolean allowFlight = player.getAllowFlight();
        text=text.replace("${flight}",allowFlight?"§c关闭":"§a开启§r")
                .replace("${create}",player.isCreative()?"§c关闭":"§a开启")
                .replace("${playername}",player.getName());

        Calendar now = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
        now.setTimeZone(timeZone);
        now.setTime(new Date());
        text = text.replace("{年}", now.get(1) + "");
        text = text.replace("{name}", player.getName());
        text = text.replace("{月}", now.get(2) + 1 + "");
        text = text.replace("{日}", now.get(5) + "");
        text = text.replace("{时}", now.get(11) + "");
        text = text.replace("{分}", now.get(12) + "");
        text = text.replace("{秒}", now.get(13) + "");
        text = text.replace("{星期}", now.get(4) + "");
        text = text.replace("{ms}", player.getPing() + "ms");
        text = text.replace("{levelName}", player.getLevel().getFolderName());
        text = text.replace("{x}", Math.round(player.getX()) + "");
        text = text.replace("{y}", Math.round(player.getY()) + "");
        text = text.replace("{z}", Math.round(player.getZ()) + "");
        text = text.replace("{tps}", Server.getInstance().getTicksPerSecond() + "");
        text = text.replace("{money}", String.format("%.2f", EconomyAPI.getInstance().myMoney(player)));
        text=text.replace("{online}", Server.getInstance().getOnlinePlayers().size() + "")
                .replace("{maxplayer}", Server.getInstance().getMaxPlayers() + "");
        text=text.replace("{h}", player.getHealth() + "").replace("{mh}", player.getMaxHealth() + "");
        return text;
    }

    /**
     * 获取求婚所需要的金额
     * @return
     */
    public static Double getProposeMoney(){
        Object proposeMoneyOBJ = SocietyPlugin.getInstance().getConfig().get("proposeMoney");
        if(proposeMoneyOBJ instanceof Integer){
            return ((Integer) proposeMoneyOBJ).doubleValue();
        }else{
            return (Double)proposeMoneyOBJ;
        }
    }

    /**
     * 解析Item根据List
     * @param list
     * @return
     */
    public static Item parseItemByList(List<Object> list){
        if(list == null) {
            return null;
        }
        String itemIDMetaStr = (String) list.get(0);
        String[] split = itemIDMetaStr.split("-");
        int itemID = Integer.parseInt(split[0]);
        int meta = Integer.parseInt(split[1]);
        int count = (int) list.get(1);
        String nbtHexString = (String) list.get(2);
        byte[] bytes = Binary.hexStringToBytes(nbtHexString);
        return Item.get(itemID,meta,count,bytes);
    }

    /**
     * 注册表单类型
     * @param windowType
     * @param clazz
     */
    public static void addWindowClass(WindowType windowType,Class clazz){
        HashMap<WindowType, Class> registerWindow = WindowManager.getRegisterWindow();
        if(!registerWindow.containsKey(windowType)){
            registerWindow.put(windowType,clazz);
            SocietyPlugin.getInstance().getLogger().debug("成功注册窗口 "+windowType.windowName+" 绑定的Class "+clazz.getPackage().getName()+"."+clazz.getSimpleName());
        }
    }

    public static String getLanguageInfo(String key){
       return getLanguageInfo(null,key);
    }

    public static String getLanguageInfo(Player player,String key){
        if(player != null){
            String languageInfo = society.getLanguageConfig().getString(key, key);
            return formatText(languageInfo, player);
        }
        return society.getLanguageConfig().getString(key,key);
    }

    public static String getWindowConfigInfo(String key){
        return getWindowConfigInfo(null,key);
    }

    public static String getWindowConfigInfo(Player player,String key){
        if(player != null){
            String windowInfo = society.getWindowConfig().getString(key, key);
            return formatText(windowInfo,player);
        }
        return society.getWindowConfig().getString(key,key);
    }
}