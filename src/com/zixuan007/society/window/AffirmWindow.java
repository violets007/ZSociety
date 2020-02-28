package com.zixuan007.society.window;

import cn.nukkit.form.window.FormWindowModal;

public class AffirmWindow extends FormWindowModal {
    private long sid;
    private String name;
    public AffirmWindow(String title, String content, String trueButtonText, String falseButtonText,long sid,String name) {
        super(title, content, trueButtonText, falseButtonText);
        this.sid=sid;
        this.name=name;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
