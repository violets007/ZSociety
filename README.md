# Zsociety

作者: zixuan007



## 简介

- ###### **一款全面的公会插件,当前仅支持(Nukkit1.0)**

- Version: 1.0.5aplah

- 当前插件处于快速版本迭代,可能会删除你的配置文件和数据,插件**不会**向下版本配置文件兼容！

- 前置所需要的插件: **FloatingText** 



## 功能

1. **公会系统**
   - 公会成员列表
   - 公会经济贡献榜
   - 公会等级榜
   - 公会商店
   - 会长管理界面
     - 设置成员职位
     - 移除人员职位
     - 升级公会
     - 解散公会
2. **称号系统**
   1. 管理称号
      - 设置玩家称号
      - 移除玩家称号
      - 创建称号商店
   2. 称号选择
      - 待定
3. **结婚系统**
   - 进服性别选择
   - 可以情侣之间的相互传送
   - 情侣之间可以存储公共财产
   - 情侣之间可以Tpa
4. **SVIP系统**
   - VIP系统
     - 生存飞行
   - SVIP系统
     - 生存飞行

## 命令

> /**公会** 展示公会功能GUI
>
> /**称号** 展示管理称号GUI(只能op进行操作)
>
> /**结婚** 展示结婚功能GUI
>
> /**特权** 展示特权功能界面



## API
```java
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
        String title = (String)SocietyPlugin.getInstance().getTitleConfig().get(playerName);
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
        if(!PluginUtils.isOnlineByName(playerName)) return "未知性别";
        int genderByPlayerName = MarryUtils.getGenderByPlayerName(playerName);
        if (genderByPlayerName < 0) return "未知性别";
        return (genderByPlayerName == 0)? "女" : "男";
    }

    /**
     * 是否是VIP
     * @param playerName
     * @return
     */
    public static boolean isVIP(String playerName){
        if(PrivilegeUtils.isVIP(playerName)) return true;
        return false;
    }

    /**
     * 是否是SVIP
     * @param playerName
     * @return
     */
    public static boolean isSVIP(String playerName){
        if(PrivilegeUtils.isSvip(playerName)) return true;
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



## Config

- ### Config.yml

  ```yml
  #公会插件主配置文件
  
  ##用于检测插件配置文件的版本是否和更新插件的版本相同
  version: 1.0.0
  
  ##创建公会的金额
  CreateSocietyMoney: 100000
  
  ##是否开启称号
  isChat: false
  ##称号显示的格式
  ChatFormat: “${SocietyName}${PostName} >> ${message}”
  
  ##是否开启底部
  isTip: false
  ##底部显示的格式信息
  TipFormat: "底部显示信息"
  
  #(暂时还没有加入)
  ##是否开启Boss血条信息显示
  isBossBar: false
  ##Boss血条的格式信息
  BossBarFormat: "此配置是专门用于显示配置文件信息"
  
  ##木牌称号商店格式信息
  SignTitleShopFormat:
  - "[称号商店]"
  - "[称号格式]"
  - "[称号经济]"
  - "[木牌格式信息]"
  	
  ```

- ### Title.yml

  ```yml
  ##暂时还没有进行更改
  玩家名字:
  称号列表:
  -  Title
  -  Title2
  -  Title3
  
  ```

- ### 结婚配置文件

  ```yml
  发起求婚玩家名_同意结婚玩家玩家名:
  结婚日期: 2020-3-23
  求婚者性别: 男
  被求婚者性别: 女
  公共资金: 200
  ```




## 结语

> 尽力做好第一个插件,使插件的健壮性更加强大,设计更加的优雅!


