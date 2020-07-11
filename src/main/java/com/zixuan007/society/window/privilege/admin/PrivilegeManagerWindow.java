package com.zixuan007.society.window.privilege.admin;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

/**
 * @author zixuan007
 */
public class PrivilegeManagerWindow extends SimpleWindow implements WindowLoader {

    public PrivilegeManagerWindow() {
        super(PluginUtils.getWindowConfigInfo("privilegeManagerWindow.title"), "");
    }

    @Override
    public FormWindow init(Object... objects) {
        ElementButtonImageData img1 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH,PluginUtils.getWindowConfigInfo("privilegeManagerWindow.setPrivilege.button.imgPath"));
        ElementButtonImageData img2 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH,PluginUtils.getWindowConfigInfo("privilegeManagerWindow.removePrivilege.button.imgPath"));
        ElementButtonImageData img3 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH,PluginUtils.getWindowConfigInfo("privilegeManagerWindow.viewPrivilegePlayer.button.imgPath"));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("privilegeManagerWindow.setPrivilege.button"),img1));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("privilegeManagerWindow.removePrivilege.button"),img2));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("privilegeManagerWindow.viewPrivilegePlayer.button"),img3));
        return this;
    }

    @Override
    public void onClick(int id, Player player) {
        switch (id){
            case 0:
                player.showFormWindow(WindowManager.getFormWindow(WindowType.SET_PRIVILEGE_WINDOW));
                break;
            case 1:
                player.showFormWindow(WindowManager.getFormWindow(WindowType.REMOVE_PRIVILEGE_WINDOW));
                break;
            case 2:
                player.showFormWindow(WindowManager.getFormWindow(WindowType.PRIVILEGE_LIST_WINDOW));
                break;
            default:
                break;
        }
    }


}
