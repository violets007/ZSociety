package com.zixuan007.society.window.society;

import cn.nukkit.Player;
import com.zixuan007.society.window.SimpleWindow;

public class MessageWindow extends SimpleWindow {
    public MessageWindow(String title, String content) {
        super(title, content);
    }

    public void onClick(int id, Player player) {
        if (getButtons().get(0) != null && getParent() != null)
            player.showFormWindow(getParent());
    }
}


