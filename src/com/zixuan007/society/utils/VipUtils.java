package com.zixuan007.society.utils;

import cn.nukkit.utils.Config;
import com.sun.istack.internal.NotNull;
import com.zixuan007.society.domain.Svip;
import com.zixuan007.society.domain.Vip;
import com.zixuan007.society.domain.VipType;

import java.io.File;
import java.util.ArrayList;

public class VipUtils {
    public static ArrayList<Vip> vips=new ArrayList<>();
    public static ArrayList<Svip> svips=new ArrayList<>();

    /**
     * 检查是否为Vip
     * @param playerName
     * @return
     */
    public static boolean isVIP(@NotNull String playerName){
        for (Vip vip : vips) {
            if (vip.getPlayerName().equals(playerName)) return true;
        }
        return false;
    }

    /**
     * 检测是否为Svip
     * @param playerName
     * @return
     */
    public static boolean isSvip(@NotNull String playerName){
        for (Svip svip:svips){
            if (svip.getPlayerName().equals(playerName)) return true;
        }
        return false;
    }


    public static void loadVipConfig(){
        File file = new File(PluginUtils.VIP_FOLDER);
        file.mkdirs();
        File[] files = file.listFiles();
        for (File vipConfigFile : files) {
            if(vipConfigFile.getName().endsWith(".yml")){
                Config config = new Config(vipConfigFile);
                VipType vipType = (VipType) config.get("vipType");
                Vip vip = Vip.init(config);
                if(vipType == VipType.VIP){
                    vips.add(vip);
                }else{
                    svips.add((Svip) vip);
                }
            }
        }
    }
}
