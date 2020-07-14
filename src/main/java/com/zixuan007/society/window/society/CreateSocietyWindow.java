package com.zixuan007.society.window.society;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.event.society.PlayerCreateSocietyEvent;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;

import java.util.HashMap;

import com.zixuan007.society.window.WindowType;
import me.onebone.economyapi.EconomyAPI;

/**
 * @author zixuan007
 */
public class CreateSocietyWindow extends CustomWindow implements WindowLoader {

    public CreateSocietyWindow() {
        super(PluginUtils.getWindowConfigInfo("createSocietyWindow.title"));
    }

    @Override
    public FormWindow init(Object... objects) {
        getElements().clear();
        if (objects.length >= 1) {
            FormWindow formWindow = (FormWindow) objects[0];
            setBack(true);
            setParent(formWindow);
        }
        this.addElement(new ElementInput("", "公会名称"));
        return this;
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {
        SocietyPlugin societyPlugin = SocietyPlugin.getInstance();
        String societyName = response.getInputResponse(0);
        Boolean societyNameExist = SocietyUtils.isSocietyNameExist(societyName);
        FormWindow createSociety = WindowManager.getFormWindow(WindowType.CREATE_SOCIETY_WINDOW);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");
        if (societyNameExist) {
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.createSocietyWindow.existSociety"), createSociety, backButtonName, backButtonImage));
        } else {
            Double createSocietyMoney;
            if (societyPlugin.getConfig().get("createSocietyMoney") instanceof Integer) {
                createSocietyMoney = ((Integer) societyPlugin.getConfig().get("createSocietyMoney")).doubleValue();
            } else {
                createSocietyMoney = (Double) societyPlugin.getConfig().get("createSocietyMoney");
            }
            double myMoney = EconomyAPI.getInstance().myMoney(player);
            if (myMoney < createSocietyMoney) {
                player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.createSocietyWindow.InsufficientBalance", new String[]{"${createSocietyMoney}"}, new String[]{createSocietyMoney + ""}), createSociety, backButtonName, backButtonImage));
            } else if (societyName != null && !"".equals(societyName)) {
                EconomyAPI.getInstance().reduceMoney(player, createSocietyMoney, true);
                long count = SocietyUtils.getNextSid();
                Society society = new Society(count, societyName, player.getName(), SocietyUtils.getFormatDateTime(), 0.0D, new HashMap<>());
                societyPlugin.getServer().getPluginManager().callEvent(new PlayerCreateSocietyEvent(player, society));
            } else {
                player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.createSocietyWindow.societyNameIsNull"), createSociety, backButtonName, backButtonImage));
            }
        }
    }


}