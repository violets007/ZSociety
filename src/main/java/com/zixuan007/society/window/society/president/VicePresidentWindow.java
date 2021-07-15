package com.zixuan007.society.window.society.president;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.pojo.Society;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

/**
 * @author zixuan007
 */
public class VicePresidentWindow extends SimpleWindow implements WindowLoader {


    public VicePresidentWindow() {
        super("副会长窗口", "");
    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        ElementButtonImageData img1 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("presidentWindow.playerApplyList.button.imgPath"));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("presidentWindow.playerApplyList.button"), img1));
        return this;
    }


    @Override
    public void onClick(int id, Player player) {
        Society society = SocietyUtils.getSocietyByPlayerName(player.getName());
        FormWindow formWindow = WindowManager.getFormWindow(WindowType.VICE_PRESIDENT_WINDOW);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");
        if (society == null) {
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.vicePresidentWindow.notSociety"), formWindow, backButtonName, backButtonImage));
            return;
        }
        switch (id) {
            case 0:
                if (society.getTempApply() == null || society.getTempApply().size() <= 0) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.presidentWindow.isApplyPlayer"), formWindow, backButtonName, backButtonImage));
                    return;
                }

                player.showFormWindow(WindowManager.getFormWindow(WindowType.PLAYER_APPLY_LIST_WINDOW, player));
                break;
            default:
                break;
        }
    }
}
