package com.zixuan007.society.utils;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockWallSign;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.pojo.Society;
import com.zixuan007.society.pojo.SocietyWar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


import static com.zixuan007.society.utils.PluginUtils.SOCIETY_FOLDER;
import static com.zixuan007.society.utils.PluginUtils.formatText;

/**
 * 公会插件工具类
 *
 * @author zixuan007
 */
public class SocietyUtils {

    public static HashMap<String, ArrayList<Object>> onCreatePlayer = new HashMap<>();
    public static ArrayList<Society> societies = new ArrayList<>();
    public static ArrayList<SocietyWar> societyWars = new ArrayList<>();
    public static HashMap<String, String> societyChatPlayers = new HashMap<>();

    /**
     * 指定的公会名称是否存在
     *
     * @param societyName 公会名
     * @return
     */
    public static Boolean hasSocietyByName(String societyName) {
        String filePath = SOCIETY_FOLDER + societyName + ".yml";
        File societyFile = new File(filePath);
        return societyFile.exists();
    }

    /**
     * 获取当前格式化后的日期
     *
     * @return yyyy年MM月dd日 HH:mm:ss
     */
    public static String getFormatDateTime() {
        long nowTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return sdf.format(nowTime);
    }

    /**
     * 指定玩家是否加入过公会
     *
     * @param playerName 玩家名
     * @return
     */
    public static boolean hasSociety(String playerName) {
        ArrayList<Society> societies = SocietyUtils.getSocieties();
        for (Society society : societies) {
            for (Map.Entry<String, ArrayList<Object>> entry : society.getPost().entrySet()) {
                String name = entry.getKey();
                if (name.equals(playerName)) {
                    return true;
                }
            }
        }
        return false;
    }


    public static Society getSocietyByPlayerName(String playerName) {
        ArrayList<Society> societies = SocietyUtils.getSocieties();
        Iterator<Society> iterator = societies.iterator();
        while (iterator.hasNext()) {
            Society society = iterator.next();
            if (society == null || society.getPost() == null || society.getPost().entrySet() == null) {
                return null;
            }
            for (Map.Entry<String, ArrayList<Object>> entry : society.getPost().entrySet()) {
                String name = entry.getKey();
                if (name.equals(playerName)) {
                    return society;
                }
            }
        }

        return null;
    }

    /**
     * 检测字符串内容是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    /**
     * 获取公会列表
     *
     * @param currentPage 当前页面
     * @return
     */
    public static List<Society> getSocietyList(int currentPage) {
        ArrayList<Society> societies = SocietyUtils.getSocieties();
        int totalPage = (societies.size() % 10 == 0) ? (societies.size() / 10) : (societies.size() / 10 + 1);
        if (currentPage > totalPage) return null;
        if (currentPage == 1) {
            if (societies.size() <= 10) return societies;
            if (societies.size() > 10) return societies.subList(0, 10);
        } else {
            int pageNumberSize = --currentPage * 10;
            List<Society> subMembers = societies.subList(pageNumberSize, societies.size());
            if (subMembers.size() < 10) {
                return societies.subList(pageNumberSize, pageNumberSize + subMembers.size());
            }
            return societies.subList(pageNumberSize, pageNumberSize + 10);
        }

        return null;
    }

    /**
     * 获获取公会列表总页数
     *
     * @param currentPage
     * @param limit
     * @return
     */
    public static int getSocietyListTotalPage(int currentPage, int limit) {
        ArrayList<Society> societies = SocietyUtils.societies;
        int totalPage = (societies.size() % limit == 0) ? (societies.size() / limit) : (societies.size() / limit + 1);
        return totalPage;
    }

    /**
     * 获取公会实体类通过公会ID
     *
     * @param sid 公会ID
     * @return
     */
    public static Society getSocietyByID(long sid) {
        for (Society society : SocietyUtils.getSocieties()) {
            if (society.getSid() == sid) {
                return society;
            }
        }
        return null;
    }

    /**
     * 检测玩家是否是会长
     *
     * @param playerName 玩家名称
     * @return
     */
    public static boolean hasChairman(String playerName) {
        for (Society society : SocietyUtils.getSocieties()) {
            if (society.getPresidentName().equals(playerName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 移除公会
     *
     * @param societyName 公会名称
     */
    public static void removeSociety(String societyName) {
        String path = SOCIETY_FOLDER + societyName + ".yml";
        File file = new File(path);
        System.gc();
        boolean isdelete = file.delete();
        if (isdelete) {
            SocietyPlugin.getInstance().getLogger().info("§a公会 §b" + file.getName() + " §a删除成功");
        } else {
            SocietyPlugin.getInstance().getLogger().info("§c公会 §4" + file.getName() + " §c删除失败");
        }
    }

    /**
     * 获取当前玩家的职位
     *
     * @param playerName 玩家名称
     * @param society    玩家所在的公会
     * @return
     */
    public static String getPostByName(String playerName, Society society) {
        if (society == null) {
            return "无职位";
        }
        ArrayList<Object> list = society.getPost().get(playerName);
        if (list.size() < 1) {
            return "无职位";
        }
        return (String) list.get(0);
    }

    /**
     * 格式化按钮文本
     *
     * @param tipText
     * @param player
     * @return
     */
    public static String formatButtomText(String tipText, Player player) {
        return formatText(tipText, player);
    }

    /**
     * 格式化聊天文本
     *
     * @param player
     * @param message
     * @return
     */
    public static String formatChat(Player player, String message) {
        Config config = SocietyPlugin.getInstance().getConfig();
        String chatText = (String) config.get("称号格式");
        chatText = chatText.replaceAll("\\$\\{message\\}", message);
        return formatText(chatText, player);
    }


    /**
     * 获取创建下一个公会的ID
     *
     * @return
     */
    public static long getNextSid() {
        ArrayList<Society> societies = SocietyUtils.getSocieties();
        int size = SocietyUtils.getSocieties().size();
        if (size == 0) return 1L;
        long max = 0L;
        for (Society society : societies) {
            if (society.getSid() > max) {
                max = society.getSid();
            }
        }
        return ++max;
    }


    /**
     * 添加成员
     *
     * @param playerName
     * @param society
     */
    public static void addMember(String playerName, Society society, String postName, int postGrade) {
        SocietyUtils.getSocieties().forEach(society1 -> society1.getTempApply().remove(playerName));
        society.getPost().put(playerName, new ArrayList() {
            {
                add(postName);
                add(postGrade);
            }
        });
        SocietyUtils.saveSociety(society);
    }

    /**
     * 给公会所有成员发送标题信息
     *
     * @param title
     */
    public static void sendMemberTitle(String title, Society society) {
        if (society.getPost().size() <= 0) return;
        for (Map.Entry<String, ArrayList<Object>> entry : society.getPost().entrySet()) {
            String playerName = entry.getKey();
            if (!PluginUtils.isOnlineByName(playerName)) continue;
            if (society.getPresidentName().equals(playerName)) continue;
            Server.getInstance().getPlayer(playerName).sendTitle(title);
        }
    }

    /**
     * 检测指定的方块坐标是否已经设置过商店
     *
     * @param block
     * @return
     */
    public static boolean isSetShop(Block block) {
        for (Map.Entry<String, Object> entry : SocietyPlugin.getInstance().getTitleShopConfig().getAll().entrySet()) {
            List<Object> value = (List<Object>) entry.getValue();
            int titleSignX = (int) value.get(0);
            int titleSignY = (int) value.get(1);
            int titleSignZ = (int) value.get(2);
            if (titleSignX == block.getFloorX() && titleSignY == block.getFloorY() && titleSignZ == block.getFloorZ())
                return true;
        }
        for (Map.Entry<String, Object> entry : SocietyPlugin.getInstance().getSocietyShopConfig().getAll().entrySet()) {

            HashMap<String, Object> value = (HashMap<String, Object>) entry.getValue();
            int titleSignX = (int) value.get("x");
            int titleSignY = (int) value.get("y");
            int titleSignZ = (int) value.get("z");
            if (titleSignX == block.getFloorX() && titleSignY == block.getFloorY() && titleSignZ == block.getFloorZ())
                return true;
        }
        return false;
    }


    /**
     * 解散公会移除所有的本公会商店
     *
     * @param society
     */
    public static void removeSocietyShopBySid(Society society) {
        Config societyShopConfig = SocietyPlugin.getInstance().getSocietyShopConfig();
        for (Map.Entry<String, Object> entry : societyShopConfig.getAll().entrySet()) {
            String key = entry.getKey();
            HashMap<String, Object> value = (HashMap<String, Object>) entry.getValue();
            int sid = (int) value.get("sid");
            if (sid == society.getSid()) {
                value.put("dissolve", true);
                societyShopConfig.set(key, value);
                societyShopConfig.save();
                //进行玩家商店内容的返还
                removeShopSign(key);
            }
        }
    }

    /**
     * 移除商店木牌
     *
     * @param key
     */
    public static void removeShopSign(String key) {
        Config societyShopConfig = SocietyPlugin.getInstance().getSocietyShopConfig();
        HashMap<String, Object> societyData = (HashMap<String, Object>) societyShopConfig.get(key);
        int x = (int) societyData.get("x");
        int y = (int) societyData.get("y");
        int z = (int) societyData.get("z");
        String levelName = (String) societyData.get("levelName");
        for (Level level : Server.getInstance().getLevels().values()) {
            Vector3 vector3 = new Vector3(x, y, z);
            Block block = level.getBlock(vector3);
            if (block != null && block.getLevel().getName().equals(levelName) && block instanceof BlockWallSign) {
                block.onBreak(Item.get(0));
            }
        }
    }

    /**
     * 移除玩家指定公会创建过的商店
     *
     * @param society
     */
    public static void removeCreateShop(Society society, String playerName) {
        Config societyShopConfig = SocietyPlugin.getInstance().getSocietyShopConfig();
        for (Map.Entry<String, Object> entry : societyShopConfig.getAll().entrySet()) {
            String key = entry.getKey();
            HashMap<String, Object> value = (HashMap<String, Object>) entry.getValue();
            int sid = (int) value.get("sid");
            String creator = (String) value.get("creator");
            if (society.getSid() == sid && creator.equals(playerName)) {
                value.put("dissolve", true);
                removeShopSign(key);
            }
        }

        //移除
    }


    public static void loadSocietyConfig() {
        File societyFolder = new File(PluginUtils.SOCIETY_FOLDER);
        SocietyPlugin societyPlugin = SocietyPlugin.getInstance();

        if (!societyFolder.exists()) {
            societyFolder.mkdirs();
        }

        File[] files = societyFolder.listFiles();
        for (File file : files) {
            if (file.isDirectory()) continue;

            Config config = new Config(file);
            societyPlugin.getSocietyConfigList().add(config);
            if (file.getName().endsWith(".yml")) {
                Society society = Society.init(config);
                SocietyUtils.getSocieties().add(society);
            }
        }

        SocietyPlugin.getInstance().getLogger().debug(SocietyUtils.getSocieties().toString());
    }

    /**
     * 保存公会数据内容
     *
     * @param society
     */
    public static void saveSociety(Society society) {
        String societyName = society.getSocietyName();
        String societyFilePath = PluginUtils.SOCIETY_FOLDER + societyName + ".yml";
        File file = new File(societyFilePath);

        if (!file.exists()) {
            try {
                boolean newFile = file.createNewFile();
                if (!newFile) {
                    SocietyPlugin.getInstance().getLogger().info("公会配置文件: " + file.getAbsolutePath() + " 文件创建失败,请检查文件配置");
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Config config = new Config(file);
        config.set("sid", society.getSid());
        config.set("societyName", societyName);
        config.set("presidentName", society.getPresidentName());
        config.set("createTime", society.getCreateTime());
        config.set("societyMoney", society.getSocietyMoney());
        config.set("psots", society.getPost());
        config.set("grade", society.getGrade());
        config.set("tempApply", society.getTempApply());

        if (society.getPosition() != null) {
            config.set("position", society.getPosition());
        }

        if (society.getDescription() != null) {
            config.set("description", society.getDescription());
        }


        getSocieties().forEach(society1 -> {
            if (society1.getSid() == society.getSid()) {
                society1.setSocietyName(society.getSocietyName());
                society1.setSocietyMoney(society.getSocietyMoney());
                society1.setGrade(society.getGrade());
                society1.setPosition(society.getPosition());
                society1.setTempApply(society.getTempApply());
            }
        });


        config.save();
    }

    /**
     * 检测是否设置公会战数据
     */
    public static boolean isSetSocietyWarData() {
        return SocietyPlugin.getInstance().getConfig().getAll().entrySet().size() != 0;
    }


    public static ArrayList<Society> getSocieties() {
        return societies;
    }

    public static void setSocieties(ArrayList<Society> societies) {
        SocietyUtils.societies = societies;
    }

}
