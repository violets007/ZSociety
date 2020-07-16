package com.zixuan007.society.window.society.president;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowLoader;

import java.util.ArrayList;

/**
 * @author zixuan007
 */
public class SendSocietyWarWindow extends CustomWindow implements WindowLoader {

    public SendSocietyWarWindow() {
        super( PluginUtils.getWindowConfigInfo("sendSocietyWarWindow.title"));
    }

    @Override
    public FormWindow init(Object... objects) {
        getElements().clear();
        Player player = (Player) objects[0];
        ArrayList<String> arrayList = new ArrayList<>();

        for (Society configSociety : SocietyUtils.societies) {
            if(configSociety.getPresidentName().equals(player.getName())){
               continue;
            }
            arrayList.add(configSociety.getSid()+" "+configSociety.getSocietyName());
        }
        addElement(new ElementDropdown("发起公会战争的列表",arrayList));
        addElement(new ElementInput("","发起公会战争的金额"));

        return this;
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {

    }
}
