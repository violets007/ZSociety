package com.zixuan007.society.window.society.president;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseData;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.domain.SocietyWar;
import com.zixuan007.society.domain.WarStatus;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author zixuan007
 */
public class SendSocietyWarWindow extends CustomWindow implements WindowLoader {

    public SendSocietyWarWindow() {
        super(PluginUtils.getWindowConfigInfo("sendSocietyWarWindow.title"));
    }

    @Override
    public FormWindow init(Object... objects) {
        getElements().clear();
        Player player = (Player) objects[0];
        ArrayList<String> arrayList = new ArrayList<>();

        for (Society configSociety : SocietyUtils.getSocieties()) {
            if (configSociety.getPresidentName().equals(player.getName())) {
                continue;
            }
            arrayList.add(configSociety.getSid() + " " + configSociety.getSocietyName());
        }
        addElement(new ElementDropdown("列表", arrayList));
        addElement(new ElementInput("", "发起公会战争的金额"));

        //发起公会战的时间
        addElement(new ElementInput("", "发起公会战的时间"));
        addElement(new ElementInput("", "发起公会战的月份"));
        addElement(new ElementInput("", "发起公会战的日期"));
        return this;
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {
        Integer config = (Integer) SocietyPlugin.getInstance().getConfig().get("money");
        FormResponseData dropdownResponse = response.getDropdownResponse(0);
        String sid = dropdownResponse.getElementContent().split(" ")[0];
        String societyName = dropdownResponse.getElementContent().split(" ")[1];
        String moneyStr = response.getInputResponse(1);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");
        String yearStr = response.getInputResponse(2);
        String monthStr = response.getInputResponse(3);
        String dayStr = response.getInputResponse(4);


        String closeButtonName = PluginUtils.getWindowConfigInfo("messageWindow.close.button");
        String closeButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.close.button.imgPath");
        FormWindow formWindow = WindowManager.getFormWindow(WindowType.SEND_SOCIETY_WAR_WINDOW);
        if (!SocietyUtils.isNumeric(moneyStr) || !SocietyUtils.isNumeric(yearStr) || !SocietyUtils.isNumeric(monthStr) || !SocietyUtils.isNumeric(dayStr)) {
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.sendSocietyWarWindow.isNumber"), formWindow, backButtonName, backButtonImage));
            return;
        }

        int money = Integer.parseInt(moneyStr);
        if (money < config) {
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.sendSocietyWarWindow.rarelyCoin", new String[]{"${money}"}, new String[]{config + ""}), formWindow, backButtonName, backButtonImage));
            return;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(yearStr + monthStr + dayStr);
        } catch (ParseException e) {
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.sendSocietyWarWindow.notDate"), formWindow, backButtonName, backButtonImage));
            return;
            //e.printStackTrace();
        }


        Society society = SocietyUtils.getSocietyByPlayerName(player.getName());
        Society targetSociety = SocietyUtils.getSocietysByID(Integer.parseInt(sid));

        SocietyWar societyWar = new SocietyWar();
        societyWar.setSid(society.getSid());
        societyWar.setSid2(targetSociety.getSid());
        societyWar.setWarTime(date);
        societyWar.setStatus(WarStatus.SEND.toString());
        societyWar.setWid(Integer.parseInt(PluginUtils.generateNumber()));
        //创建公会配置文件
        SocietyUtils.createSocietyWar(societyWar);
        player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.sendSocietyWarWindow.success", new String[]{"${societyName}"}, new String[]{societyName}), null, closeButtonName, closeButtonImage));
    }
}
