package com.zixuan007.society.window.society;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowManager;
import java.util.List;

public class PlayerApplyListWindow extends SimpleWindow {
    private List<String> tempApply;
    private long sid;

    public PlayerApplyListWindow(List<String> tempApply, long sid) {
        super((String) SocietyPlugin.getInstance().getLangConfig().get("玩家申请加入公会标题"), "§e申请加入公会人员");
        this.tempApply = tempApply;
        this.sid = sid;
        tempApply.forEach(name -> addButton(new ElementButton(name)));
    }

    public void onClick(int id, Player player) {
        String playerName = this.tempApply.get(id);
        if (SocietyUtils.isJoinSociety(playerName)) {
            MessageWindow messageWindow1 = WindowManager.getMessageWindow("§c玩家 " + playerName + " 已经加入公会", getParent(), "返回管理界面");
            return;
        }
        SocietyUtils.addMember(playerName, SocietyUtils.getSocietysByID(this.sid));
        MessageWindow messageWindow = WindowManager.getMessageWindow("§a成功同意 §b" + playerName + " §a加入公会", getParent(), "返回管理界面");
        player.showFormWindow(messageWindow);
    }
}
