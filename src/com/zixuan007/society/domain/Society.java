package com.zixuan007.society.domain;

import cn.nukkit.utils.Config;
import com.zixuan007.society.utils.SocietyUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class Society {

    private long sid;
    private String societyName;
    private String presidentName;
    private String createTime;
    private Double societyMoney;
    private HashMap<String, ArrayList<Object>> psots;
    private int grade = 1;
    private ArrayList<String> tempApply = new ArrayList<>();

    public Society() {}

    public Society(long sid, String societyName, String presidentName, String createTime, Double societyMoney, HashMap<String, ArrayList<Object>> psots) {
        this.sid = sid;
        this.societyName = societyName;
        this.presidentName = presidentName;
        this.createTime = createTime;
        this.societyMoney = societyMoney;
        this.psots = psots;
    }

    public static Society init(Config config) {
        if (config.get("sid") == null) {
            return null;
        } else {
            Society society = new Society();
            if (config.get("sid") instanceof Integer) {
                society.sid = ((Integer)config.get("sid")).longValue();
            }
            if (config.get("sid") instanceof Long) {
                society.sid = (Long)config.get("sid");
            }
            society.societyName = config.getString("societyName");
            society.presidentName = config.getString("presidentName");
            society.createTime = config.getString("createTime");
            society.societyMoney = config.getDouble("societyMoney");
            society.psots = (HashMap<String, ArrayList<Object>>)config.get("psots");
            society.grade = config.getInt("grade");
            society.tempApply = (ArrayList)config.getList("tempApply");
            return society;
        }
    }

    public void saveData() {
        String societyFilePath = SocietyUtils.SOCIETYFOLDER + this.societyName + ".yml";
        File file = new File(societyFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Config config = new Config(file);
        config.set("sid", this.sid);
        config.set("societyName", this.societyName);
        config.set("presidentName", this.presidentName);
        config.set("createTime", this.createTime);
        config.set("societyMoney", this.societyMoney);
        config.set("psots", this.psots);
        config.set("grade", this.grade);
        config.set("tempApply", this.tempApply);
        config.save();
    }

    public String getSocietyName() {
        return this.societyName;
    }

    public void setSocietyName(String societyName) {
        this.societyName = societyName;
    }

    public long getSid() {
        return this.sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public String getPresidentName() {
        return this.presidentName;
    }

    public void setPresidentName(String presidentName) {
        this.presidentName = presidentName;
    }

    public Double getSocietyMoney() {
        return this.societyMoney;
    }

    public void setSocietyMoney(Double societyMoney) {
        this.societyMoney = societyMoney;
    }

    public HashMap<String, ArrayList<Object>> getPost() {
        return this.psots;
    }

    public void setPsots(HashMap<String, ArrayList<Object>> psots) {
        this.psots = psots;
    }

    public ArrayList<String> getTempApply() {
        return this.tempApply;
    }

    public void setTempApply(ArrayList<String> tempApply) {
        this.tempApply = tempApply;
    }

    public int getGrade() {
        return this.grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String toString() {
        return "Society{sid=" + this.sid + ", societyName='" + this.societyName + '\'' + ", presidentName='" + this.presidentName + '\'' + ", createTime='" + this.createTime + '\'' + ", societyMoney=" + this.societyMoney + ", psots=" + this.psots + ", grade=" + this.grade + ", tempApply=" + this.tempApply + '}';
    }

}