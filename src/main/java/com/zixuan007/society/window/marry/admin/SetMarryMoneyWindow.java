package com.zixuan007.society.window.marry.admin;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseData;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.domain.Marry;
import com.zixuan007.society.utils.MarryUtils;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

import java.util.ArrayList;

/**
 * @author zixuan007
 */
public class SetMarryMoneyWindow extends CustomWindow implements WindowLoader {

    public SetMarryMoneyWindow() {
        super(PluginUtils.getWindowConfigInfo("setMarryMoneyWindow.title"));

    }

    @Override
    public FormWindow init(Object... objects) {
        getElements().clear();
        if (MarryUtils.marrys.size() > 0) {
            ArrayList<String> midList = new ArrayList<>();
            for (Marry marry : MarryUtils.marrys) {
                midList.add(marry.getMid()+" 丈夫: §b"+marry.getPropose()+"§r 妻子: §a"+marry.getRecipient());
            }
            addElement(new ElementDropdown("结婚夫妻列表",midList));
            addElement(new ElementInput("","设置的经济数量"));
        }else{
            addElement(new ElementLabel(PluginUtils.getLanguageInfo("message.SetMarryMoneyWindow.noMarry")));
        }
        return this;
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {
        FormResponseData dropdownResponse = response.getDropdownResponse(0);
        FormWindow setMarryMoneyWindow = WindowManager.getFormWindow(WindowType.SET_MARRY_MONEY_WINDOW);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");
        String closeButtonName = PluginUtils.getWindowConfigInfo("messageWindow.close.button");
        String closeButtonImagePath = PluginUtils.getWindowConfigInfo("messageWindow.close.button.imgPath");
        if(getElements().size() <= 1){
            return;
        }
        String midStr = dropdownResponse.getElementContent().split(" ")[0];
        String moneyStr = response.getInputResponse(1);

        if(!SocietyUtils.isNumeric(moneyStr)){
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,PluginUtils.getLanguageInfo("message.setMarryMoneyWindow.isNumber"),setMarryMoneyWindow,backButtonName,backButtonImage));
            return;
        }
        int mid = Integer.parseInt(midStr);
        Integer money = Integer.parseInt(moneyStr);

        Marry marry = MarryUtils.getMarryById(mid);
        if(marry == null){
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,PluginUtils.getLanguageInfo("message.setMarryMoneyWindow.isNumber"),null,closeButtonName,closeButtonImagePath));
            return;
        }

        MarryUtils.marrys.remove(marry);
        marry.setMoney(money.doubleValue());
        MarryUtils.marrys.add(marry);
        MarryUtils.saveMarry(marry);
        Double marryMoney = marry.getMoney();
        player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,PluginUtils.getLanguageInfo("message.setMarryMoneyWindow.set",new String[]{"${marryMoney}"},new String[]{marryMoney+""}),null,closeButtonName,closeButtonImagePath));
    }


}
