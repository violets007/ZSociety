package com.zixuan007.society.window.vip.admin;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseData;
import com.zixuan007.society.domain.Vip;
import com.zixuan007.society.domain.VipType;
import com.zixuan007.society.utils.PrivilegeUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowManager;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SetPrivilegeWindow extends CustomWindow {

    public SetPrivilegeWindow() {
        super("设置SVIP窗口");
        addElement(new ElementInput("需要设置的玩家名称"));
        addElement(new ElementInput("需要设置的天数"));
        ArrayList<String> vipType = new ArrayList();
        vipType.add(VipType.VIP.getTypeName());
        vipType.add(VipType.SVIP.getTypeName());
        addElement(new ElementDropdown("特权类型",vipType));
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {
        String setPrivilePlayerName = response.getInputResponse(0);
        String time_str = response.getInputResponse(1);
        if(!SocietyUtils.isNumeric(time_str) || time_str.equals("")){
            player.showFormWindow(WindowManager.getMessageWindow("§c输入的不是数字",this,"返回上级"));
            return;
        }
        Integer time=Integer.parseInt(time_str);
        FormResponseData dropdownResponse = response.getDropdownResponse(2);
        String elementContent = dropdownResponse.getElementContent();

        Vip vip = new Vip();
        vip.setVid(PrivilegeUtils.getNextVid());
        long expireTime = new Date().getTime() + 60 * 60 * 24 * time * 1000;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日");
        String format = simpleDateFormat.format(new Date(expireTime));
        vip.setHoldTime(format);
        vip.setBuyDate("");
        vip.setPlayerName(setPrivilePlayerName);
        vip.setVip_Type(elementContent.equals(VipType.VIP.getTypeName())?VipType.VIP:VipType.SVIP);
        PrivilegeUtils.saveData(vip);
        if(!PrivilegeUtils.isSvip(setPrivilePlayerName) && !PrivilegeUtils.isVIP(setPrivilePlayerName)) PrivilegeUtils.privilegeList.add(vip);

        player.showFormWindow(WindowManager.getMessageWindow("§a成功设置玩家 §b"+setPrivilePlayerName+" §a的 §c"+elementContent+" §a天数为 §e"+time+" §a天",this,"返回上级"));
    }
}
