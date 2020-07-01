package com.zixuan007.society.window.title;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.TitleUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

import java.util.ArrayList;

/**
 * @author zixuan007
 */
public class TitleWindow extends SimpleWindow implements WindowLoader {
    private String playerName;

    public TitleWindow(String playerName) {
        super(PluginUtils.getWindowConfigInfo("titleWindow.title"), "");
    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        Player player = (Player) objects[0];
        this.playerName = player.getName();
        for (String title : TitleUtils.titleList.get(playerName)) {
            addButton(new ElementButton(title));
        }
        return this;
    }

    @Override
    public void onClick(int id, Player player) {
        ArrayList<String> titles = TitleUtils.titleList.get(player.getName());
        String closeButtonName = PluginUtils.getWindowConfigInfo("messageWindow.close.button");
        String closeButtonImagePath=PluginUtils.getWindowConfigInfo("messageWindow.close.button.imgPath");
        if (titles.get(id) == null || titles.size() <= 0) {
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,"§a佩戴的称号已经被管理员移除",null,closeButtonName,closeButtonImagePath));
            return;
        }
        String title = titles.get(id);
        titles.set(0,title);
        TitleUtils.addTitle(playerName, title);
        TitleUtils.titleList.put(playerName, titles);
        player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,"§a成功佩戴 §r§l" + title + " §r§a称号",null,closeButtonName,closeButtonImagePath));
    }


}
