package com.zixuan007.society.window.society.admin;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseData;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowManager;

import java.util.ArrayList;

/**
 * @author zixuan007
 */
public class SetContributeWindow extends CustomWindow {

    public SetContributeWindow() {
        super("设置公会贡献窗口");
        ArrayList<Society> societies = SocietyUtils.societies;
        if (societies.size() > 0) {
            ArrayList<String> sidList = new ArrayList<>();
            for (Society society : societies) {
                sidList.add(society.getSid()+" "+society.getSocietyName());
            }
            addElement(new ElementDropdown("公会列表",sidList));
            addElement(new ElementInput("","请输入玩家等级"));
        } else {
            addElement(new ElementLabel("没有可以设置的公会"));
        }
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {
        FormResponseData dropdown = response.getDropdownResponse(0);
        String contributeStr = response.getInputResponse(1);
        if(getElements().size() <= 1){
            return;
        }

        if(!SocietyUtils.isNumeric(contributeStr)){
            player.showFormWindow(WindowManager.getMessageWindow("§c设置的贡献值不是数字",this,"返回上级"));
            return;
        }

        String societyContent = dropdown.getElementContent();
        String sidStr = societyContent.split("")[0];
        int sid = Integer.parseInt(sidStr);
        Integer contribute = Integer.parseInt(contributeStr);
        Society society = SocietyUtils.getSocietysByID(sid);

        if(!SocietyUtils.societies.contains(society)){
            player.showFormWindow(WindowManager.getMessageWindow("§c此公会已经被解散,请设置其他公会",null,"关闭"));
            return;
        }

        SocietyUtils.societies.remove(society);
        society.setSocietyMoney(contribute.doubleValue());
        SocietyUtils.societies.add(society);
        society.saveData();

        player.showFormWindow(WindowManager.getMessageWindow("§a成功设置 §b"+society.getSocietyName()+" §a贡献值为 §e"+society.getSocietyMoney(),null,"关闭"));
    }
}
