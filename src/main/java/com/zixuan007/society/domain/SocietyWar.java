package com.zixuan007.society.domain;


import java.util.Date;


/**
 * @author zixuan007
 */
public class SocietyWar {
    private long sid; //公会方1
    private long sid2; //公会方2
    private Date warTime;
    private String status; //当前公会战争状态

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public long getSid2() {
        return sid2;
    }

    public void setSid2(long sid2) {
        this.sid2 = sid2;
    }

    public Date getWarTime() {
        return warTime;
    }

    public void setWarTime(Date warTime) {
        this.warTime = warTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
