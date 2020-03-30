package com.zixuan007.society.window.society;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowManager;
import me.onebone.economyapi.EconomyAPI;

public class ContributionWindow extends CustomWindow {
    private long sid;

    public ContributionWindow(long sid) {
        super((String)SocietyPlugin.getInstance().getLangConfig().get("贡献窗口标题"));
        this.sid = sid;
        this.addElement(new ElementInput("", "贡献金额"));
    }

    public void onClick(FormResponseCustom response, Player player) {
        String strMoney = response.getInputResponse(0);
        MessageWindow messageWindow = null;
        if (!strMoney.equals("") && SocietyUtils.isNumeric(strMoney)) {
            if (!SocietyUtils.isJoinSociety(player.getName())) {
                messageWindow = WindowManager.getMessageWindow("§c您当前还没有加入公会,请先加入公会", this, "返回上级");
                player.showFormWindow(messageWindow);
            } else {
                int money = Integer.parseInt(strMoney);
                double myMoney = EconomyAPI.getInstance().myMoney(player);
                if (myMoney < (double)money) {
                    messageWindow = WindowManager.getMessageWindow("§c当前金币不足", this, "返回上级");
                } else {
                    Society society = SocietyUtils.getSocietysByID(this.sid);
                    society.setSocietyMoney(society.getSocietyMoney() + (double)money);
                    if (EconomyAPI.getInstance().reduceMoney(player, money) == EconomyAPI.RET_SUCCESS) {
                        messageWindow = WindowManager.getMessageWindow("§a贡献成功,当前公会经济 §b" + society.getSocietyMoney(), this.getParent(), "返回上级");
                    }else {
                        messageWindow = WindowManager.getMessageWindow("§a贡献失败，请检查金币数量是否充足", this.getParent(), "返回上级");
                    }
                }
                player.showFormWindow(messageWindow);
            }
        } else {
            messageWindow = WindowManager.getMessageWindow("§c输入的不是数字", this, "返回上级");
            player.showFormWindow(messageWindow);
        }
    }
}