package com.zixuan007.society.utils;

import cn.nukkit.utils.Config;
import com.sun.istack.internal.NotNull;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.PrivilegeType;
import com.zixuan007.society.domain.Vip;


import java.io.File;
import java.util.ArrayList;

public class PrivilegeUtils {
    public static ArrayList<Vip> privilegeList = new ArrayList<>();
    public static ArrayList<String> removePrivilegeName = new ArrayList<String>();//需要移除特权的玩家名称

    /**
     * 检查是否为Vip
     *
     * @param playerName
     * @return
     */
    public static boolean isVIP(@NotNull String playerName) {
        for (Vip vip : privilegeList) {
            if (vip.getPlayerName().equals(playerName) && vip.getVip_Type().getTypeName().equals(PrivilegeType.VIP.getTypeName()))
                return true;
        }
        return false;
    }

    /**
     * 检测是否为Svip
     *
     * @param playerName
     * @return
     */
    public static boolean isSvip(@NotNull String playerName) {
        for (Vip vip : privilegeList) {
            if (vip.getPlayerName().equals(playerName) && vip.getVip_Type().getTypeName().equals(PrivilegeType.SVIP.getTypeName()))
                return true;
        }
        return false;
    }

    /**
     * 加载Vip配置文件数据
     */
    public static void loadVipConfig() {
        File file = new File(PluginUtils.PRIVILEGE_FOLDER);
        file.mkdirs();
        File[] files = file.listFiles();
        for (File vipConfigFile : files) {
            if (vipConfigFile.getName().endsWith(".yml")) {
                Config config = new Config(vipConfigFile);
                Vip vip = Vip.init(config);
                privilegeList.add(vip);
            }
        }
        SocietyPlugin.getInstance().getLogger().debug(privilegeList.toString());
    }

    /**
     * 获取下一个vid
     */
    public static long getNextVid() {
        long vid = 0;
        for (Vip vip : privilegeList) {
            if (vip.getVid() > vid)
                vid = vip.getVid();
        }
        return ++vid;
    }

    /**
     * 获取指定VIP实体类
     *
     * @param playerName
     * @return
     */
    public static Vip getPivilegeByPlayerName(String playerName) {
        for (Vip vip : privilegeList) {
            if (vip.getPlayerName().equals(playerName)) return vip;
        }
        return null;
    }

    /**
     * 移除指定玩家的特权数据
     *
     * @param playerName
     */
    public static void removePivilegeData(String playerName) {
        privilegeList.remove(getPivilegeByPlayerName(playerName));
        String configPath = PluginUtils.PRIVILEGE_FOLDER + playerName + ".yml";
        System.gc();//防止文件无法删除
        File file = new File(configPath);
        if (file.delete()) {
            SocietyPlugin.getInstance().getLogger().debug("成功删除 " + file.getAbsolutePath() + " 文件");
        } else {
            SocietyPlugin.getInstance().getLogger().debug("删除 " + file.getAbsolutePath() + " 文件失败");
        }
    }

    /**
     * 保存Vip数据
     *
     * @param vip
     */
    public static void savePrivilege(Vip vip) {
        String configPath = PluginUtils.PRIVILEGE_FOLDER + vip.getPlayerName() + ".yml";
        Config config = new Config(configPath, Config.YAML);
        config.set("vid", vip.getVid());
        config.set("Vip_Type", vip.getVip_Type().getTypeName());
        config.set("BuyDate", vip.getBuyDate());
        config.set("playerName", vip.getPlayerName());
        config.set("holdTime", vip.getHoldTime());
        config.save();
        privilegeList.forEach((vip1) -> {
            if (vip1.getPlayerName().equals(vip.getPlayerName())) {
                vip1.setVid(vip.getVid());
                vip1.setHoldTime(vip.getHoldTime());
                vip1.setBuyDate(vip.getBuyDate());
                vip1.setVip_Type(vip.getVip_Type());
            }
        });
        //每次保存查看下列表数据
        SocietyPlugin.getInstance().getLogger().info(privilegeList.toString());
    }
}
