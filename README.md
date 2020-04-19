# Zsociety

作者: zixuan007



## 简介

- ###### **一款全面的公会插件,当前仅支持(Nukkit1.0)**

- Version: 1.0.6aplah

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
     - 副会长和长老均可以审核成员加入公会
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
5. 对Config.yml配置的优化和底部聊天显示分世界的显示BossBar的显示

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
  #  _____              _      _
  # |__  /___  ___ ___ (_) ___| |_ _   _
  #   / // __|/ __/ _ \| |/ _ \ __| | | |
  #  / /_\__ \ (_| (_) | |  __/ |_| |_| |
  # /____|___/\___\___/|_|\___|\__|\__, |
  #                                |___/
  
  
  ##用于检测配置文件版本
  version: 1.0.5alpha
  
  #暂时先别进行更改(后面会进行改动)
  comands: 公会
  
  #默认创建公会金额(浮点类型必须不能删除.0)
  createSocietyMoney: 100000.0
  
  #每次求婚最低金额(浮点类型必须不能删除.0)
  proposeMoney: 100000.0
  
  ##插件默认语言(暂时只支持中文)
  language: cn
  
  #是否开启聊天显示
  isChat: true
  
  #称号格式 可以兼容Zsociety ${变量名}（Zsociety变量暂时有限）和Tip的{变量名}
  chatFormat: "${privilege}§r§7[§6${world}§7][${title}§7][§e${societyName}§7]§7[${zmarry}§7]§b${playerName}§f➣ ${message}"
  
  #是否显示底部信息 （如果有底部显示建议false）
  isTip: true
  
  #底部显示格式 (新手腐竹勿动) 可以兼容Zsociety ${变量名}和Tip的{变量名}
  tipText: "§c✎手持>${itemID}  §9☣地图>{levelName}  §d♨生命>{h}/{mh}  §f۞在线>{online}/{maxplayer}  §b❉延迟>{ms} \n §2✤职位>${post}
                     §e♈金币>{money}   §7☼公会>${societyName}  §3❤公会等级>${societyGrade}"
  
  ##是否开启玩家标签设置
  isSetNameTag: true
  
  ##玩家标签格式化内容
  nameTagFormat: "${privilege}§r §3❤ §e${title} §a✤ §b${societyName} §a✤ §9{name}"
  
  ##称号商店格式内容
  titleShopFormat:
    - "§7[§c称§a号§b商店§7]"
    - "§7[§e称号§b☼§f${title}§7]"
    - "§7[售价§b❤§e${money}§7]"
    - "§c快速抢购"
  
  ##此配置信息暂时勿动!
  post:
    - {
      name: 会长, #名称
      grade: 4, #职位等级
      count: 1 #可拥有数量 (会长必须唯一) -1代表无限
    }
    - {
      name: 副会长,
      grade: 3,
      count: 1
    }
    - {
      name: 元老,
      grade: 2,
      count: 2
    }
    - {
      name: 精英,
      grade: 1,
      count: 1
    }
    - {
      name: 玩家,
      grade: 0,
      count: -1
    }
  
  ##公会等级(人数人数控制后期增加)
  ##配置格式如下 公会等级: [最大成员数量,升级所需要的金额]
  等级1:
    - 10
    - 1000
  等级2:
    - 20
    - 10000
  等级3:
    - 30
    - 20000
  等级4:
    - 40
    - 40000
  等级5:
    - 50
    - 80000
  等级6:
    - 60
    - 100000
  等级7:
    - 70
    - 1000000
  ```
  
- ### Title.yml

  ```yml
  玩家名字:
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
  公共资金: 0
  ```




## 结语

> 尽力做好第一个插件,使插件的健壮性更加强大,设计更加的优雅!


