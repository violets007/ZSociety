package com.zixuan007.society.utils;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Society;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.onebone.economyapi.EconomyAPI;
import tip.utils.Api;

/**
 * 公会插件工具类
 */
public class SocietyUtils
{
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");//系统分隔符 Linux \  Window /
    public static final String SOCIETYFOLDER = SocietyPlugin.getInstance().getServer().getPluginPath() + SocietyPlugin.getInstance().getName() + FILE_SEPARATOR + "Society" + FILE_SEPARATOR; //公会数据文件路径
    public static final String CONFIGFOLDER = SocietyPlugin.getInstance().getServer().getPluginPath() + SocietyPlugin.getInstance().getName() + FILE_SEPARATOR;//公会配置文件夹







    public static Boolean isSocietyNameExist(String societyName) {
        String filePath = SOCIETYFOLDER + societyName + ".yml";
        File societyFile = new File(filePath);
        return Boolean.valueOf(societyFile.exists());
    }






    public static String getFormatDateTime() {
        long nowTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return sdf.format(Long.valueOf(nowTime));
    }







    public static boolean isJoinSociety(String playerName) {
        ArrayList<Society> societies = SocietyPlugin.getInstance().getSocieties();
        for (Society society : societies) {
            for (Map.Entry<String, ArrayList<Object>> entry : (Iterable<Map.Entry<String, ArrayList<Object>>>)society.getPost().entrySet()) {
                String name = entry.getKey();
                if (name.equals(playerName)) {
                    return true;
                }
            }
        }
        return false;
    }







    public static Society getSocietyByPlayerName(String playerName) {
        ArrayList<Society> societies = SocietyPlugin.getInstance().getSocieties();
        for (Society society : societies) {
            for (Map.Entry<String, ArrayList<Object>> entry : (Iterable<Map.Entry<String, ArrayList<Object>>>)society.getPost().entrySet()) {
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








    public static List<String> getMemberList(Society society, int currentPage, int limit) {
        HashMap<String, ArrayList<Object>> postMap = society.getPost();
        ArrayList<HashMap<String, Object>> postList = new ArrayList<>();
        ArrayList<String> tempList = new ArrayList<>();
        for (Map.Entry<String, ArrayList<Object>> entry : postMap.entrySet()) {
            ArrayList<Object> value = entry.getValue();
            final String playerName = entry.getKey();
            final Integer grade = (Integer)value.get(1);
            postList.add(new HashMap<String, Object>()
            {

            });
        }


        Collections.sort(postList, new Comparator<HashMap<String, Object>>()
        {
            public int compare(HashMap<String, Object> map1, HashMap<String, Object> map2) {
                Integer grade = (Integer)map1.get("grade");
                Integer grade1 = (Integer)map2.get("grade");
                return (grade.intValue() < grade1.intValue()) ? 1 : ((grade.intValue() > grade1.intValue()) ? -1 : (grade.equals(grade1) ? 0 : -1));
            }
        });
        postList.forEach(map -> {
            String name = (String)map.get("name");
            tempList.add(name);
        });
        ArrayList<String> members = tempList;
        int pageNumber = (members.size() % limit == 0) ? (society.getPost().size() / limit) : (society.getPost().size() / limit + 1);
        if (currentPage > pageNumber) return null;
        if (currentPage == 1) {
            if (members.size() <= limit) return members;
            if (members.size() > limit) return members.subList(0, limit);
        } else {
            int pageNumberSize = --currentPage * limit;
            List<String> subMembers = members.subList(pageNumberSize, members.size());
            if (subMembers.size() < limit) {
                return members.subList(pageNumberSize, pageNumberSize + subMembers.size());
            }
            return members.subList(pageNumberSize, pageNumberSize + limit);
        }

        return null;
    }







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







    public static int getTotalMemberPage(Society society, int limit) {
        return (society.getPost().size() % limit == 0) ? (society.getPost().size() / limit) : (society.getPost().size() / limit + 1);
    }







    public static List<Society> getSocietyList(int currentPage) {
        ArrayList<Society> societies = SocietyPlugin.getInstance().getSocieties();
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

    public static int getSocietyListTotalPage(int currentPage, int limit) {
        ArrayList<Society> societies = SocietyPlugin.getInstance().getSocieties();
        int totalPage = (societies.size() % limit == 0) ? (societies.size() / limit) : (societies.size() / limit + 1);
        return totalPage;
    }






    public static int getTotalSocietiesPage() {
        return (SocietyPlugin.getInstance().getSocieties().size() % 10 == 0) ? (SocietyPlugin.getInstance().getSocieties().size() / 10) : (SocietyPlugin.getInstance().getSocieties().size() / 10 + 1);
    }







    public static Society getSocietysByID(long sid) {
        for (Society society : SocietyPlugin.getInstance().getSocieties()) {
            if (society.getSid() == sid) {
                return society;
            }
        }
        return null;
    }







    public static int getPostGradeByName(String playerName) {
        /* 233 */     Config config = SocietyPlugin.getInstance().getConfig();
        /* 234 */     ArrayList<HashMap<String, Object>> post = (ArrayList<HashMap<String, Object>>)config.get("post");
        /* 235 */     for (HashMap<String, Object> map : post) {
            /* 236 */       Integer grade = (Integer)map.get("grade");
            /* 237 */       String name1 = (String)map.get("name");
            /* 238 */       if (name1.equals(playerName))
                /* 239 */         return grade.intValue();
        }
        /* 241 */     return -1;
    }







    public static boolean isChairman(String playerName) {
        /* 251 */     for (Society society : SocietyPlugin.getInstance().getSocieties()) {
            /* 252 */       if (society.getPresidentName().equals(playerName)) return true;
        }
        /* 254 */     return false;
    }






    public static void removeSociety(String societyName) {
           String path = SOCIETYFOLDER + societyName + ".yml";
         File file = new File(path);
            System.gc();
          boolean isdelete = file.delete();
        if (isdelete) {
               SocietyPlugin.getInstance().getLogger().info("§a公会 §b" + file.getName() + " §a删除成功");
        } else {
                 SocietyPlugin.getInstance().getLogger().info("§c公会 §4" + file.getName() + " §c删除失败");
        }
    }







    public static String getPostByName(String playerName, Society society) {
        /* 281 */     ArrayList<Object> list = society.getPost().get(playerName);
        /* 282 */     return (String)list.get(0);
    }







    public static String formatButtomText(String tipText, Player player) {
        /* 292 */     return formatText(tipText, player);
    }







    public static String formatChat(Player player, String message) {
        /* 302 */     Config config = SocietyPlugin.getInstance().getConfig();
        /* 303 */     String chatText = (String)config.get("聊天信息格式");
        /* 304 */     chatText = chatText.replaceAll("\\$\\{message\\}", message);
        /* 305 */     return formatText(chatText, player);
    }

    /*     */


    public static String formatText(String text, Player player) {
        Item itemInHand = player.getInventory().getItemInHand();
        String itemID = itemInHand.getId() + ":" + itemInHand.getDamage();
        Double myMoney = EconomyAPI.getInstance().myMoney(player);
        Society society = getSocietyByPlayerName(player.getName());
        String societyNam = society != null ? "§9" + society.getSocietyName() : "无公会";
        String societyGrade = society != null ? society.getGrade() + "" : "无等级";
        String postName = society != null ? society.getPost().get(player.getName()).get(0) + "" : "无职位";
        String title = (String)SocietyPlugin.getInstance().getTitleConfig().get(player.getName());
        String name = player.getLevel().getName();
        float ticksPerSecond = SocietyPlugin.getInstance().getServer().getTicksPerSecond();
        text = Api.strReplace(text, player);
        text = text.replaceAll("\\$\\{world\\}", name).replaceAll("\\$\\{societyName\\}", societyNam).replaceAll("\\$\\{societyGrade\\}", societyGrade).replaceAll("\\$\\{playerName\\}", player.getName()).replaceAll("\\$\\{post\\}", postName).replaceAll("\\$\\{tps\\}", ticksPerSecond + "").replaceAll("\\$\\{money\\}", myMoney.toString()).replaceAll("\\$\\{itemID\\}", itemID).replaceAll("\\$\\{title\\}", title);
        return text;
    }







    public static long getNextSid() {
        /* 344 */     ArrayList<Society> societies = SocietyPlugin.getInstance().getSocieties();
        /* 345 */     int size = SocietyPlugin.getInstance().getSocieties().size();
        /* 346 */     if (size == 0) return 1L;
        /* 347 */     long max = 0L;
        /* 348 */     for (Society society : societies) {
            /* 349 */       if (society.getSid() > max) {
                /* 350 */         max = society.getSid();
            }
        }
        /* 353 */     return ++max;
    }




    public static List<String> getAllPost() {
        /* 360 */     List<Map<String, Object>> post = (List<Map<String, Object>>)SocietyPlugin.getInstance().getConfig().get("post");
        /* 361 */     ArrayList<String> arrayList = new ArrayList<>();
        /* 362 */     for (Map<String, Object> map : post) {
            /* 363 */       String name = (String)map.get("name");
            /* 364 */       if (name.equals("会长"))
                /* 365 */         continue;  arrayList.add(name);
        }
        /* 367 */     return arrayList;
    }







    public static void addMember(String playerNmae, Society society) {
        /* 377 */     SocietyPlugin.getInstance().getSocieties().forEach(society1 -> society1.getTempApply().remove(playerNmae));


        /* 380 */     society.getPost().put(playerNmae, new ArrayList()
        {

        });


        /* 386 */     society.saveData();
    }
}