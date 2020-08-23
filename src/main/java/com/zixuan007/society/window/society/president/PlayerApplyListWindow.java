package com.zixuan007.society.window.society.president;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

import java.util.List;

/**
 * @author zixuan007
 */
public class PlayerApplyListWindow extends SimpleWindow implements WindowLoader {
    private List<String> tempApply;
    private long sid;

    public PlayerApplyListWindow() {
        super(PluginUtils.getWindowConfigInfo("playerApplyListWindow.title"), "§e申请加入公会人员");
    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        Player player = (Player) objects[0];
        Society society = SocietyUtils.getSocietyByPlayerName(player.getName());
        this.tempApply = society.getTempApply();
        this.sid = society.getSid();
        tempApply.forEach(name -> addButton(new ElementButton(name)));
        return this;
    }

    @Override
    public void onClick(int id, Player player) {
        String playerName = this.tempApply.get(id);
        FormWindow societyWindow = WindowManager.getFormWindow(WindowType.SOCIETY_WINDOW, player);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");

        if (!SocietyUtils.isJoinSociety(playerName)) {

            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.playerApplyList.isJoinSociety", new String[]{"${playerName}"}, new String[]{playerName}), societyWindow, backButtonName, backButtonImage));
            return;
        }

        Society society = SocietyUtils.getSocietysByID(this.sid);
        SocietyUtils.addMember(playerName, society, "精英", 1);
        SocietyUtils.sendMemberTitle(PluginUtils.getLanguageInfo("message.playerApplyList.joinSociety", new String[]{"${playerName}"}, new String[]{playerName}), society);
        player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.playerApplyList.acceptJoinSociety", new String[]{"${playerName}"}, new String[]{playerName}), societyWindow, backButtonName, backButtonImage));
    }


}
