package com.zixuan007.society.domain;

import cn.nukkit.utils.Config;

/**
 * Vip数据模板
 */
public class Vip {
    private static VipType  Vip_Type=VipType.VIP;

    private long vid;
    private String BuyDate;
    private String playerName;
    private String holdTime;

    public static Vip init(Config config){
        Vip vip = new Vip();
        long vid = (long) config.get("vid");
        String buyDate = (String) config.get("BuyDate");
        String playerName = (String) config.get("playerName");
        String holdTime = (String) config.get("holdTime");
        VipType vip_type = (VipType) config.get("vip_type");
        vip.setVid(vid);
        vip.setBuyDate(buyDate);
        vip.setPlayerName(playerName);
        vip.setHoldTime(holdTime);
        vip.setVip_Type(vip_type);

        return vip;
    }

    public long getVid() {
        return vid;
    }

    public void setVid(long vid) {
        this.vid = vid;
    }

    public String getBuyDate() {
        return BuyDate;
    }

    public void setBuyDate(String buyDate) {
        BuyDate = buyDate;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(String holdTime) {
        this.holdTime = holdTime;
    }

    public static VipType getVip_Type() {
        return Vip_Type;
    }

    public static void setVip_Type(VipType vip_Type) {
        Vip_Type = vip_Type;
    }
}
