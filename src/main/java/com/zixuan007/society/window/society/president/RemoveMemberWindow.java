package com.zixuan007.society.window.society.president;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Lang;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;
import com.zixuan007.society.window.society.MessageWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author zixuan007
 */
public class RemoveMemberWindow extends SimpleWindow implements WindowLoader {
    private long sid;

    public RemoveMemberWindow() {
        super(PluginUtils.getWindowConfigInfo("removeMemberWindow.title"), "");
    }


    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        Player player= (Player) objects[0];
        Society society = SocietyUtils.getSocietyByPlayerName(player.getName());
        this.sid=society.getSid();
        if(society.getPost() != null){
            String[] memberList= society.getPost().entrySet().toArray(new String[0]);
            for (String name : memberList) {
                if (name.equals(society.getPresidentName())) {
                    continue;
                }
                addButton(new ElementButton(name));
            }
        }
        return this;
    }

    @Override
    public void onClick(int id, Player player) {
        String playerName = getResponse().getClickedButton().getText();
        Society society = SocietyUtils.getSocietysByID(this.sid);
        society.getPost().remove(playerName);
        society.saveData();
        FormWindow presidentWindow = WindowManager.getFormWindow(WindowType.PRESIDENT_WINDOW);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");

        MessageWindow messageWindow = (MessageWindow) WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,"§a成功移除成员 §b" + playerName, presidentWindow,backButtonName,backButtonImage);
        SocietyUtils.sendMemberTitle(PluginUtils.getLanguageInfo("message.removeMemberWindow.removeMember",new String[]{"${playerName}"},new String[]{playerName}),society);
        if(PluginUtils.isOnlineByName(playerName)) {
            Server.getInstance().getPlayer(playerName).sendTitle(PluginUtils.getLanguageInfo("message.removeMemberWindow.kickPlayer"));
        }
        player.showFormWindow(messageWindow);
        //移除玩家在此公会创建过的商店
        SocietyUtils.removeCreateShop(society,playerName);
    }


}


