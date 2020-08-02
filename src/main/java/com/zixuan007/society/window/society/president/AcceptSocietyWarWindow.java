package com.zixuan007.society.window.society.president;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowLoader;

/**
 * @author zixuan007
 */
public class AcceptSocietyWarWindow extends CustomWindow implements WindowLoader {

    public AcceptSocietyWarWindow() {
        super("");
    }

    @Override
    public FormWindow init(Object... objects) {
        //设置公会战窗口标题

        //设置公会战信息


        return this;
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {

    }
}
