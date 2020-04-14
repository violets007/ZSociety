package com.zixuan007.society.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 称号工具类
 */
public class TitleUtils {
    public static final HashMap<String, HashMap<String, Object>> onCreateName = new HashMap(); //正在创建商店的玩家
    public static final HashMap<String, ArrayList<String>> titleList = new HashMap<String, ArrayList<String>>();


    /**
     * 检测指定称号是否存在
     * @param title
     * @param playerName
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
     * @param playerName
     * @param title
     */
    public static void addTitle(String playerName,String title){
        ArrayList<String> arrayList = titleList.get(playerName);
        if (arrayList == null) {
            arrayList=new ArrayList<>();
        }
        arrayList.add(title);
        titleList.put(playerName,arrayList);
    }

    /**
     * 获取指定玩家的所有称号
     * @param playerName
     * @return
     */
    public static ArrayList<String> getTitles(String playerName){
        ArrayList<String> arrayList = titleList.get(playerName);
        return arrayList == null?new ArrayList<String>():arrayList;
    }
}