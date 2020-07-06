package com.zixuan007.society.window.marry.admin;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.domain.Marry;
import com.zixuan007.society.utils.MarryUtils;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

import java.util.ArrayList;

/**
 * @author zixuan007
 */
public class RemoveMarryWindow extends SimpleWindow implements WindowLoader {

    public RemoveMarryWindow() {
        super( PluginUtils.getWindowConfigInfo("removeMarryWindow.title"), "");
    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        if (MarryUtils.marrys.size() > 0) {
            for (Marry marry : MarryUtils.marrys) {
                addButton(new ElementButton(marry.getMid()+" 丈夫: §b"+marry.getPropose()+"§r 妻子: §a"+marry.getRecipient()));
            }
        }else{
            setContent("当前还没有一对夫妻");
        }
        return this;
    }

    @Override
    public void onClick(int id, Player player) {
        String closeButtonName = PluginUtils.getWindowConfigInfo("messageWindow.close.button");
        String closeButtonImagePath = PluginUtils.getWindowConfigInfo("messageWindow.close.button.imgPath");
        if(getButtons().size() < 1) {
            return;
        }
        String midStr = getButtons().get(id).getText().split(" ")[0];
        int mid = Integer.parseInt(midStr);
        Marry marry = MarryUtils.getMarryById(mid);
        MarryUtils.removeMarry(marry);
        player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.removeMarryWindow.remove",new String[]{"${mid}"},new String[]{mid+""}),null,closeButtonName,closeButtonImagePath));
    }


}
