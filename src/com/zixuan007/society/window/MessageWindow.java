package com.zixuan007.society.window;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import com.zixuan007.society.domain.Society;

public class MessageWindow extends FormWindowSimple {
    private long sid;
    private String message;

    public MessageWindow(String title, String message,long sid) {
        super(title, message);
        this.sid=sid;
        this.message=message;
        getButtons().add(new ElementButton("确认"));
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
