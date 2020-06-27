package com.zixuan007.society.window.vip;

import cn.nukkit.AdventureSettings;
import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.domain.Vip;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.PrivilegeUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

/**
 * VIP功能界面
 * @author zixuan007
 */
public class VipWindow extends SimpleWindow implements WindowLoader {
    public VipWindow() {
        super(PluginUtils.getWindowConfigInfo("vipwindow.title"), "");
    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        Player player = (Player) objects[0];
        ElementButtonImageData img1 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH,PluginUtils.getWindowConfigInfo("vipwindow.survivalflight.button.imgpath"));
        ElementButtonImageData img2 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH,PluginUtils.getWindowConfigInfo("vipwindow.viewvip.button.imgpath"));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo(player,"vipwindow.survivalflight.button"),img1));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo(player,"vipwindow.viewvip.button"),img2));
        return this;
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
            case 1:
                Vip privilege = PrivilegeUtils.getPivilegeByPlayerName(player.getName());
                if (privilege.getVip_Type().equals("VIP")) {
                   player.showFormWindow(WindowManager.getFromWindow(WindowType.PRIVILEGEWINDOW,player,privilege));
                }else{
                    player.sendMessage(">> §c你不是VIP无法进行查看");
                }
                break;
        }
    }


}
