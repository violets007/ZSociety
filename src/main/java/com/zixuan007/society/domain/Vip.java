package com.zixuan007.society.domain;

import cn.nukkit.utils.Config;

/**
 * Vip数据模板
 */
public class Vip {
    private VipType vip_Type = VipType.VIP;

    private long vid;
    private String BuyDate;
    private String playerName;
    private String holdTime;

    public static Vip init(Config config) {
        Vip vip = new Vip();
        long vid = 0;
        if (config.get("vid") instanceof Integer) {
            vid = ((Integer) config.get("vid")).longValue();
        } else {
            vid = (long) config.get("vid");
        }
        String buyDate = (String) config.get("BuyDate");
        String playerName = (String) config.get("playerName");
        String holdTime = (String) config.get("holdTime");
        String vip_type = (String) config.get("Vip_Type");
        vip.setVid(vid);
        vip.setBuyDate(buyDate);
        vip.setPlayerName(playerName);
        vip.setHoldTime(holdTime);
        vip.setVip_Type(vip_type.equals(VipType.VIP.getTypeName()) ? VipType.VIP : VipType.SVIP);

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

    public VipType getVip_Type() {
        return vip_Type;
    }

    public void setVip_Type(VipType vip_Type) {
        this.vip_Type = vip_Type;
    }

    @Override
    public String toString() {
        return "Vip{" +
                "vip_Type=" + vip_Type +
                ", vid=" + vid +
                ", BuyDate='" + BuyDate + '\'' +
                ", playerName='" + playerName + '\'' +
                ", holdTime=" + holdTime +
                '}';
    }
}
