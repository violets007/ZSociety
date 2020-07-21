package com.zixuan007.society.window.society.admin;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

/**
 * @author zixuan007
 */
public class SetSocietyWarDataWindow extends SimpleWindow implements WindowLoader {

    public SetSocietyWarDataWindow() {
        super(PluginUtils.getWindowConfigInfo("setSocietyWarDataWindow.title"), "");
    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        addButton(new ElementButton(PluginUtils.getLanguageInfo("setSocietyWarDataWindow.setPosition1.button")));
        addButton(new ElementButton(PluginUtils.getLanguageInfo("setSocietyWarDataWindow.setPosition2.button")));
        addButton(new ElementButton(PluginUtils.getLanguageInfo("setSocietyWarDataWindow.sendWarMoney.button")));
        return this;
    }

    @Override
    public void onClick(int id, Player player) {
        Config societyWarConfig = SocietyPlugin.getInstance().getSocietyWarConfig();
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");
        FormWindow formWindow = WindowManager.getFormWindow(WindowType.Set_Society_War_Data_Window);

        switch (id){
            case 0:
                String position1=player.getPosition().getFloorX()+"-"+player.getPosition().getFloorY()+"-"+player.getPosition().getFloorZ();
                societyWarConfig.set("坐标1",position1);
                player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("setSocietyWarDataWindow.setPosition1"), formWindow, backButtonName, backButtonImage));
                break;
            case 1:
                String position2 =player.getPosition().getFloorX()+"-"+player.getPosition().getFloorY()+"-"+player.getPosition().getFloorZ();
                societyWarConfig.set("坐标2",position2);
                player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("setSocietyWarDataWindow.setPosition2"), formWindow, backButtonName, backButtonImage));
                break;
            case 2:
                //展示设置公会经济窗口内容
                player.showFormWindow(WindowManager.getFormWindow(WindowType.Set_Society_War_Data_Window));
                break;
            default:
                break;
        }

        if(SocietyUtils.isSetSocietyWarData()){
          societyWarConfig.save();
        }
    }
}
