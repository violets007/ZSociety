package com.zixuan007.society.window.society.president;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.pojo.Society;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author zixuan007
 * @description: 设置公会职位
 * @date: 2021/7/17 下午4:18
 */
public class SetRightsWindow extends CustomWindow implements WindowLoader {

    public SetRightsWindow() {
        super(PluginUtils.getWindowConfigInfo("setRightsWindow.title"));
    }

    @Override
    public FormWindow init(Object... objects) {
        addElement(new ElementInput("", "§e玩家名称"));

        List<HashMap<String, Object>> configPostList = SocietyPlugin.getInstance().getConfig().getList("职位");
        addElement(new ElementDropdown("§e公会职位", configPostList.stream().map((post) -> {
            return post.get("名称").toString();
        }).collect(Collectors.toList())));

        return this;
    }


    @Override
    public void onClick(FormResponseCustom response, Player player) {
        String playerName = response.getInputResponse(0);
        List<HashMap<String, Object>> configPostList = SocietyPlugin.getInstance().getConfig().getList("职位");

        int elementID = response.getDropdownResponse(1).getElementID();
        String jobName = configPostList.get(elementID).get("名称").toString();
        int jobGrade = (int) configPostList.get(elementID).get("等级");

        FormWindow setRightsWindow = WindowManager.getFormWindow(WindowType.SET_RIGHTS_WINDOW);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");

        if (player.getName().equals(playerName)) {
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.setRightsWindow.isMe"), setRightsWindow, backButtonName, backButtonImage));
            return;
        }

        if (!SocietyUtils.hasSociety(playerName)) {
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.setRightsWindow.isJoinSociety"), setRightsWindow, backButtonName, backButtonImage));
            return;
        }

        Society society = SocietyUtils.getSocietyByPlayerName(playerName);

        if (SocietyUtils.hasPositionExceeds(society, jobName)) {
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.setRightsWindow.positionExceeds"), setRightsWindow, backButtonName, backButtonImage));
            return;
        }


        if (society == null || society.getPost() == null || !society.getPost().containsKey(playerName)) {
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.setRightsWindow.notSocietyMember"), setRightsWindow, backButtonName, backButtonImage));
            return;
        }

        for (ArrayList<Object> arrayList : society.getMembers().values()) {
            String postName = (String) arrayList.get(0);
            Integer postGrade = (Integer) arrayList.get(1);
            if (postGrade == jobGrade && !postName.equals(jobName)) {
                player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.setRightsWindow.existJobGrade"), setRightsWindow, backButtonName, backButtonImage));
                return;
            }
        }

        society.getPost().put(playerName, new ArrayList() {
            {
                add(jobName);
                add(jobGrade);
            }
        });
        SocietyUtils.saveSociety(society);

        if (PluginUtils.isOnlineByName(playerName)) {
            Server.getInstance().getPlayer(playerName).sendTitle(PluginUtils.getLanguageInfo("message.setRightsWindow.upJob", new String[]{"${jobName}"}, new String[]{jobName}));
        }
        player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.setRightsWindow.setJob", new String[]{"${playerName}", "${jobName}"}, new String[]{playerName, jobName}), setRightsWindow, backButtonName, backButtonImage));
    }


}


