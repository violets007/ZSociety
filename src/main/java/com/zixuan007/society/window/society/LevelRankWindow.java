package com.zixuan007.society.window.society;

import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Lang;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.SimpleWindow;
import java.util.Collections;
import java.util.Comparator;



public class LevelRankWindow extends SimpleWindow {
    public LevelRankWindow() {
        super(Lang.levelRankWindow_Title, "");
        StringBuilder sb = new StringBuilder();
        SocietyPlugin societyPlugin = SocietyPlugin.getInstance();
        Collections.sort(SocietyUtils.societies, new Comparator<Society>() {
            public int compare(Society o1, Society o2) {
                return (o1.getGrade() < o2.getGrade()) ? 1 : ((o1.getGrade() > o2.getGrade()) ? -1 : 0);
            }
        });
        sb.append("§l§d公会等级排名§f(§c前五§f)\n");
        for (int i = 0; i < SocietyUtils.societies.size() && i <= 4; i++) {
            Society society = SocietyUtils.societies.get(i);
            long sid = society.getSid();
            String societyName1 = society.getSocietyName();
            int grade = society.getGrade();
            sb.append("§f公会名称 §a" + societyName1 + " §f公会等级 §b" + grade + "\n");
        }
        setContent(sb.toString());
    }
}

