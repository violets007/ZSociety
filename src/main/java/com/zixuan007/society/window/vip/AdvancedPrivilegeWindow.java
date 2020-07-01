package com.zixuan007.society.window.vip;

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
 * @author zixuan007
 */
public class AdvancedPrivilegeWindow extends SimpleWindow implements WindowLoader {
    public AdvancedPrivilegeWindow() {
        super(PluginUtils.getWindowConfigInfo("advancedPrivilegeWindow.title"), "");
    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        Player player = (Player) objects[0];
        ElementButtonImageData img1 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("advancedPrivilegeWindow.openVip.button.imgPath"));
        ElementButtonImageData img2 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("advancedPrivilegeWindow.switchCreate.button.imgPath"));
        ElementButtonImageData img3 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("advancedPrivilegeWindow.openInfo.button.imgPath"));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("advancedPrivilegeWindow.openVip.button"),img1));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo(player, "advancedPrivilegeWindow.switchCreate.button"),img2));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo(player, "advancedPrivilegeWindow.openInfo.button"),img3));
        return this;
    }

    @Override
    public void onClick(int id, Player player) {
        switch (id) {
            case 0:
                player.showFormWindow(WindowManager.getFormWindow(WindowType.PRIVILEGE_WINDOW, player));
                break;
            case 1:
                boolean creative = player.isCreative();
                player.setGamemode(!creative ? 1 : 0);
                player.sendMessage(">> §a创造模式 §e" + (!creative ? "开启" : "关闭"));
                break;
            case 2:
                Vip advancedPrivilege = PrivilegeUtils.getPivilegeByPlayerName(player.getName());
                player.showFormWindow(WindowManager.getFormWindow(WindowType.PRIVILEGE_WINDOW,player, advancedPrivilege));
                break;
            default:
                break;
        }
    }


}
