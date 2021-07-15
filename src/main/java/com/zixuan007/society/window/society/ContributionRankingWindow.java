package com.zixuan007.society.window.society;

import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.pojo.Society;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;

import java.util.Collections;


/**
 * @author zixuan007
 */
public class ContributionRankingWindow extends SimpleWindow implements WindowLoader {
    public ContributionRankingWindow() {
        super(PluginUtils.getWindowConfigInfo("contributionRankingWindow.title"), "");

    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        if (objects != null && objects.length >= 1 && objects[0] != null) {
            setParent((FormWindow) objects[0]);
            setBack(true);
        }
        Collections.sort(SocietyUtils.getSocieties(), (o1, o2) -> Double.compare(o2.getSocietyMoney().doubleValue(), o1.getSocietyMoney().doubleValue()));
        StringBuilder sb = new StringBuilder();
        sb.append("§l§d公会经济排名§f(§c前五§f)\n");
        for (int i = 0; i < SocietyUtils.getSocieties().size() && i <= 4; i++) {
            Society society = SocietyUtils.getSocieties().get(i);
            long sid = society.getSid();
            String societyName1 = society.getSocietyName();
            Double societyMoney = society.getSocietyMoney();
            sb.append("§f§l公会ID §c" + sid + " §f公会名称 §a" + societyName1 + " §f公会经济 §b" + societyMoney + "\n");
        }
        setContent(sb.toString());
        return this;
    }
}

