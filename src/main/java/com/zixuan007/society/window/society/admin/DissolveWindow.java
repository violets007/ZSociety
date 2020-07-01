package com.zixuan007.society.window.society.admin;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

import java.util.ArrayList;

/**
 * @author zixuan007
 */
public class DissolveWindow extends SimpleWindow implements WindowLoader {

    public DissolveWindow() {
        super(PluginUtils.getWindowConfigInfo("dissolveWindow.title"), "");

    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        ArrayList<Society> societies = SocietyUtils.societies;
        if (societies.size() > 0) {
            for (Society society : societies) {
                String buttonContent = society.getSocietyName();
                addButton(new ElementButton(buttonContent));
            }
        } else {
            setContent("没有可以设置的公会");
        }
        return this;
    }

    @Override
    public void onClick(int id, Player player) {
        ElementButton elementButton = getButtons().get(id);
        String sidStr = elementButton.getText().split(" ")[0];
        int sid = Integer.parseInt(sidStr);
        Society society = SocietyUtils.getSocietysByID(sid);
        String closeButtonName=PluginUtils.getWindowConfigInfo("messageWindow.close.button");
        String closeImagePath=PluginUtils.getWindowConfigInfo("messageWindow.close.button.imgPath");

        if(!SocietyUtils.societies.contains(society)){
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,"§c此公会已经被解散,请设置其他公会",null,closeButtonName,closeImagePath));
            return;
        }

        SocietyUtils.removeSocietyShopBySid(society);
        SocietyUtils.societies.remove(society);
        SocietyUtils.removeSociety(society.getSocietyName());
        player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,"§a成功解散 §b"+society.getSocietyName()+" §a公会",null,closeButtonName,closeImagePath));

    }


}
