package com.zixuan007.society.window.vip.admin;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.PrivilegeUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowManager;

public class RemovePrivilegeWindow extends CustomWindow {
    public RemovePrivilegeWindow() {
        super("移除玩家特权窗口");
        addElement(new ElementInput("","玩家名称"));
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {
        String playerName = response.getInputResponse(0);
        if(!PrivilegeUtils.isVIP(playerName) && !PrivilegeUtils.isSvip(playerName)){
            player.showFormWindow(WindowManager.getMessageWindow("§c输入的玩家名并没有特权",this,"返回上级"));
            return;
        }
        PrivilegeUtils.removePivilege(playerName);
        if(PluginUtils.isOnlineByName(playerName)){
            Server.getInstance().getPlayer(playerName).setAllowInteract(false);
        }else{
            PrivilegeUtils.removePrivilegeName.add(playerName);
        }

        player.showFormWindow(WindowManager.getMessageWindow("§a成功移除 §b"+playerName+" §a的特权",this,"返回上级"));
    }
}
