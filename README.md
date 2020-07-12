# Zsociety

作者: zixuan007

[![License](https://img.shields.io/badge/license-GPL-blue)](LICENSE)   [![MCBBS](https://img.shields.io/badge/Link-MCBBS-brightgreen)](https://www.mcbbs.net/thread-970895-1-1.html)  ![build](https://img.shields.io/badge/build-passing-brightgreen) 



## 介绍

一款功能全面基础功能的公会插件。

简单的GUI操作让玩家远离命令操作

当前插件版本: **1.0.8alpha**

**欢迎你贡献代码给这个项目!** 无论 PR 还是 issue.



## 功能

1. **公会系统**

2. **称号系统**

3. **结婚系统**

4. **SVIP系统**

   

## 命令

- #### OP:

  1. /管理 公会
  2. /管理 称号
  3. /管理 结婚
  4. /管理 特权
  5. /管理 给予 称号 [玩家名字] [给予的称号]
  
- User:

  1. /公会
  2. /称号
  3. /结婚
  4. /特权		



## API
```java
package com.zixuan007.society.api;

import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.*;

public class ZsocietyAPI {
    public static ZsocietyAPI instance;

    private ZsocietyAPI(){

    }

    static {
        instance=new ZsocietyAPI();
    }
    /**
     * 获取公会名称
     * @param playerNmae 玩家名字
     * @return
     */
    public static String getSocietyName(String playerNmae) {
        Society society = SocietyUtils.getSocietyByPlayerName(playerNmae);
        return society != null ? society.getSocietyName() : "无公会";
    }

    /**
     * 获取指定玩家所在的公会
     * @param playerName
     * @return
     */
    public static Society getSocietyByPlayerName(String playerName){
        return SocietyUtils.getSocietyByPlayerName(playerName);
    }

    /**
     * 获取职位名称
     * @param playerName 玩家名字
     * @param society 玩家所在的公会
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
     * @param society 玩家当前所存在的公会
     * @return
     */
    public static int getSocietyGrade(Society society) {
        return society != null ? society.getGrade() : -1;
    }

    /**
     * 获取指定玩家的称号
     * @param playerName 玩家名字
     * @return
     */
    public static String getTitle(String playerName) {
        String title = TitleUtils.getTitles(playerName).size()<=0 ? null : TitleUtils.getTitles(playerName).get(0);
        return title != null ? title : "无称号";
    }

    /**
     * 检测玩家是否结婚
     * @return
     */
    public static boolean isMarry(String playerName){
        return MarryUtils.isMarry(playerName);
    }

    /**
     * 获取玩家性别
     * @param playerName
     * @return
     */
    public static String getGenderbyPlayerName(String playerName){
        if(!PluginUtils.isOnlineByName(playerName)) {
            return "未知性别";
        }
        int genderByPlayerName = MarryUtils.getGenderByPlayerName(playerName);
        if (genderByPlayerName < 0) {
            return "未知性别";
        }
        return (genderByPlayerName == 0)? "女" : "男";
    }

    /**
     * 是否是VIP
     * @param playerName
     * @return
     */
    public static boolean isVIP(String playerName){
        if(PrivilegeUtils.isVIP(playerName)) {
            return true;
        }
        return false;
    }

    /**
     * 是否是SVIP
     * @param playerName
     * @return
     */
    public static boolean isSVIP(String playerName){
        if(PrivilegeUtils.isSvip(playerName)) {
            return true;
        }
        return false;
    }

    public static ZsocietyAPI getInstance() {
        return instance;
    }

    public static void setInstance(ZsocietyAPI instance) {
        ZsocietyAPI.instance = instance;
    }
}
```




## 结语

> 尽力做好第一个插件,使插件的健壮性更加强大,设计更加的优雅!


