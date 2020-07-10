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

    public TitleWindow() {
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
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,PluginUtils.getLanguageInfo("message.titleWindow.remove"),null,closeButtonName,closeButtonImagePath));
            return;
        }
        String title = titles.get(id);
        titles.remove(title);
        titles.add(0,title);
        TitleUtils.saveTitle(playerName,titles);
        player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.titleWindow.wearTitle",new String[]{"${title}"},new String[]{title}),null,closeButtonName,closeButtonImagePath));
    }


}
