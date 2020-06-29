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
        addElement(new ElementInput("","请输入增加的金额"));
        return this;
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {
        String str_Money = response.getInputResponse(0);
        int money = Integer.parseInt(str_Money);
        double myMoney = EconomyAPI.getInstance().myMoney(player);
        if(myMoney < money){
            player.showFormWindow(WindowManager.getMessageWindow("§c当前资金不足",this,"返回上级"));
            return;
        }
        EconomyAPI.getInstance().reduceMoney(player,money);
        Marry marry = MarryUtils.getMarryByName(player.getName());
        marry.setMoney(marry.getMoney()+money);
        MarryUtils.saveMarry(marry);
        player.showFormWindow(WindowManager.getMessageWindow("§a贡献 §b"+money+" §a成功",this,"返回上级"));
    }


}
