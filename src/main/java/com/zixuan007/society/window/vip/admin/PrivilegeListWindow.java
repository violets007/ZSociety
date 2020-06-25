package com.zixuan007.society.window.vip.admin;

import cn.nukkit.form.element.ElementLabel;
import com.zixuan007.society.domain.Vip;
import com.zixuan007.society.utils.PrivilegeUtils;
import com.zixuan007.society.window.CustomWindow;

public class PrivilegeListWindow extends CustomWindow {
    public PrivilegeListWindow() {
        super("特权信息列表");
        StringBuilder sb=new StringBuilder();
        for(Vip vip:PrivilegeUtils.privilegeList){
            sb.setLength(0);
            sb.append("§eVid§f: §b"+vip.getVid()+"\n");
            sb.append("§e持有人§f: §b"+vip.getPlayerName()+"\n");
            sb.append("§e特权类型§f: §l§d"+vip.getVip_Type().getTypeName()+"\n");
            sb.append("§r§e到期时间§f: §b"+vip.getHoldTime()+"\n");
            addElement(new ElementLabel(sb.toString()));
        }
    }
}
