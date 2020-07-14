package com.zixuan007.society.window.society;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;

/**
 * @author zixuan007
 */
public class MessageWindow extends SimpleWindow implements WindowLoader {
    public MessageWindow() {
        super(PluginUtils.getWindowConfigInfo("messageWindow.title"), "");
    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        String message = (String) objects[0];
        FormWindow formWindow = (FormWindow) objects[1];
        String buttonName = (String) objects[2];
        String imgPath = (String) objects[3];
        setContent(message);
        addButton(new ElementButton(buttonName, new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, imgPath)));
        setParent(formWindow);
        return this;
    }

    @Override
    public void onClick(int id, Player player) {
        if (getButtons().get(0) != null && getParent() != null) {
            player.showFormWindow(getParent());
        }
    }


}


