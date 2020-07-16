package com.zixuan007.society.domain;

import cn.nukkit.utils.Config;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author zixuan007
 */
public class Society {

    private long sid;
    private String societyName;
    private String presidentName;
    private String createTime;
    private Double societyMoney;
    private HashMap<String, ArrayList<Object>> members =new HashMap<String, ArrayList<Object>>();
    private int grade = 1;
    private ArrayList<String> tempApply = new ArrayList<>();
    private String position;
    private String description;

    public Society() {}

    public Society(long sid, String societyName, String presidentName, String createTime, Double societyMoney, HashMap<String, ArrayList<Object>> members) {
        this.sid = sid;
        this.societyName = societyName;
        this.presidentName = presidentName;
        this.createTime = createTime;
        this.societyMoney = societyMoney;
        this.members = members;
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
            society.members = (HashMap<String, ArrayList<Object>>)config.get("psots");
            society.grade = config.getInt("grade");
            society.tempApply = (ArrayList)config.getList("tempApply");
            society.position = (String) config.get("position");
            society.description= (String) config.get("description");
            return society;
        }
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
        return this.members;
    }

    public void setMembers(HashMap<String, ArrayList<Object>> members) {
        this.members = members;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public HashMap<String, ArrayList<Object>> getMembers() {
        return members;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Society{sid=" + this.sid + ", societyName='" + this.societyName + '\'' + ", presidentName='" + this.presidentName + '\'' + ", createTime='" + this.createTime + '\'' + ", societyMoney=" + this.societyMoney + ", psots=" + this.members + ", grade=" + this.grade + ", tempApply=" + this.tempApply + '}';
    }

}