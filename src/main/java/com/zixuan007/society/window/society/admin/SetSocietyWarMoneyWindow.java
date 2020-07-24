package com.zixuan007.society.window.society.admin;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

/**
 * @author zixuan007
 */
public class SetSocietyWarMoneyWindow extends CustomWindow implements WindowLoader {


    public SetSocietyWarMoneyWindow() {
        super("设置发起公会战争贡献");
    }

    @Override
    public FormWindow init(Object... objects) {
        getElements().clear();
        addElement(new ElementInput("","设置发起公会的最低贡献度"));
        return this;
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {
        String moneyStr = response.getInputResponse(0);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");
        FormWindow formWindow = WindowManager.getFormWindow(WindowType.Set_Society_War_Data_Window);
        if(SocietyUtils.isNumeric(moneyStr)){
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.societyWindow.existSociety"), formWindow, backButtonName, backButtonImage));
            return;
        }
        Integer money = Integer.getInteger(moneyStr);
        Config societyWarConfig = SocietyPlugin.getInstance().getConfig();
        societyWarConfig.set("money",money);
        if(SocietyUtils.isSetSocietyWarData()){
            societyWarConfig.save();
        }
    }
}
