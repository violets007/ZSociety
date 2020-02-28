package com.zixuan007.society.window;

import cn.nukkit.form.window.FormWindowModal;

/**
 * 确认公会升级窗口类
 */
public class AffirmUpdateWindow extends FormWindowModal {
    private long sid;

    public AffirmUpdateWindow(String title, String content, String trueButtonText, String falseButtonText,long sid) {
        super(title, content, trueButtonText, falseButtonText);
        this.sid=sid;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }
}
