package com.zixuan007.society.window.vip.admin;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowManager;

public class PrivilegeManagerWindow extends SimpleWindow {

    public PrivilegeManagerWindow() {
        super("特权管理窗口", "");
        addButton(new ElementButton("设置特权"));
        addButton(new ElementButton("移除特权"));
        addButton(new ElementButton("查看拥有特权玩家"));
    }

    @Override
    public void onClick(int id, Player player) {
        switch (id){
            case 0:
                player.showFormWindow(WindowManager.getSetPrivilegeWindow());
                break;
            case 1:
                player.showFormWindow(WindowManager.getRemovePrivilegeWindow());
                break;
            case 2:
                player.showFormWindow(WindowManager.getPrivilegeListWindow());
                break;
        }
    }
}
