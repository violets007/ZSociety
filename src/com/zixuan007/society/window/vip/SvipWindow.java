package com.zixuan007.society.window.vip;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowManager;

public class SvipWindow extends SimpleWindow {
    public SvipWindow() {
        super("SVIP特权窗口", "");
        addButton(new ElementButton("VIP功能"));
    }

    @Override
    public void onClick(int id, Player player) {
        switch (id){
            case 0:
                player.showFormWindow(WindowManager.getVipWindow());
                break;
        }
    }
}
