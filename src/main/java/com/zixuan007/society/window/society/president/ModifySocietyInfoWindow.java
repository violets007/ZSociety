package com.zixuan007.society.window.society.president;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

/**
 * @author zixuan007
 */
public class ModifySocietyInfoWindow extends CustomWindow implements WindowLoader {

    public ModifySocietyInfoWindow() {
        super(PluginUtils.getWindowConfigInfo("modifySocietyInfoWindow.title"));
    }

    @Override
    public FormWindow init(Object... objects) {
        getElements().clear();
        Player player = (Player) objects[0];
        Society society = SocietyUtils.getSocietyByPlayerName(player.getName());
        addElement(new ElementInput("",society.getDescription() == null ? "§e请设置第一次的公会描述内容": society.getDescription()));

        return this;
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {
        String description = response.getInputResponse(0);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");


        if(description == null || description.length() == 0){
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,PluginUtils.getLanguageInfo("message.modifySocietyInfoWindow.notDescription"), WindowManager.getFormWindow(WindowType.MODIFY_SOCIETY_INFO_WINDOW,player), backButtonName, backButtonImage));
            return;
        }

        Society society = SocietyUtils.getSocietyByPlayerName(player.getName());
        society.setDescription(description);
        SocietyUtils.saveSociety(society);
        player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,PluginUtils.getLanguageInfo("message.modifySocietyInfoWindow.success"), WindowManager.getFormWindow(WindowType.PRESIDENT_WINDOW,player), backButtonName, backButtonImage));
    }
}
