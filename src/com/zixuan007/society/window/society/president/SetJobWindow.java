package com.zixuan007.society.window.society.president;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseData;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.society.MessageWindow;
import java.util.ArrayList;


public class SetJobWindow extends CustomWindow {
    public SetJobWindow() {
        super((String)SocietyPlugin.getInstance().getLangConfig().get("设置玩家职位窗口标题"));
        addElement((Element)new ElementInput("", "§e玩家名称"));
        ElementDropdown elementDropdown = new ElementDropdown("§e职位列表", SocietyUtils.getAllPost());
        addElement((Element)elementDropdown);
    }


    public void onClick(FormResponseCustom response, Player player) {
        String playerName = response.getInputResponse(0);
        FormResponseData dropdownResponse = response.getDropdownResponse(1);
        final String jobName = dropdownResponse.getElementContent();
        final int jobGrade = SocietyUtils.getPostGradeByName(jobName);
        if (player.getName().equals(playerName)) {
            MessageWindow messageWindow1 = WindowManager.getMessageWindow("§c设置职位的玩家不能是自己", (FormWindow)this, "返回上级");
            player.showFormWindow((FormWindow)messageWindow1);
            return;
        }
        if (!SocietyUtils.isJoinSociety(playerName)) {
            MessageWindow messageWindow1 = WindowManager.getMessageWindow("§c设置的成员还没有加入公会", (FormWindow)this, "返回上级");
            player.showFormWindow((FormWindow)messageWindow1);
            return;
        }
        Society society = SocietyUtils.getSocietyByPlayerName(playerName);
        if (!society.getPost().keySet().contains(playerName)) {
            MessageWindow messageWindow1 = WindowManager.getMessageWindow("§c此玩家不是本公会成员,无法设置职位", (FormWindow)this, "返回上级");
            player.showFormWindow((FormWindow)messageWindow1);
            return;
        }
        society.getPost().put(playerName, new ArrayList() {
            {
                add(jobName);
                add(jobGrade);
            }
        });
        society.saveData();

        MessageWindow messageWindow = WindowManager.getMessageWindow("§a成功设置 §b" + playerName + " §a职位为 §e" + jobName, (FormWindow)this, "返回上级");
        if(PluginUtils.isOnlineByName(playerName)){
            Server.getInstance().getPlayer(playerName).sendTitle("§a职位成功升级为 §e"+jobName);
        }
        player.showFormWindow((FormWindow)messageWindow);
    }
}


