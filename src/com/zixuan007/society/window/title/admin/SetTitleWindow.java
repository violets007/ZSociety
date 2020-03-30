package com.zixuan007.society.window.title.admin;
import cn.nukkit.Player;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowManager;

public class SetTitleWindow extends CustomWindow {
    public SetTitleWindow() {
        super((String)SocietyPlugin.getInstance().getLangConfig().get("设置称号窗口标题"));
        addElement((Element)new ElementInput("", "玩家名字"));
        addElement((Element)new ElementInput("", "称号"));
    }

    public void onClick(FormResponseCustom response, Player player) {
        String playerName = response.getInputResponse(0);
        String title = response.getInputResponse(1);
        SocietyPlugin societyPlugin = SocietyPlugin.getInstance();
        if (societyPlugin.getTitleConfig().get(playerName) == null) {
            player.showFormWindow((FormWindow)WindowManager.getMessageWindow("§c输入的玩家名字不存在", (FormWindow)this, "返回上级"));
            return;
        }
        if (title.equals("") || title.equals(" ")) {
            player.showFormWindow((FormWindow)WindowManager.getMessageWindow("§c称号内容不能为空", (FormWindow)this, "返回上级"));
            return;
        }
        Config titleConfig = societyPlugin.getTitleConfig();
        titleConfig.set(playerName, title);
        titleConfig.save();
        player.showFormWindow((FormWindow)WindowManager.getMessageWindow("§a成功设置 §b" + playerName + " §a的称号为 §e" + title, (FormWindow)this, "返回上级"));
    }
}