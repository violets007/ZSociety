package com.zixuan007.society.window;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;

public class ManageWindow extends FormWindowSimple {
    private long sid;

    public ManageWindow(String title, String content, long sid) {
        super(title, content);
        this.sid=sid;
        addButton(new ElementButton("移除人员"));
        addButton(new ElementButton("设置成员职位"));
        addButton(new ElementButton("解散"));
        addButton(new ElementButton("查看玩家申请"));
        addButton(new ElementButton("升级公会"));
    }


    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }
}
