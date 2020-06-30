package com.zixuan007.society.window.society;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Lang;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;
import me.onebone.economyapi.EconomyAPI;

/**
 * @author zixuan007
 */
public class ContributionWindow extends CustomWindow implements WindowLoader {
    private long sid;

    public ContributionWindow() {
        super(PluginUtils.getWindowConfigInfo("contributionWindow.title"));

    }

    @Override
    public FormWindow init(Object... objects) {
        getElements().clear();
        Player player= (Player) objects[0];
        if(objects != null && objects.length >= 2 && objects[1] != null){
            setParent((FormWindow) objects[1]);
            setBack(true);
        }
        this.sid = SocietyUtils.getSocietyByPlayerName(player.getName()).getSid();
        this.addElement(new ElementInput("", "贡献金额"));
        return this;
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {
        String strMoney = response.getInputResponse(0);
        MessageWindow messageWindow = null;
        FormWindow contributionForm = WindowManager.getFromWindow(WindowType.CONTRIBUTION_WINDOW, player);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");
        if (!strMoney.equals("") && SocietyUtils.isNumeric(strMoney)) {
            if (!SocietyUtils.isJoinSociety(player.getName())) {
                player.showFormWindow(WindowManager.getFromWindow(WindowType.MESSAGE_WINDOW, "§c您当前还没有加入公会,请先加入公会", contributionForm, backButtonName, backButtonImage));
            } else {
                int money = Integer.parseInt(strMoney);
                if (money > 0) {
                    double myMoney = EconomyAPI.getInstance().myMoney(player);
                    if (myMoney < (double)money) {
                        player.showFormWindow(WindowManager.getFromWindow(WindowType.MESSAGE_WINDOW, "§c当前金币不足", contributionForm, backButtonName, backButtonImage));
                        messageWindow = (MessageWindow) WindowManager.getFromWindow(WindowType.MESSAGE_WINDOW, "§c当前金币不足", contributionForm, backButtonName, backButtonImage);
                    } else {
                        Society society = SocietyUtils.getSocietysByID(this.sid);
                        society.setSocietyMoney(society.getSocietyMoney() + (double)money);
                        if (EconomyAPI.getInstance().reduceMoney(player, money) == EconomyAPI.RET_SUCCESS) {
                            WindowManager.getFromWindow(WindowType.MESSAGE_WINDOW, "§a贡献成功,当前公会经济 §b" + society.getSocietyMoney(), contributionForm, backButtonName, backButtonImage);
                            messageWindow = (MessageWindow) WindowManager.getFromWindow(WindowType.MESSAGE_WINDOW, "§a贡献成功,当前公会经济 §b" + society.getSocietyMoney(), contributionForm, backButtonName, backButtonImage);
                        }else {

                            messageWindow = (MessageWindow) WindowManager.getFromWindow(WindowType.MESSAGE_WINDOW, "§a贡献失败，请检查金币数量是否充足", contributionForm, backButtonName, backButtonImage);
                        }
                    }
                }else {

                    messageWindow = (MessageWindow) WindowManager.getFromWindow(WindowType.MESSAGE_WINDOW, "§c请输入大于0的金额", contributionForm, backButtonName, backButtonImage);
                }
                player.showFormWindow(messageWindow);
            }
        } else {
            messageWindow = (MessageWindow) WindowManager.getFromWindow(WindowType.MESSAGE_WINDOW, "§c输入的不是数字", contributionForm, backButtonName, backButtonImage);
            player.showFormWindow(messageWindow);
        }
    }


}