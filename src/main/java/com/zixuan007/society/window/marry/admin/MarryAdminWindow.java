package com.zixuan007.society.window.marry.admin;

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
public class MarryAdminWindow extends SimpleWindow implements WindowLoader {
    public MarryAdminWindow() {
        super(PluginUtils.getWindowConfigInfo("marryAdminWindow.title"), "");
    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        ElementButtonImageData img1 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("marryAdminWindow.setContribution.button.imgPath"));
        ElementButtonImageData img2 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("marryAdminWindow.remove.button.imgPath"));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("marryAdminWindow.setContribution.button"), img1));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("marryAdminWindow.remove.button"), img2));
        return this;
    }

    @Override
    public void onClick(int id, Player player) {
        switch (id) {
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
