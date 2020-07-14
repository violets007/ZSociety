package com.zixuan007.society.window.privilege;

import cn.nukkit.AdventureSettings;
import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.domain.Vip;
import com.zixuan007.society.domain.VipType;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.PrivilegeUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

/**
 * VIP功能界面
 *
 * @author zixuan007
 */
public class PrivilegeWindow extends SimpleWindow implements WindowLoader {
    public PrivilegeWindow() {
        super(PluginUtils.getWindowConfigInfo("privilegeWindow.title"), "");
    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        Player player = (Player) objects[0];
        ElementButtonImageData img1 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("privilegeWindow.survivalFlight.button.imgPath"));
        ElementButtonImageData img2 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("privilegeWindow.viewVip.button.imgPath"));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo(player, "privilegeWindow.survivalFlight.button"), img1));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo(player, "privilegeWindow.openInfo.button"), img2));
        return this;
    }

    @Override
    public void onClick(int id, Player player) {
        switch (id) {
            case 0:
                boolean allowFlight = player.getAllowFlight();
                player.getAdventureSettings().set(AdventureSettings.Type.ALLOW_FLIGHT, !allowFlight);
                player.getAdventureSettings().update();
                String flightStats = allowFlight ? "关闭" : "开启";
                player.sendMessage(PluginUtils.getLanguageInfo("message.privilegeWindow.setFlight", new String[]{"${allowFlight}"}, new String[]{flightStats}));
                break;
            case 1:
                Vip privilege = PrivilegeUtils.getPivilegeByPlayerName(player.getName());
                if (privilege != null && privilege.getVip_Type().equals(VipType.VIP)) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.PRIVILEGE_WINDOW, player, privilege));
                } else {
                    player.sendMessage(PluginUtils.getLanguageInfo("message.PrivilegeWindow.isVip"));
                }
                break;
            default:
                break;
        }
    }


}
