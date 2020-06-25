package com.zixuan007.society.window.society.admin;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import com.zixuan007.society.domain.Lang;
import com.zixuan007.society.window.SimpleWindow;


/**
 * @author zixuan007
 */
public class SocietyAdminWindow extends SimpleWindow {

    public SocietyAdminWindow() {
        super(Lang.societyAdminWindow_Title, "");
        ElementButtonImageData imgData = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, Lang.societyAdminWindow_Button_ImgPath);
        addButton(new ElementButton("设置公会等级",imgData));
        addButton(new ElementButton("设置公会贡献值",imgData));
        addButton(new ElementButton("解散指定公会",imgData));
    }

    @Override
    public void onClick(int id, Player player) {
        switch (id){
            case 0:
                player.showFormWindow(new SetGradeWindow());
                break;
            case 1:
                player.showFormWindow(new SetContributeWindow());
                break;
            case 2:
                player.showFormWindow(new DissolveWindow());
                break;
        }
    }
}


