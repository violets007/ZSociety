package com.zixuan007.society.window;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.Utils;

import java.util.ArrayList;

public class SocietyApplyWindow extends FormWindowSimple {
    private long sid;
    public SocietyApplyWindow(String title,long sid) {
        super(title, "");
        this.sid=sid;
        Society society = Utils.getSocietysByID(sid);
        ArrayList<String> tempApply = society.getTempApply();
        tempApply.forEach(name->{
            addButton(new ElementButton(name));
        });
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }
}
