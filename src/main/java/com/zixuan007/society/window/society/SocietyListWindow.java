package com.zixuan007.society.window.society;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.event.society.PlayerApplyJoinSocietyEvent;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zixuan007
 */
public class SocietyListWindow extends SimpleWindow implements WindowLoader {
    private List<Society> societyList;
    private int currentPage;
    private int limit = 10;
    private int totalPage;

    public SocietyListWindow() {
        super(PluginUtils.getWindowConfigInfo("societyListWindow.title"), "");


    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        String content= (String) objects[0];
        int currentPage = (int) objects[1];
        int totalPage = (int) objects[2];

        setContent(content);
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        if (currentPage != 1) {
            addButton(new ElementButton("上一页"));
        }

        this.societyList = (List<Society>) objects[3];
        for (Society society : societyList) {
            addButton(new ElementButton("§e公会ID §b" + society.getSid() + " §e公会名称 §b" + society.getSocietyName() + " §e会长 §b" + society.getPresidentName()+" §6lv_§0"+society.getGrade()));
        }
        if (currentPage < totalPage) {
            addButton(new ElementButton("下一页"));
        }

        if(objects.length >= 4 && objects[4] != null){
            setParent((FormWindow) objects[4]);
            setBack(true);
        }
        return this;
    }


    public void nextPage(int currentPage, Player player) {
        SocietyListWindow societyListWindow = WindowManager.getSocietyListWindow(++currentPage, WindowType.SOCIETY_WINDOW);
        player.showFormWindow(societyListWindow);
    }

    public void upPage(int currentPage, Player player) {
        SocietyListWindow societyListWindow = WindowManager.getSocietyListWindow(--currentPage, WindowType.SOCIETY_WINDOW);
        player.showFormWindow(societyListWindow);
    }



    @Override
    public void onClick(int id, Player player) {
        Society society;
        FormWindow societyWindow = WindowManager.getFormWindow(WindowType.SOCIETY_WINDOW);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");
        if (id == 0 && this.currentPage != 1) {
            upPage(this.currentPage, player);
            return;
        }
        if (id == limit) {
            nextPage(this.currentPage, player);
            return;
        }
        if (SocietyUtils.isJoinSociety(player.getName())) {
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.societyListWindow.isJoinSociety"), societyWindow, backButtonName, backButtonImage));
            return;
        }
        ModalWindow affirmWindow;

        if (this.currentPage == 1) {
            society = this.societyList.get(id);

            affirmWindow = (ModalWindow) WindowManager.getFormWindow(WindowType.MODAL_WINDOW,"§e您确定要加入 §b" + (this.societyList.get(id)).getSocietyName() + " §e公会吗?","§a确认加入","§c取消加入");
        } else {
            society = this.societyList.get(id - 1);
            affirmWindow = (ModalWindow) WindowManager.getFormWindow(WindowType.MODAL_WINDOW,"§e您确定要加入 §b" + (this.societyList.get(id - 1)).getSocietyName() + " §e公会吗?","§a确认加入","§c取消加入");
        }
        affirmWindow.setButtonClickedListener((affirm, player1) -> {
            if (affirm) {
                if (PluginUtils.isOnlineByName(society.getPresidentName())) {
                    String playerName = player1.getName();
                    Server.getInstance().getPlayer(society.getPresidentName()).sendMessage(PluginUtils.getLanguageInfo("message.societyListWindow.playerApplyJoinSociety",new String[]{"${playerName}"},new String[]{playerName}));
                }
                String key = "等级" + society.getGrade();
                ArrayList<Object> configGrades = (ArrayList<Object>) SocietyPlugin.getInstance().getConfig().get(key);
                Integer maxMemberCount = (Integer) configGrades.get(0);
                int societyMemberCount = society.getMembers().size();
                if(societyMemberCount >= maxMemberCount){
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.societyListWindow.overtakeMaxMember"), societyWindow, backButtonName, backButtonImage));
                    return;
                }
                SocietyPlugin.getInstance().getServer().getPluginManager().callEvent(new PlayerApplyJoinSocietyEvent(player1, society));
            } else {
                player1.showFormWindow(getParent());
            }
        });

        player.showFormWindow(affirmWindow);
    }




}


