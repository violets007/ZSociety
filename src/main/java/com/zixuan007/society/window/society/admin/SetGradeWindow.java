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
 * 设置公会等级窗口
 * @author zixuan007
 */
public class SetGradeWindow extends CustomWindow {


    public SetGradeWindow() {
        super("设置公会等级窗口");
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
        String gradeStr = response.getInputResponse(1);
        if(getElements().size() <= 1){
            return;
        }

        if(!SocietyUtils.isNumeric(gradeStr)){
            player.showFormWindow(WindowManager.getMessageWindow("§c设置的等级不是数字请重新输入",this,"返回上级"));
            return;
        }

        String societyContent = dropdown.getElementContent();
        String sidStr = societyContent.split("")[0];
        int sid = Integer.parseInt(sidStr);
        int grade = Integer.parseInt(gradeStr);
        Society society = SocietyUtils.getSocietysByID(sid);

        if(!SocietyUtils.societies.contains(society)){
            player.showFormWindow(WindowManager.getMessageWindow("§c此公会已经被解散,请设置其他公会",null,"关闭"));
            return;
        }

        SocietyUtils.societies.remove(society);
        society.setGrade(grade);
        SocietyUtils.societies.add(society);
        society.saveData();

        player.showFormWindow(WindowManager.getMessageWindow("§a成功设置 §b"+society.getSocietyName()+" §a等级为 §e"+society.getGrade(),null,"关闭"));
    }
}
