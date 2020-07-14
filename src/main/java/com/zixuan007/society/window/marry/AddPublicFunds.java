package com.zixuan007.society.window.marry;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.domain.Marry;
import com.zixuan007.society.utils.MarryUtils;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;
import me.onebone.economyapi.EconomyAPI;

/**
 * @author zixuan007
 */
public class AddPublicFunds extends CustomWindow implements WindowLoader {
    public AddPublicFunds() {
        super(PluginUtils.getWindowConfigInfo("addPublicFunds.title"));

    }

    @Override
    public FormWindow init(Object... objects) {
        getElements().clear();
        addElement(new ElementInput("", "请输入增加的金额"));
        return this;
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {
        String strMoney = response.getInputResponse(0);
        int money = Integer.parseInt(strMoney);
        double myMoney = EconomyAPI.getInstance().myMoney(player);
        FormWindow addPublicFunds = WindowManager.getFormWindow(WindowType.ADD_PUBLIC_FUNDS);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");
        if (myMoney < money) {
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.addPublicFunds.rarelyCoin"), addPublicFunds, backButtonName, backButtonImage));
            return;
        }

        EconomyAPI.getInstance().reduceMoney(player, money);
        Marry marry = MarryUtils.getMarryByName(player.getName());
        marry.setMoney(marry.getMoney() + money);
        MarryUtils.saveMarry(marry);
        player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.addPublicFunds.contribute", new String[]{"${money}"}, new String[]{money + ""}), addPublicFunds, backButtonName, backButtonImage));
    }


}
