package com.zixuan007.society.window.society;

import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;

import java.util.Collections;


/**
 * @author zixuan007
 */
public class LevelRankWindow extends SimpleWindow implements WindowLoader {
    public LevelRankWindow() {
        super(PluginUtils.getWindowConfigInfo("levelRankWindow.title"), "");

    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        if (objects != null && objects.length >= 1 && objects[0] != null) {
            setParent((FormWindow) objects[0]);
            setBack(true);
        }
        StringBuilder sb = new StringBuilder();
        Collections.sort(SocietyUtils.getSocieties(), (o1, o2) -> Integer.compare(o2.getGrade(), o1.getGrade()));
        sb.append("§l§d公会等级排名§f(§c前五§f)\n");
        for (int i = 0; i < SocietyUtils.getSocieties().size() && i <= 4; i++) {
            Society society = SocietyUtils.getSocieties().get(i);
            String societyName1 = society.getSocietyName();
            int grade = society.getGrade();
            sb.append("§f公会名称 §a" + societyName1 + " §f公会等级 §b" + grade + "\n");
        }
        setContent(sb.toString());
        return this;
    }
}

