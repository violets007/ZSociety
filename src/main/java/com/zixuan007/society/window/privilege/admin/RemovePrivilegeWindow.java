package com.zixuan007.society.window.privilege.admin;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.PrivilegeUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

/**
 * @author zixuan007
 */
public class RemovePrivilegeWindow extends CustomWindow implements WindowLoader {
    public RemovePrivilegeWindow() {
        super(PluginUtils.getWindowConfigInfo("removePrivilegeWindow.title"));
    }

    @Override
    public FormWindow init(Object... objects) {
        getElements().clear();
        addElement(new ElementInput("","玩家名称"));
        return this;
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {
        String playerName = response.getInputResponse(0);
        FormWindow removePrivilegeWindow = WindowManager.getFormWindow(WindowType.REMOVE_PRIVILEGE_WINDOW);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");

        if(!PrivilegeUtils.isVIP(playerName) && !PrivilegeUtils.isSvip(playerName)){
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,PluginUtils.getLanguageInfo("message.removePrivilegeWindow.notPrivilege"),removePrivilegeWindow,backButtonName,backButtonImage));
            return;
        }
        PrivilegeUtils.removePivilegeData(playerName);
        if(PluginUtils.isOnlineByName(playerName)){
            Server.getInstance().getPlayer(playerName).setAllowInteract(false);
        }else{
            PrivilegeUtils.removePrivilegeName.add(playerName);
        }
        player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,PluginUtils.getLanguageInfo("message.removePrivilegeWindow.removePrivilege",new String[]{"${playerName}"},new String[]{playerName}),removePrivilegeWindow,backButtonName,backButtonImage));
    }


}
