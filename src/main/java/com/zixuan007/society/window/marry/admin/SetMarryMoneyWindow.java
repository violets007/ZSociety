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
            addElement(new ElementLabel("当前还没有一对夫妻"));
        }
        return this;
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {
        FormResponseData dropdownResponse = response.getDropdownResponse(0);
        if(getElements().size() <= 1){
            return;
        }
        String midStr = dropdownResponse.getElementContent().split(" ")[0];
        String moneyStr = response.getInputResponse(1);

        if(!SocietyUtils.isNumeric(moneyStr)){
            player.showFormWindow(WindowManager.getMessageWindow("§c设置的金额不是数字",this,"返回上级"));
            return;
        }
        int mid = Integer.parseInt(midStr);
        Integer money = Integer.parseInt(moneyStr);

        Marry marry = MarryUtils.getMarryById(mid);
        if(marry == null){
            player.showFormWindow(WindowManager.getMessageWindow("§c当前夫妻已经离婚,请设置其他的情侣",null,"关闭"));
            return;
        }

        MarryUtils.marrys.remove(marry);
        marry.setMoney(money.doubleValue());
        MarryUtils.marrys.add(marry);
        MarryUtils.saveMarry(marry);

        player.showFormWindow(WindowManager.getMessageWindow("§a成功设置指定夫妻资产为 §e"+marry.getMoney(),null,"关闭"));
    }


}
