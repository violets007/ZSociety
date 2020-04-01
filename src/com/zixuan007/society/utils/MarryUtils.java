package com.zixuan007.society.utils;

import cn.nukkit.utils.Config;
import com.zixuan007.society.domain.Marry;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 结婚工具类
 */
public class MarryUtils{
    public static ArrayList<Marry> marrys=new ArrayList<Marry>();
    public static HashMap<String,String> proposeFailName = new HashMap<String, String>(); //求婚失败玩家
    /**
     * 检测指定玩家是否结婚
     * @param playerName
     * @return
     */
    public static boolean isMarry(String playerName){
        for(Marry marry:marrys){
            if(marry.getRecipient().equals(playerName) || marry.getPropose().equals(playerName)){
                return true;
            }
        }
        return false;
    }

    /**
     * 获取结婚实体类
     * @param mid
     * @return
     */
    public static Marry getMarryByID(long mid){
        for(Marry marry:marrys){
            if(marry.getMid() == mid){
                return marry;
            }
        }
        return null;
    }

    /**
     * 获取结婚实体类通过玩家名称
     * @param playerName
     * @return
     */
    public static Marry getMarryByName(String playerName){
        for(Marry marry:marrys){
            if(marry.getPropose().equals(playerName) || marry.getRecipient().equals(playerName)){
                return marry;
            }
        }
        return null;
    }

    /**
     * 获取指定玩家的配置文件信息
     * @return
     */
    public static Config getMarryConfigByName(String playerName){
        Marry marry=getMarryByName(playerName);
        if(marry != null)
            return getMarryConfigByMID(marry.getMid());
        return null;
    }

    /**
     * 获取结婚配置信息文件通过ID
     * @param mid 结婚ID
     * @return
     */
    public static Config getMarryConfigByMID(long mid){
        Marry marry= getMarryByID(mid);
        if(marry == null) return null;
        String configName=marry.getPropose()+"_"+marry.getRecipient()+".yml";
        Config config=new Config(configName);
        return config;
    }

    /**
     * 加载结婚数据文件
     */
    public static void loadMarryConfig(){
        String marryFolder = PluginUtils.MARRY_FOLDER;
        File file = new File(marryFolder);
        if(!file.exists()) file.mkdirs();
        File[] files = file.listFiles();
        for (File marryFile : files) {
            Config config=new Config(marryFile);
            Marry marry = Marry.init(config);
            marrys.add(marry);
        }
        System.out.println(marrys);
    }


}
