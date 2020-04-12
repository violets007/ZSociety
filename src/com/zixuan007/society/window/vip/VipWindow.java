package com.zixuan007.society.window.vip;

import cn.nukkit.AdventureSettings;
import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import com.zixuan007.society.window.SimpleWindow;

/**
 * VIP功能界面
 */
public class VipWindow extends SimpleWindow {
    public VipWindow() {
        super("VIP特权窗口", "");
        addButton(new ElementButton("开启-关闭 生存飞行"));
    }

    @Override
    public void onClick(int id, Player player) {
        switch (id){
            case 0:
                boolean allowFlight = player.getAllowFlight();
                player.getAdventureSettings().set(AdventureSettings.Type.ALLOW_FLIGHT,!allowFlight);
                player.getAdventureSettings().update();
                player.sendMessage(">> §a生存飞行 §b"+(!allowFlight?"开启":"关闭"));
                break;
        }
    }
}
