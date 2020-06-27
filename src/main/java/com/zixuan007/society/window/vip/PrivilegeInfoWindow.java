package com.zixuan007.society.window.vip;


import cn.nukkit.Player;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.domain.Vip;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;

/**
 * @author zixuan007
 */
public class PrivilegeInfoWindow extends SimpleWindow implements WindowLoader {
    private Vip vip;

    public PrivilegeInfoWindow() {
        super("", "");

    }


    @Override
    public FormWindow init(Object... objects) {
        Player player = (Player) objects[0];
        this.vip= (Vip) objects[1];
        setTitle(PluginUtils.getWindowConfigInfo(player,"privilegeInfoWindow.title"));
        StringBuilder sb=new StringBuilder();
        sb.append("§6VID §b"+vip.getVid()+"\n");
        sb.append("§6持有时间 §b"+vip.getHoldTime()+"\n");
        sb.append("§6特权类型 §b"+vip.getVip_Type().getTypeName()+"\n");
        setContent(sb.toString());
        return this;
    }
}
