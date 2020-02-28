package com.zixuan007.society.window;

import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public class SetPostWindow extends FormWindowCustom {
    private long sid;

    public SetPostWindow(String title,long sid) {
        super(title);
        this.sid=sid;
        getElements().add(new ElementInput("", "§e成员名称"));
        Config config = SocietyPlugin.getInstance().getConfig();
        ArrayList<HashMap<String, Object>> post = (ArrayList<HashMap<String, Object>>) config.get("post");
        ArrayList<String> postName = new ArrayList<>();

        for (HashMap<String, Object> map : post) {
            String name=(String) map.get("name");
            if(name.equals("会长")) continue;
            postName.add(name);
        }
        ElementDropdown elementDropdown = new ElementDropdown("职位", postName, 0);
        getElements().add(elementDropdown);
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }
}
