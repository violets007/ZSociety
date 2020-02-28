package com.zixuan007.society.utils;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Society;
import me.onebone.economyapi.EconomyAPI;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 工具类
 */
public class Utils {
    public final static String FILE_SEPARATOR = System.getProperty("file.separator"); //系统分隔符 win->\  Linux->/
    public final static String SOCIETYFOLDER = SocietyPlugin.getInstance().getServer().getPluginPath() + SocietyPlugin.getInstance().getName() + FILE_SEPARATOR + "Society" + FILE_SEPARATOR;

    /**
     * 检测指定公会名称是否存在
     *
     * @param societyName
     * @return true存在 false不存在
     */
    public static Boolean isSocietyNameExist(String societyName) {
        String filePath = SOCIETYFOLDER + societyName + ".yml";
        File societyFile = new File(filePath);
        return societyFile.exists();
    }

    /**
     * 获取当前指定格式时间
     *
     * @return 当前格式化后的日期时间
     */
    public static String getFormatDateTime() {
        long nowTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return sdf.format(nowTime);
    }

    /**
     * 检测指定玩家是否加入已存在的公会
     *
     * @param playerName 公会名称
     * @return
     */
    public static boolean isJoinSociety(String playerName) {
        ArrayList<Society> societies = SocietyPlugin.getInstance().getSocieties();
        for (Society society : societies) {
            for (Map.Entry<String,ArrayList<Object>> entry : society.getPost().entrySet()) {
                String name = entry.getKey();
                if (name.equals(playerName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取玩家存在的公会
     *
     * @param playerName 玩家名字
     * @return
     */
    public static Society getSocietyByName(String playerName) {
        ArrayList<Society> societies = SocietyPlugin.getInstance().getSocieties();
        for (Society society : societies) {
            for (Map.Entry<String,ArrayList<Object>> entry : society.getPost().entrySet()) {
                String name = entry.getKey();
                if (name.equals(playerName)) {
                    return society;
                }
            }
        }
        return null;
    }

    public static List<String> getMemberList(Society society, int currentPage) {
        return getMemberList(society, currentPage, 10);
    }

    /**
     * 获取公会成员列表
     *
     * @param society     公会实例类
     * @param currentPage 查询的页数
     * @return
     */
    public static List<String> getMemberList(Society society, int currentPage, int limit) {
        HashMap<String, ArrayList<Object>> postMap = society.getPost();
        ArrayList<HashMap<String, Object>> postList = new ArrayList<>();
        ArrayList<String> tempList = new ArrayList<>();
        for (Map.Entry<String, ArrayList<Object>> entry : postMap.entrySet()) {
            ArrayList<Object> value = entry.getValue();
            String playerName = entry.getKey();
            Integer grade = (Integer) value.get(1);
            postList.add(new HashMap<String, Object>() {
                {
                    put("name", playerName);
                    put("grade", grade);
                }
            });
        }
        Collections.sort(postList, new Comparator<HashMap<String, Object>>() {
            @Override
            public int compare(HashMap<String, Object> map1, HashMap<String, Object> map2) {
                Integer grade = (Integer) map1.get("grade");
                Integer grade1 = (Integer) map2.get("grade");
                return grade<grade1?1:(grade>grade1)?-1:(grade.equals(grade1))?0:-1;
            }
        });
        postList.forEach(map->{
            String name = (String) map.get("name");
            tempList.add(name);
        });
        ArrayList<String> members = tempList;
        int pageNumber = members.size() % limit == 0 ? society.getPost().size() / limit : society.getPost().size() / limit + 1;
        if (currentPage > pageNumber) return null;
        if (currentPage == 1) {
            if (members.size() <= limit) return members;
            if (members.size() > limit) return members.subList(0, limit);
        } else {
            int pageNumberSize = (--currentPage) * limit;
            List<String> subMembers = members.subList(pageNumberSize, members.size());
            if (subMembers.size() < limit) {
                return members.subList(pageNumberSize, pageNumberSize + subMembers.size());
            } else {
                return members.subList(pageNumberSize, pageNumberSize + limit);
            }
        }
        return null;
    }

    /**
     * 检测字符串是否为数字
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

    public static int getTotalMemberPage(Society society) {
        return getTotalMemberPage(society, 10);
    }

    /**
     * 获取指定公会成员列表总页数
     *
     * @param society 公会实例类
     * @return 页数
     */
    public static int getTotalMemberPage(Society society, int limit) {
        return society.getPost().size() % limit == 0 ? society.getPost().size() / limit : society.getPost().size() / limit + 1;
    }

    /**
     * 查询指定公会列表页数
     *
     * @param currentPage
     * @return
     */
    public static List<Society> getSocietyList(int currentPage) {
        ArrayList<Society> societies = SocietyPlugin.getInstance().getSocieties();
        int totalPage = societies.size() % 10 == 0 ? societies.size() / 10 : societies.size() / 10 + 1;
        if (currentPage > totalPage) return null;
        if (currentPage == 1) {
            if (societies.size() <= 10) return societies;
            if (societies.size() > 10) return societies.subList(0, 10);
        } else {
            int pageNumberSize = (--currentPage) * 10;
            List<Society> subMembers = societies.subList(pageNumberSize, societies.size());
            if (subMembers.size() < 10) {
                return societies.subList(pageNumberSize, pageNumberSize + subMembers.size());
            } else {
                return societies.subList(pageNumberSize, pageNumberSize + 10);
            }
        }
        return null;
    }

    /**
     * 获取指定公会列表总页数
     *
     * @return 总页数
     */
    public static int getTotalSocietiesPage() {
        return SocietyPlugin.getInstance().getSocieties().size() % 10 == 0 ? SocietyPlugin.getInstance().getSocieties().size() / 10 : SocietyPlugin.getInstance().getSocieties().size() / 10 + 1;
    }

    /**
     * 获取指定公会id的公会实例对象
     *
     * @param sid 公会id
     * @return
     */
    public static Society getSocietysByID(long sid) {
        for (Society society : SocietyPlugin.getInstance().getSocieties()) {
            if (society.getSid() == sid) {
                return society;
            }
        }
        return null;
    }

    /**
     * 获取职位等级通过玩家名
     *
     * @param playerName 玩家名
     * @return
     */
    public static int getPostGradeByName(String playerName) {
        Config config = SocietyPlugin.getInstance().getConfig();
        ArrayList<HashMap<String, Object>> post = (ArrayList<HashMap<String, Object>>) config.get("post");
        for (HashMap<String, Object> map : post) {
            Integer grade = (Integer) map.get("grade");
            String name1 = (String) map.get("name");
            if (name1.equals(playerName))
                return grade;
        }
        return -1;
    }

    /**
     * 移除指定公会所有数据
     *
     * @param societyName 公会类
     */
    public static void removeSociety(String societyName) {
        String path = Utils.SOCIETYFOLDER + societyName + ".yml";
        File file = new File(path);
        file.delete();
    }

    /**
     * 通过玩家名字获取指定职位
     * @param playerName
     */
    public static String getPostByName(String playerName,Society society){
        ArrayList<Object> list = society.getPost().get(playerName);
        return (String) list.get(0);
    }

    /**
     * 格式化底部文本
     * @param text 文本
     * @param player 玩家
     * @return
     */
    public static String formatButtomText(String text, Player player){
        Item itemInHand = player.getInventory().getItemInHand();
        String itemID = itemInHand.getId() + ":" + itemInHand.getDamage();
        Double myMoney = EconomyAPI.getInstance().myMoney(player);
        Society society = Utils.getSocietyByName(player.getName());
        String societyNam = society != null ? society.getSid()+"§9➤"+society.getSocietyName(): "无公会";
        String postName=society!=null?society.getPost().get(player.getName()).get(0)+"":"无职位";
        String name = player.getLevel().getName();
        float ticksPerSecond = SocietyPlugin.getInstance().getServer().getTicksPerSecond();
        text=text.replaceAll("\\{itemID\\}",itemID)
            .replaceAll("\\$\\{world\\}",name)
            .replaceAll("\\$\\{money\\}",myMoney.toString())
            .replaceAll("\\$\\{societyName\\}",societyNam)
            .replaceAll("\\$\\{post\\}",postName)
            .replaceAll("\\$\\{tps\\}",ticksPerSecond+"");
        return text;
    }

    /**
     * 增强聊天前缀
     * @param player 玩家
     * @return
     */
    public static String addPrefixText(Player player,String message){
        Config config = SocietyPlugin.getInstance().getConfig();
        String name = player.getLevel().getName();
        Society society = Utils.getSocietyByName(player.getName());
        String societyNam = society != null ? society.getSocietyName(): "无公会";
        String societyGrade=society!=null?society.getGrade()+"":"无";
        String postName = society != null ? (String) society.getPost().get(player.getName()).get(0) : "无";
        String chatText = (String) config.get("chatText");
        chatText=chatText.replaceAll("\\$\\{world\\}",name)
                .replaceAll("\\$\\{societyName\\}",societyNam)
                .replaceAll("\\$\\{societyGrade\\}",societyGrade)
                .replaceAll("\\$\\{playerName\\}",player.getName())
                .replaceAll("\\$\\{message\\}",message)
                .replaceAll("\\$\\{post\\}",postName);
        return chatText;
    }

    /**
     * 获取下一次创建的sid
     * @return
     */
    public static long getNextSid(){
        ArrayList<Society> societies = SocietyPlugin.getInstance().getSocieties();
        int size = SocietyPlugin.getInstance().getSocieties().size();
        if(size == 0) return 1;
        long max=0;
        for (Society society : societies) {
            if(society.getSid() > max){
                max=society.getSid();
            }
        }
        return ++max;
    }
}
