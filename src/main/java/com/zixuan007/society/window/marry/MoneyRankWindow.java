package com.zixuan007.society.window.marry;

import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;

/**
 * @author zixuan007
 */
public class MoneyRankWindow extends SimpleWindow implements WindowLoader {
    public MoneyRankWindow() {
        super(PluginUtils.getWindowConfigInfo("moneyRankWindow.title"), "");

    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        return this;
    }
}
