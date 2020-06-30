package com.zixuan007.society.window.society.president;

import cn.nukkit.Player;
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

import java.util.List;

/**
 * @author zixuan007
 */
public class PlayerApplyListWindow extends SimpleWindow implements WindowLoader {
    private List<String> tempApply;
    private long sid;

    public PlayerApplyListWindow(List<String> tempApply, long sid) {
        super( PluginUtils.getWindowConfigInfo("playerApplyListWindow.title"), "§e申请加入公会人员");
    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        Player player= (Player) objects[0];
        Society society = SocietyUtils.getSocietyByPlayerName(player.getName());
        this.tempApply = society.getTempApply();
        this.sid = society.getSid();
        tempApply.forEach(name -> addButton(new ElementButton(name)));
        return this;
    }

    @Override
    public void onClick(int id, Player player) {
        String playerName = this.tempApply.get(id);
        FormWindow societyWindow = WindowManager.getFromWindow(WindowType.SOCIETY_WINDOW);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");

        if (SocietyUtils.isJoinSociety(playerName)) {
            player.showFormWindow(WindowManager.getFromWindow(WindowType.MESSAGE_WINDOW, "§c玩家 " + playerName + " 已经加入公会", societyWindow, backButtonName, backButtonImage));
            return;
        }

        Society society = SocietyUtils.getSocietysByID(this.sid);
        SocietyUtils.addMember(playerName, society);
        SocietyUtils.sendMemberTitle("§a恭喜玩家 §b"+playerName+" §a成功加入公会",society);
        player.showFormWindow(WindowManager.getFromWindow(WindowType.MESSAGE_WINDOW, "§a成功同意 §b" + playerName + " §a加入公会", societyWindow, backButtonName, backButtonImage));
    }


}
