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
 * 设置公会等级窗口
 * @author zixuan007
 */
public class SetGradeWindow extends CustomWindow implements WindowLoader {


    public SetGradeWindow() {
        super(PluginUtils.getWindowConfigInfo("setGradeWindow.title"));
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
        String gradeStr = response.getInputResponse(1);
        FormWindow setGradeWindow = WindowManager.getFormWindow(WindowType.SET_GRADE_WINDOW);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");

        String closeButtonName = PluginUtils.getWindowConfigInfo("messageWindow.close.button");
        String closeButtonImagePath = PluginUtils.getWindowConfigInfo("messageWindow.close.button.imgPath");
        if(getElements().size() <= 1){
            return;
        }

        if(!SocietyUtils.isNumeric(gradeStr)){
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,PluginUtils.getLanguageInfo("message.setSocietyGrade.isNumber"),setGradeWindow,backButtonName,backButtonImage));
            return;
        }

        String societyContent = dropdown.getElementContent();
        String sidStr = societyContent.split("")[0];
        int sid = Integer.parseInt(sidStr);
        int grade = Integer.parseInt(gradeStr);
        Society society = SocietyUtils.getSocietysByID(sid);

        if(!SocietyUtils.societies.contains(society)){
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,PluginUtils.getLanguageInfo("message.setSocietyGrade.notSociety"),null,closeButtonName,closeButtonImagePath));
            return;
        }

        SocietyUtils.societies.remove(society);
        society.setGrade(grade);
        SocietyUtils.societies.add(society);
        SocietyUtils.saveSociety(society);
        String societyName = society.getSocietyName();
        int societyGrade = society.getGrade();
        player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.setSocietyGrade.setSocietyGrade",new String[]{"${societyName}","${societyGrade}"},new String[]{societyName,societyGrade+""}),null,closeButtonName,closeButtonImagePath));
    }


}
