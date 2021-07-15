package com.zixuan007.society.window.privilege;


import cn.nukkit.Player;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.pojo.Vip;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;

/**
 * @author zixuan007
 */
public class PrivilegeInfoWindow extends SimpleWindow implements WindowLoader {

    public PrivilegeInfoWindow() {
        super("", "");

    }


    @Override
    public FormWindow init(Object... objects) {
        Player player = (Player) objects[0];
        Vip vip = (Vip) objects[1];
        setTitle(PluginUtils.getWindowConfigInfo(player, "privilegeInfoWindow.title"));
        String sb = "§6VID §b" + vip.getVid() + "\n" +
                "§6持有时间 §b" + vip.getHoldTime() + "\n" +
                "§6特权类型 §b" + vip.getVip_Type().getTypeName() + "\n";
        setContent(sb);
        return this;
    }
}
