package com.zixuan007.society.window.title;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import com.zixuan007.society.domain.Lang;
import com.zixuan007.society.utils.TitleUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowManager;

import java.util.ArrayList;

public class TitleWindow extends SimpleWindow {
    private String playerName;
    public TitleWindow(String playerName) {
        super(Lang.titleWindow_title, "");
        this.playerName=playerName;
        for (String title : TitleUtils.titleList.get(playerName)) {
            addButton(new ElementButton(title));
        }
    }

    @Override
    public void onClick(int id, Player player) {
        ArrayList<String> titles = TitleUtils.titleList.get(player.getName());
        if(titles.size() <= id ){
            player.showFormWindow(WindowManager.getMessageWindow("§a佩戴的称号已经被管理员移除",null,"关闭窗口"));
            return;
        }
        String title = titles.get(id);
        titles.remove(title);
        TitleUtils.addTitle(playerName,title);
        TitleUtils.titleList.put(playerName,titles);
        player.showFormWindow(WindowManager.getMessageWindow("§a成功佩戴 §l"+title+" §r§a称号",null,"关闭窗口"));
    }
}
