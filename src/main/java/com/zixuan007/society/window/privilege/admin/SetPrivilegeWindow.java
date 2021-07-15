package com.zixuan007.society.window.privilege.admin;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseData;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.pojo.PrivilegeType;
import com.zixuan007.society.pojo.Vip;

import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.PrivilegeUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zixuan007
 */
public class SetPrivilegeWindow extends CustomWindow implements WindowLoader {

    public SetPrivilegeWindow() {
        super(PluginUtils.getWindowConfigInfo("setPrivilegeWindow.title"));
    }

    @Override
    public FormWindow init(Object... objects) {
        getElements().clear();
        addElement(new ElementInput("§e需要设置的玩家名称"));
        addElement(new ElementInput("§e需要设置的天数"));
        ArrayList<String> vipType = new ArrayList<>();
        vipType.add(PrivilegeType.VIP.getTypeName());
        vipType.add(PrivilegeType.SVIP.getTypeName());
        addElement(new ElementDropdown("特权类型", vipType));
        return this;
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {
        String setPrivilegePlayerName = response.getInputResponse(0);
        String timeStr = response.getInputResponse(1);
        FormWindow setPrivilegeWindow = WindowManager.getFormWindow(WindowType.SET_PRIVILEGE_WINDOW);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");
        if (!SocietyUtils.isNumeric(timeStr) || "".equals(timeStr)) {
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, "§c输入的不是数字", setPrivilegeWindow, backButtonName, backButtonImage));
            return;
        }

        int time = Integer.parseInt(timeStr);
        FormResponseData dropdownResponse = response.getDropdownResponse(2);
        String elementContent = dropdownResponse.getElementContent();

        Vip vip = new Vip();
        vip.setVid(PrivilegeUtils.getNextVid());


        Calendar calc = Calendar.getInstance();
        calc.setTime(new Date());
        calc.add(Calendar.DATE, time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String format = simpleDateFormat.format(calc.getTime());
        vip.setHoldTime(format);
        vip.setPlayerName(setPrivilegePlayerName);
        vip.setVip_Type(elementContent.equals(PrivilegeType.VIP.getTypeName()) ? PrivilegeType.VIP : PrivilegeType.SVIP);
        PrivilegeUtils.savePrivilege(vip);
        if (!PrivilegeUtils.isSvip(setPrivilegePlayerName) && !PrivilegeUtils.isVIP(setPrivilegePlayerName)) {
            PrivilegeUtils.privilegeList.add(vip);
        }

        player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.setPrivilegeWindow.set", new String[]{"${setPrivilegePlayerName}", "${elementContent}", "${time}"}, new String[]{setPrivilegePlayerName, elementContent, time + ""}), setPrivilegeWindow, backButtonName, backButtonImage));
    }


}
