package com.zixuan007.society.window.society;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Lang;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.event.society.PlayerCreateSocietyEvent;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowManager;

import java.util.ArrayList;
import java.util.HashMap;
import me.onebone.economyapi.EconomyAPI;

public class CreateSocietyWindow extends CustomWindow {

    public CreateSocietyWindow() {
        super(Lang.createSocietyWindow_Title);
        this.addElement(new ElementInput("", "公会名称"));
    }

    public void onClick(FormResponseCustom response, Player player) {
        SocietyPlugin societyPlugin = SocietyPlugin.getInstance();
        String societyName = response.getInputResponse(0);
        Boolean societyNameExist = SocietyUtils.isSocietyNameExist(societyName);
        MessageWindow messageWindow = null;
        if (societyNameExist) {
            messageWindow = WindowManager.getMessageWindow("§c此公会名称已经存在", this, "返回上级");
            player.showFormWindow(messageWindow);
        } else {
            Double createSocietyMoney=null;
            if(societyPlugin.getConfig().get("createSocietyMoney") instanceof Integer){
                createSocietyMoney=((Integer) societyPlugin.getConfig().get("createSocietyMoney")).doubleValue();
            }else{
                createSocietyMoney= (Double) societyPlugin.getConfig().get("createSocietyMoney");
            }
            double myMoney = EconomyAPI.getInstance().myMoney(player);
            if (myMoney < createSocietyMoney) {
                messageWindow = WindowManager.getMessageWindow("§c当前余额不足,创建公会需要: §l§a" + createSocietyMoney, this, "返回上级");
                player.showFormWindow(messageWindow);
            } else if (societyName != null && !societyName.equals("")) {
                EconomyAPI.getInstance().reduceMoney(player, createSocietyMoney, true);
                long count = SocietyUtils.getNextSid();
                Society society = new Society(count, societyName, player.getName(), SocietyUtils.getFormatDateTime(), 0.0D, new HashMap());
                societyPlugin.getServer().getPluginManager().callEvent(new PlayerCreateSocietyEvent(player, society));
            } else {
                messageWindow = WindowManager.getMessageWindow("§c公会名称不能为空", this, "返回上级");
                player.showFormWindow(messageWindow);
            }
        }
    }
}