package com.zixuan007.society.api;

import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.SocietyUtils;

public class ZsocietyAPI {

    public static String getSocietyName(String playerNmae) {
        Society society = SocietyUtils.getSocietyByPlayerName(playerNmae);
        return society != null ? society.getSocietyName() : "无公会";
    }

    public static String getPostName(String playerName, Society society) {
        String postByName = null;
        if (society != null) {
            postByName = SocietyUtils.getPostByName(playerName, society);
        }

        return postByName != null ? postByName : "无职位";
    }

    public static int getSocietyGrade(String playerName, Society society) {
        return society != null ? society.getGrade() : -1;
    }

    public static String getTitle(String playerName) {
        String title = (String)SocietyPlugin.getInstance().getTitleConfig().get(playerName);
        return title != null ? title : "无称号";
    }
    
}