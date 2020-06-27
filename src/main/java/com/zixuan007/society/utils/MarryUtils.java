package com.zixuan007.society.utils;

import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Marry;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 结婚工具类
 * @author zixuan007
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
            if(marryFile.getName().endsWith(".yml")) {
                Config config = new Config(marryFile);
                Marry marry = Marry.init(config);
                marrys.add(marry);
            }
        }
        SocietyPlugin.getInstance().getLogger().debug(marrys.toString());
    }

    /**
     * 移除结婚配置文件
     * @param marry
     */
    public static void removeMarry(Marry marry){
        String fileName = marry.getPropose() +"_"+ marry.getRecipient() + ".yml";
        String configPath = PluginUtils.MARRY_FOLDER + fileName;
        File file = new File(configPath);
        System.gc();
        if(file.delete()){
            SocietyPlugin.getInstance().getLogger().debug("§a成功移除 §e"+configPath);
        }else{
            SocietyPlugin.getInstance().getLogger().debug("§c移除 §b"+configPath+" §c失败,建议手动删除");
        }
        marrys.remove(marry);
    }

    /**
     * 保存指定的公会数据
     * @param marry
     */
    public static void saveMarry(Marry marry){
        String propose = marry.getPropose();
        String recipient = marry.getRecipient();
        String marryConfigPath = PluginUtils.MARRY_FOLDER + propose + "_" + recipient + ".yml";
        Config config = new Config(marryConfigPath, Config.YAML);
        config.set("mid",marry.getMid());
        config.set("求婚者",propose);
        config.set("被求婚者",recipient);
        config.set("结婚时间",marry.getMarryDate());
        config.set("公共资金",marry.getMoney());
        config.set("求婚者性别",marry.getProposeSex());
        config.set("被求婚者性别",marry.getRecipientSex());
        SocietyPlugin.getInstance().getLogger().debug("结婚数据配置文件内容: "+config.getAll());
        config.save();
    }

    /**
     * 获取下一级MID
     * @return
     */
    public static long nextMID(){
        long id=0;
        for (Marry marry : marrys) {
            if(marry.getMid() > id) id=marry.getMid();
        }
        return ++id;
    }

    /**
     * 获取指定玩家的性别
     * @param playerName
     * @return
     */
    public static int getGenderByPlayerName(String playerName){
        Config marryConfig = SocietyPlugin.getInstance().getMarryConfig();
        if(marryConfig.get(playerName) == null) return -1;
        int gender = (int) marryConfig.get(playerName);
        return gender;
    }

    /**
     * 获取指定玩家的性别名称
     */
    public static String getGenderNameByPlayerName(String playerName){
        int genderByPlayerName = getGenderByPlayerName(playerName);
        return genderByPlayerName == 0?"女":"男";
    }

}
