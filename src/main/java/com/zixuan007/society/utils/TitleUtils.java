package com.zixuan007.society.utils;

import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;

import java.util.*;

/**
 * 称号工具类
 */
public class TitleUtils {

    public static final HashMap<String, HashMap<String, Object>> onCreateName = new HashMap(); //正在创建商店的玩家
    public static final HashMap<String, ArrayList<String>> titleList = new HashMap<String, ArrayList<String>>();


    /**
     * 加载配置文件
     */
    public static void loadConfig(){
        Config titleConfig = SocietyPlugin.getInstance().getTitleConfig();
        if(titleConfig.getAll().size() > 0){
            for (String key : titleConfig.getKeys()) {
                if (!(titleConfig.get(key) instanceof List)) {
                    SocietyPlugin.getInstance().saveResource("Title.yml",true);
                    SocietyPlugin.getInstance().getLogger().info("§c检测到Title版本太低进行覆盖!");
                    break;
                }
            }
        }
        String titleConfigPath = PluginUtils.CONFIG_FOLDER + "Title.yml";
        SocietyPlugin.getInstance().setTitleConfig(new Config(titleConfigPath,Config.YAML));

        titleConfig.getAll().forEach((key,list)->{
            ArrayList<String> titles= (ArrayList<String>) list;
            titleList.put(key,titles);
        });

        SocietyPlugin.getInstance().getLogger().debug("称号数据列表: "+titleList.toString());
    }


    /**
     * 检测指定称号是否存在
     * @param title 称号
     * @param playerName 玩家名称
     * @return
     */
    public static boolean isExistTitle(String playerName,String title){
        ArrayList<String> arrayList = titleList.get(playerName);
        if(arrayList != null && arrayList.size() > 0){
            for (String existTitle : arrayList) {
                if (existTitle.equals(title)) return true;
            }
        }
        return false;
    }

    /**
     * 添加称号
     * @param playerName 玩家名称
     * @param title 称号
     */
    public static void addTitle(String playerName,String title){
        ArrayList<String> arrayList = titleList.get(playerName);
        if (arrayList == null) {
            arrayList=new ArrayList<>();
        }
        arrayList.add(0,title);
        titleList.put(playerName,arrayList);

        SocietyPlugin.getInstance().getTitleConfig().set(playerName, TitleUtils.getTitles(playerName));
        SocietyPlugin.getInstance().getTitleConfig().save();
    }

    /**
     * 获取指定玩家的所有称号
     * @param playerName 玩家名称
     * @return
     */
    public static ArrayList<String> getTitles(String playerName){
        ArrayList<String> arrayList = titleList.get(playerName);
        return arrayList == null?new ArrayList<String>():arrayList;
    }


    /**
     * 保存制定玩家的称号数据
     * @param playerName
     * @param titles
     */
    public static void saveTitle(String playerName,ArrayList<String> titles){
        if (titleList.containsKey(playerName)) {
            titleList.put(playerName, titles);
        }

        Config titleConfig = SocietyPlugin.getInstance().getTitleConfig();

        for (Map.Entry<String, ArrayList<String>> entry : titleList.entrySet()) {
            String holdTitlePlayer = entry.getKey();
            ArrayList<String> playerTitles = entry.getValue();
            titleConfig.set(holdTitlePlayer,playerTitles);
        }

        titleConfig.save();
    }

}