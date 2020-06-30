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

        if(!SocietyUtils.societies.contains(society)){
            player.showFormWindow(WindowManager.getMessageWindow("§c此公会已经被解散,请设置其他公会",null,"关闭"));
            return;
        }

        SocietyUtils.removeSocietyShopBySid(society);
        SocietyUtils.societies.remove(society);
        SocietyUtils.removeSociety(society.getSocietyName());

        player.showFormWindow(WindowManager.getMessageWindow("§a成功解散 §b"+society.getSocietyName()+" §a公会",null,"关闭"));

    }


}
