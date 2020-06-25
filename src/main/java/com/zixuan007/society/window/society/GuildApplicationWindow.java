package com.zixuan007.society.window.society;

import cn.nukkit.Player;
import cn.nukkit.event.Event;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseData;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Lang;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.event.society.PlayerApplyJoinSocietyEvent;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowManager;

public class GuildApplicationWindow extends CustomWindow {
    private static SocietyPlugin societyPlugin = SocietyPlugin.getInstance();

    public GuildApplicationWindow() {
        super(Lang.guildApplication_Title);
        addElement((Element)new ElementLabel("§c建议先查看公会列表"));
        ElementDropdown elementDropdown = new ElementDropdown("§b公会SID");
        SocietyUtils.societies.forEach(society -> elementDropdown.addOption(society.getSid() + ""));
        addElement((Element)elementDropdown);
    }


    public void onClick(FormResponseCustom response, Player player) {
        FormResponseData dropdownResponse = response.getDropdownResponse(1);
        String elementContent = dropdownResponse.getElementContent();
        Long sid = Long.valueOf(Long.parseLong(elementContent));
        Society society = SocietyUtils.getSocietysByID(sid.longValue());
        societyPlugin.getServer().getPluginManager().callEvent((Event)new PlayerApplyJoinSocietyEvent(player, society));
        MessageWindow messageWindow = WindowManager.getMessageWindow("§a成功申请SID §b" + society.getSid() + " §a公会名称为 §b" + society.getSocietyName(), getParent(), "返回主界面");
        player.showFormWindow((FormWindow)messageWindow);
    }
}

