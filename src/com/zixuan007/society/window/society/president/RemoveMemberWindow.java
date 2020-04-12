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
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.society.MessageWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RemoveMemberWindow extends SimpleWindow {
    public RemoveMemberWindow(long sid, List<String> memberList) {
        super(Lang.removeMemberWindow_Title, "");
        this.sid = sid;
        Society society = SocietyUtils.getSocietysByID(sid);
        for (String name : memberList) {
            if (name.equals(society.getPresidentName()))
                continue;
            addButton(new ElementButton(name));
        }
    }
    private long sid;

    public void onClick(int id, Player player) {
        String playerName = getResponse().getClickedButton().getText();
        Society society = SocietyUtils.getSocietysByID(this.sid);
        society.getPost().remove(playerName);
        society.saveData();
        MessageWindow messageWindow = WindowManager.getMessageWindow("§a成功移除成员 §b" + playerName, getParent(), "返回上级");
        SocietyUtils.sendMemberTitle("§c成员: §b"+playerName+" 被踢出公会",society);
        if(PluginUtils.isOnlineByName(playerName)) Server.getInstance().getPlayer(playerName).sendTitle("§c你被被踢出公会");
        player.showFormWindow((FormWindow)messageWindow);
    }
}


