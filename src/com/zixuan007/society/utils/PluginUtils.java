package com.zixuan007.society.utils;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Lang;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 插件工具类
 */
public class PluginUtils {
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");//系统分隔符 Linux \  Window /
    public static final String SOCIETYFOLDER = SocietyPlugin.getInstance().getDataFolder().getAbsolutePath() + FILE_SEPARATOR + "Society" + FILE_SEPARATOR; //公会数据文件路径
    public static final String CONFIGFOLDER = SocietyPlugin.getInstance().getDataFolder().getAbsolutePath() + FILE_SEPARATOR;//公会配置文件夹
    public static final String MARRY_FOLDER =SocietyPlugin.getInstance().getDataFolder().getAbsolutePath()+FILE_SEPARATOR+"Marry"+FILE_SEPARATOR;
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
            if (player.getName().equals(playerName)) return true;
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
                String langPath = PluginUtils.CONFIGFOLDER +language+"_language.yml";
                SocietyPlugin.getInstance().saveResource(langPath);
            }
        }
        return null;
    }
}