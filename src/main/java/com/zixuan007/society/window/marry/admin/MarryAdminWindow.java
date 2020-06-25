package com.zixuan007.society.window.marry.admin;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import com.zixuan007.society.window.SimpleWindow;

/**
 * @author zixuan007
 */
public class MarryAdminWindow extends SimpleWindow {
    public MarryAdminWindow() {
        super("结婚管理窗口", "");
        addButton(new ElementButton("设置指定夫妻公共资产"));
        addButton(new ElementButton("移除指定夫妻"));
    }

    @Override
    public void onClick(int id, Player player) {
        switch (id){
            case 0:
                player.showFormWindow(new SetMarryMoneyWindow());
                break;
            case 1:
                player.showFormWindow(new RemoveMarryWindow());
                break;
        }
    }
}
