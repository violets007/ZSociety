package com.zixuan007.society.window.society.admin;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseData;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.domain.Society;
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
public class SetContributeWindow extends CustomWindow implements WindowLoader {

    public SetContributeWindow() {
        super("设置公会贡献窗口");

    }

    @Override
    public FormWindow init(Object... objects) {
        getElements().clear();
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
        return this;
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {
        FormResponseData dropdown = response.getDropdownResponse(0);
        String contributeStr = response.getInputResponse(1);
        FormWindow setContributeWindow = WindowManager.getFormWindow(WindowType.SET_CONTRIBUTE_WINDOW);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");

        String closeButtonName=PluginUtils.getWindowConfigInfo("messageWindow.close.button");
        String closeImagePath=PluginUtils.getWindowConfigInfo("messageWindow.close.button.imgPath");
        if(getElements().size() <= 1){
            return;
        }

        if(!SocietyUtils.isNumeric(contributeStr)){
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.setContributeWindow.isNumber"),setContributeWindow,backButtonName,backButtonImage));
            return;
        }

        String societyContent = dropdown.getElementContent();
        String sidStr = societyContent.split("")[0];
        int sid = Integer.parseInt(sidStr);
        Integer contribute = Integer.parseInt(contributeStr);
        Society society = SocietyUtils.getSocietysByID(sid);

        if(!SocietyUtils.societies.contains(society)){
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,PluginUtils.getLanguageInfo("message.setContributeWindow.notSociety"),null,closeButtonName,closeImagePath));
            return;
        }

        SocietyUtils.societies.remove(society);
        society.setSocietyMoney(contribute.doubleValue());
        SocietyUtils.societies.add(society);
        SocietyUtils.saveSociety(society);
        String societyName = society.getSocietyName();
        Double societyMoney = society.getSocietyMoney();
        player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,PluginUtils.getLanguageInfo("message.setContributeWindow.setContribute",new String[]{"${societyName}","${societyMoney}"},new String[]{societyName,societyMoney+""}),null,closeButtonName,closeImagePath));
    }


}
