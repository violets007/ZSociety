package com.zixuan007.society.window.society.admin;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;


/**
 * @author zixuan007
 */
public class SocietyAdminWindow extends SimpleWindow implements WindowLoader {

    public SocietyAdminWindow() {
        super(PluginUtils.getWindowConfigInfo("societyAdminWindow.title"), "");
    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        ElementButtonImageData img1 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyAdminWindow.setSocietyGrade.button.imgPath"));
        ElementButtonImageData img2 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyAdminWindow.setSocietyContribution.button.imgPath"));
        ElementButtonImageData img3 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyAdminWindow.dissolve.button.imgPath"));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyAdminWindow.setSocietyGrade.button"),img1));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyAdminWindow.setSocietyContribution.button"),img2));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyAdminWindow.dissolve.button"),img3));
        return this;
    }

    @Override
    public void onClick(int id, Player player) {
        switch (id){
            case 0:
                player.showFormWindow(new SetGradeWindow());
                break;
            case 1:
                player.showFormWindow(new SetContributeWindow());
                break;
            case 2:
                player.showFormWindow(new DissolveWindow());
                break;
            default:
                break;
        }
    }


}


