package com.zixuan007.society.api;

import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.*;

/**
 * @author zixuan007
 */
public class ZsocietyAPI {
    public static ZsocietyAPI instance;

    private ZsocietyAPI() {

    }

    static {
        instance = new ZsocietyAPI();
    }

    public static ZsocietyAPI getInstance() {
        return instance;
    }

    /**
     * 获取公会名称
     *
     * @param playerName 玩家名字
     * @return
     */
    public static String getSocietyName(String playerName) {
        Society society = SocietyUtils.getSocietyByPlayerName(playerName);
        return society != null ? society.getSocietyName() : "无公会";
    }

    /**
     * 获取指定玩家所在的公会
     *
     * @param playerName
     * @return
     */
    public static Society getSocietyByPlayerName(String playerName) {
        return SocietyUtils.getSocietyByPlayerName(playerName);
    }

    /**
     * 获取职位名称
     *
     * @param playerName 玩家名字
     * @param society    玩家所在的公会
     * @return
     */
    public static String getPostName(String playerName, Society society) {
        String postByName = null;
        if (society != null) {
            postByName = SocietyUtils.getPostByName(playerName, society);
        }

        return postByName != null ? postByName : "无职位";
    }

    /**
     * 获取公会等级
     *
     * @param society 玩家当前所存在的公会
     * @return
     */
    public static int getSocietyGrade(Society society) {
        return society != null ? society.getGrade() : -1;
    }

    /**
     * 获取指定玩家的称号
     *
     * @param playerName 玩家名字
     * @return
     */
    public static String getTitle(String playerName) {
        String title = TitleUtils.getTitles(playerName).size() <= 0 ? null : TitleUtils.getTitles(playerName).get(0);
        return title != null ? title : "无称号";
    }

    /**
     * 检测玩家是否结婚
     *
     * @return
     */
    public static boolean isMarry(String playerName) {
        return MarryUtils.isMarry(playerName);
    }

    /**
     * 获取玩家性别
     *
     * @param playerName
     * @return
     */
    public static String getGenderbyPlayerName(String playerName) {
        if (!PluginUtils.isOnlineByName(playerName)) {
            return "未知性别";
        }
        int genderByPlayerName = MarryUtils.getGenderByPlayerName(playerName);
        if (genderByPlayerName < 0) {
            return "未知性别";
        }
        return (genderByPlayerName == 0) ? "女" : "男";
    }

    /**
     * 是否是VIP
     *
     * @param playerName
     * @return
     */
    public static boolean isVIP(String playerName) {
        if (PrivilegeUtils.isVIP(playerName)) {
            return true;
        }
        return false;
    }

    /**
     * 是否是SVIP
     *
     * @param playerName
     * @return
     */
    public static boolean isSVIP(String playerName) {
        if (PrivilegeUtils.isSvip(playerName)) {
            return true;
        }
        return false;
    }


}
