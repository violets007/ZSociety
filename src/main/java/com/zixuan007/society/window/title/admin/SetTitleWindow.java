package com.zixuan007.society.window.title.admin;
import cn.nukkit.Player;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Lang;
import com.zixuan007.society.utils.TitleUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowManager;

import java.util.ArrayList;

public class SetTitleWindow extends CustomWindow {
    public SetTitleWindow() {
        super(Lang.setTitleWindow_title);
        addElement(new ElementInput("", "玩家名字"));
        addElement(new ElementInput("", "称号"));
    }

    public void onClick(FormResponseCustom response, Player player) {
        String playerName = response.getInputResponse(0);
        String title = response.getInputResponse(1);
        SocietyPlugin societyPlugin = SocietyPlugin.getInstance();
        if (societyPlugin.getTitleConfig().get(playerName) == null) {
            player.showFormWindow(WindowManager.getMessageWindow("§c输入的玩家名字不存在",this, "返回上级"));
            return;
        }
        if (title.trim().equals("") || title.equals(" ")) {
            player.showFormWindow(WindowManager.getMessageWindow("§c称号内容不能为空", this, "返回上级"));
            return;
        }
        title=title.replaceAll(" ","");
        ArrayList<String> arrayList = TitleUtils.titleList.get(playerName);
        if(TitleUtils.isExistTitle(playerName,title)){
            player.showFormWindow(WindowManager.getMessageWindow("§c玩家 §b"+playerName+" 已经拥有此称号,请勿重复设置", this, "返回上级"));
            return;
        }
        Config titleConfig = societyPlugin.getTitleConfig();
        TitleUtils.addTitle(playerName,title);
        titleConfig.set(playerName, TitleUtils.getTitles(playerName));
        titleConfig.save();
        player.showFormWindow(WindowManager.getMessageWindow("§a成功设置 §b" + playerName + " §a的称号为 §e" + title, this, "返回上级"));
    }
}