package com.zixuan007.society.domain;

import java.util.Date;


import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;

/**
 * 结婚数据实体类
 */
public class Marry{
    private long mid; //结婚ID方方便进行查询
    private String propose;//求婚方姓名
    private int proposeSex;//求婚方性别
    private String recipient;//接收方姓名
    private int recipientSex;//被求婚方性别
    private Double money;//公共资金
    private String marryDate;//结婚日期


    /**
     * 初始化构造方法
     */
    public Marry(){}

    /**
     * 初始化当前配置信息
     */
    public static Marry init(Config config){
        Marry marry=new Marry();
        long mid=0;
        if(config.get("mid") instanceof Lang){
            mid= (long) config.get("mid");
        }else{
            mid= ((Integer) config.get("mid")).longValue();
        }
        String propose= (String) config.get("求婚者");
        int proposeSex= (int) config.get("求婚者性别");
        String recipient= (String) config.get("被求婚者");
        int recipientSex= (int) config.get("被求婚者性别");
        Double money= (double) config.get("公共资金");
        String marryDate=(String)config.get("结婚时间");

        marry.mid=mid;
        marry.propose=propose;
        marry.proposeSex=proposeSex;
        marry.recipient=recipient;
        marry.recipientSex=recipientSex;
        marry.money=money;
        marry.marryDate=marryDate;
        return marry;

    }


    public long getMid(){
        return mid;
    }

    public void setMid(){
        this.mid=mid;
    }

    public String getPropose(){
        return propose;
    }
    public void setPropose(String propose){
        this.propose=propose;
        
    }

    public int getProposeSex(){
        return proposeSex;
    }

    public void setProposeSex(int proposeSex){
        this.proposeSex=proposeSex;
    }

    public void setRecipient(String recipient){
       this.recipient=recipient;
    }

    public String getRecipient(){
        return this.recipient;
    }

    public void setRecipientSex(){
        this.recipientSex=recipientSex;
    }

    public int getRecipientSex(){
        return recipientSex;
    }

    public Double getMoney(){
        return money;
    }

    public void setMoney(Double money){
        this.money=money;
    }

    public String getMarryDate(){
        return marryDate;
    }

    public void setMarryDate(){
        this.marryDate=marryDate;
    }

    public void setMarryDate(String marryDate) {
        this.marryDate = marryDate;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    public void setRecipientSex(int recipientSex) {
        this.recipientSex = recipientSex;
    }

    @Override
    public String toString() {
        return "Marry{" +
                "mid=" + mid +
                ", propose='" + propose + '\'' +
                ", proposeSex=" + proposeSex +
                ", recipient='" + recipient + '\'' +
                ", recipientSex=" + recipientSex +
                ", money=" + money +
                ", marryDate='" + marryDate + '\'' +
                '}';
    }
}