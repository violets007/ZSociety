package com.zixuan007.society.window.title.admin;
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
public class TitleManagerWindow extends SimpleWindow implements WindowLoader {
    public TitleManagerWindow() {
        super( PluginUtils.getWindowConfigInfo("titleManagerWindow.title"), "");
    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        ElementButtonImageData img1 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("titleManagerWindow.setTitle.button.imgPath"));
        ElementButtonImageData img2 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("titleManagerWindow.removeTitle.button.imgPath"));
        ElementButtonImageData img3 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("titleManagerWindow.createTitleShop.button.imgPath"));

        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("titleManagerWindow.setTitle.button"),img1));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("titleManagerWindow.removeTitle.button"),img2));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("titleManagerWindow.createTitleShop.button"),img3));
        return this;
    }

    @Override
    public void onClick(int id, Player player) {
        switch (id) {
            case 0:
                player.showFormWindow( WindowManager.getFormWindow(WindowType.SET_TITLE_WINDOW));
                break;
            case 1:

                player.showFormWindow(WindowManager.getFormWindow(WindowType.REMOVE_TITLE_WINDOW));
                break;
            case 2:

                player.showFormWindow(WindowManager.getFormWindow(WindowType.CREATE_TITLE_SHOP_WINDOW));
                break;
            default:
                break;
        }
    }


}
