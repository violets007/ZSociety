package com.zixuan007.society.event.marry;

import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;

import java.util.Date;

/**
 * @author zixuan007
 */
public class PlayerMarryEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private String propose;
    private String recipient;
    private Date marryDate;

    public static HandlerList getHandlers() {
        return handlers;
    }

    public PlayerMarryEvent(String propose, String recipient, Date marryDate) {
        this.propose = propose;
        this.recipient = recipient;
        this.marryDate = marryDate;
    }

    public String getPropose() {
        return propose;
    }

    public void setPropose(String propose) {
        this.propose = propose;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Date getMarryDate() {
        return marryDate;
    }

    public void setMarryDate(Date marryDate) {
        this.marryDate = marryDate;
    }
}
