package com.zixuan007.society.window.marry.admin;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

/**
 * @author zixuan007
 */
public class MarryAdminWindow extends SimpleWindow implements WindowLoader {
    public MarryAdminWindow() {
        super(PluginUtils.getWindowConfigInfo("marryAdminWindow.title"), "");
    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        addButton(new ElementButton("设置指定夫妻公共资产"));
        addButton(new ElementButton("移除指定夫妻"));
        return this;
    }

    @Override
    public void onClick(int id, Player player) {
        switch (id){
            case 0:
                player.showFormWindow(WindowManager.getFormWindow(WindowType.SET_MARRY_MONEY_WINDOW));
                break;
            case 1:
                player.showFormWindow(WindowManager.getFormWindow(WindowType.REMOVE_MARRY_WINDOW));
                break;
            default:
                break;
        }
    }


}
