package com.zixuan007.society.window.privilege.admin;

import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.domain.Vip;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.PrivilegeUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowLoader;

/**
 * @author zixuan007
 */
public class PrivilegeListWindow extends CustomWindow implements WindowLoader {

    public PrivilegeListWindow() {
        super(PluginUtils.getWindowConfigInfo("privilegeListWindow.title"));
    }

    @Override
    public FormWindow init(Object... objects) {
        StringBuilder sb=new StringBuilder();
        for(Vip vip:PrivilegeUtils.privilegeList){
            sb.setLength(0);
            sb.append("§eVid§f: §b"+vip.getVid()+"\n")
                    .append("§e持有人§f: §b"+vip.getPlayerName()+"\n")
                    .append("§e特权类型§f: §l§d"+vip.getVip_Type().getTypeName()+"\n")
                    .append("§r§e到期时间§f: §b"+vip.getHoldTime()+"\n");
            addElement(new ElementLabel(sb.toString()));
        }
        if(getElements().size() == 0){
            addElement(new ElementLabel(PluginUtils.getLanguageInfo("message.privilegeListWindow.noData")));
        }
        return this;
    }
}
