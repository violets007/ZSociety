package com.zixuan007.society.window.title.admin;
import cn.nukkit.Player;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.TitleUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author zixuan007
 */
public class SetTitleWindow extends CustomWindow implements WindowLoader {
    public SetTitleWindow() {
        super(PluginUtils.getWindowConfigInfo("setTitleWindow.title"));
    }

    @Override
    public FormWindow init(Object... objects) {
        getElements().clear();
        Config titleConfig = SocietyPlugin.getInstance().getTitleConfig();
        ArrayList<String> players = new ArrayList<>();
        for (Map.Entry<String, Object> entry : titleConfig.getAll().entrySet()) {
           players.add(entry.getKey());
        }
        addElement(new ElementDropdown("",players));
        addElement(new ElementInput("", "称号"));
        return this;
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {
        String playerName = response.getDropdownResponse(0).getElementContent();
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