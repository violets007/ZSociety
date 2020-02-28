package com.zixuan007.society.domain;

import cn.nukkit.utils.Config;
import com.zixuan007.society.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 公会实体类
 */
public class Society {
    private long sid; //公会ID
    private String societyName; //公会名称
    private String presidentName; //会长名称
    private String createTime; //公会创建时间
    private Double societyMoney; //公会经济
    private HashMap<String, ArrayList<Object>> psots; //公会成员数据
    private int grade = 1;//公会等级
    private ArrayList<String> tempApply=new ArrayList<>(); //临时申请集合

    public Society() {
    }

    public Society(long sid, String societyName, String presidentName, String createTime, Double societyMoney, HashMap<String, ArrayList<Object>> psots) {
        this.sid = sid;
        this.societyName = societyName;
        this.presidentName = presidentName;
        this.createTime = createTime;
        this.societyMoney = societyMoney;
        this.psots = psots;
    }

    public static Society init(Config config) {
        if (config.get("sid") == null) return null;
        Society society = new Society();
        if (config.get("sid") instanceof Integer) society.sid = ((Integer) config.get("sid")).longValue();
        if (config.get("sid") instanceof Long) society.sid = (long) config.get("sid");
        society.societyName = (String) config.get("societyName");
        society.presidentName = (String) config.get("presidentName");
        society.createTime = (String) config.get("createTime");
        society.societyMoney = (Double) config.get("societyMoney");
        society.psots = (HashMap<String, ArrayList<Object>>) config.get("psots");
        society.grade = (int) config.get("grade");
        society.tempApply = (ArrayList<String>) config.get("tempApply");
        return society;
    }

    public void saveData() {
        String societyFilePath = Utils.SOCIETYFOLDER + this.societyName + ".yml";
        File file = new File(societyFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Config config = new Config(file);
        config.set("sid", sid);
        config.set("societyName", this.societyName);
        config.set("presidentName", presidentName);
        config.set("createTime", createTime);
        config.set("societyMoney", societyMoney);
        config.set("psots", psots);
        config.set("grade", grade);
        config.set("tempApply",tempApply);
        config.save();
    }

    public String getSocietyName() {
        return societyName;
    }

    public void setSocietyName(String societyName) {
        this.societyName = societyName;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }


    public String getPresidentName() {
        return presidentName;
    }

    public void setPresidentName(String presidentName) {
        this.presidentName = presidentName;
    }

    public Double getSocietyMoney() {
        return societyMoney;
    }

    public void setSocietyMoney(Double societyMoney) {
        this.societyMoney = societyMoney;
    }

    public HashMap<String, ArrayList<Object>> getPost() {
        return psots;
    }

    public void setPsots(HashMap<String, ArrayList<Object>> psots) {
        this.psots = psots;
    }

    public ArrayList<String> getTempApply() {
        return tempApply;
    }

    public void setTempApply(ArrayList<String> tempApply) {
        this.tempApply = tempApply;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Society{" +
                "sid=" + sid +
                ", societyName='" + societyName + '\'' +
                ", presidentName='" + presidentName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", societyMoney=" + societyMoney +
                ", psots=" + psots +
                ", grade=" + grade +
                ", tempApply=" + tempApply +
                '}';
    }
}
