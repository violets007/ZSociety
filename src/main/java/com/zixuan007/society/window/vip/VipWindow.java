package com.zixuan007.society.window.vip;

import cn.nukkit.AdventureSettings;
import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;

/**
 * VIP功能界面
 * @author zixuan007
 */
public class VipWindow extends SimpleWindow implements WindowLoader {
    public VipWindow() {
        super("VIP特权窗口", "");
        init();
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

    @Override
    public FormWindow init(Object[]... objects) {
        addButton(new ElementButton("开启-关闭 生存飞行"));
        addButton(new ElementButton("查看SVIP信息"));
        return this;
    }
}
