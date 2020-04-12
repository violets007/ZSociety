package com.zixuan007.society.window.title.admin;

import cn.nukkit.Player;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Lang;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowManager;

public class RemoveTitleWindow extends CustomWindow {
    public RemoveTitleWindow() {
        super(Lang.removeTitleWindow_title);
        addElement((Element)new ElementInput("", "玩家名字"));
    }


    public void onClick(FormResponseCustom response, Player player) {
        String playerName = response.getInputResponse(0);
        Config titleConfig = SocietyPlugin.getInstance().getTitleConfig();
        String title = (String)titleConfig.get(playerName);
        if (title == null) {
            player.showFormWindow((FormWindow)WindowManager.getMessageWindow("§c输入的玩家名字不存在", (FormWindow)this, "返回上级"));
        } else {
            titleConfig.set(playerName, "无称号");
            titleConfig.save();
            player.showFormWindow((FormWindow)WindowManager.getMessageWindow("§a移除玩家 §b" + playerName + " §a称号成功", (FormWindow)this, "返回上级"));
        }
    }
}


