package com.zixuan007.society.window.society;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.event.society.PlayerQuitSocietyEvent;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.ModalWindow;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.society.president.PresidentWindow;
import java.util.Arrays;

public class SocietyWindow extends SimpleWindow {
    public SocietyWindow(Player player) {
        super((String)SocietyPlugin.getInstance().getLangConfig().get("玩家公会功能窗口标题"), "");
        this.addButton(new ElementButton("创建公会"));
        this.addButton(new ElementButton("管理公会"));
        this.addButton(new ElementButton("退出公会"));
        this.addButton(new ElementButton("加入公会"));
        this.addButton(new ElementButton("成员列表"));
        this.addButton(new ElementButton("公会贡献排行榜"));
        this.addButton(new ElementButton("公会等级排行榜"));
        this.addButton(new ElementButton("贡献经济"));
    }

    public void onClick(int id, Player player) {
        int clickedButtonId = this.getResponse().getClickedButtonId();
        Society society = SocietyUtils.getSocietyByPlayerName(player.getName());
        MessageWindow messageWindow = null;
        switch(clickedButtonId) {
            case 0:
                if (society != null) {
                    messageWindow = WindowManager.getMessageWindow("§c你已经存在公会,无法创建请先退出当前公会", this, "确认返回");
                    player.showFormWindow(messageWindow);
                    return;
                }

                CreateSocietyWindow createSocietyWindow = new CreateSocietyWindow();
                WindowManager.getCreateSocietyWindow(true);
                createSocietyWindow.setParent(this);
                createSocietyWindow.setBack(true);
                player.showFormWindow(createSocietyWindow);
                break;
            case 1:
                if (!SocietyUtils.isChairman(player.getName())) {
                    messageWindow = WindowManager.getMessageWindow("§c您没有权限管理公会", this, "确认返回");
                    player.showFormWindow(messageWindow);
                    return;
                }

                PresidentWindow chairmanWindow = WindowManager.getChairmanWindow(society.getSid());
                player.showFormWindow(chairmanWindow);
                break;
            case 2:
                boolean isJoinSociety = SocietyUtils.isJoinSociety(player.getName());
                if (!isJoinSociety) {
                    messageWindow = WindowManager.getMessageWindow("§c当前还没加入公会请先加入公会", this, "返回上级");
                    player.showFormWindow(messageWindow);
                    return;
                }

                if (society.getPresidentName().equals(player.getName())) {
                    messageWindow = WindowManager.getMessageWindow("§c你是会长无法退出公会,请解散公会!", this, "返回上级");
                    player.showFormWindow(messageWindow);
                    return;
                }

                ModalWindow modalWindow = WindowManager.getAffrimWindow("§c您确认要退出 §b" + society.getSocietyName() + " §c公会吗?", "§a确认退出", "§c取消退出");
                modalWindow.setButtonClickedListener((affrim, player1) -> {
                    if (affrim) {
                        Society society1 = SocietyUtils.getSocietyByPlayerName(player1.getName());
                        WindowManager.societyPlugin.getServer().getPluginManager().callEvent(new PlayerQuitSocietyEvent(player1, society1));
                    } else {
                        player.showFormWindow(this);
                    }

                });
                player.showFormWindow(modalWindow);
                break;
            case 3:
                if (WindowManager.societyPlugin.getSocieties().size() == 0) {
                    messageWindow = WindowManager.getMessageWindow("§c当前还没有公会", this, "确认返回");
                    player.showFormWindow(messageWindow);
                    return;
                }

                SocietyListWindow societyListWindow = WindowManager.getSocietyListWindow(1, this);
                player.showFormWindow(societyListWindow);
                break;
            case 4:
                if (WindowManager.societyPlugin.getSocieties().size() == 0) {
                    messageWindow = WindowManager.getMessageWindow("§c当前还没加入公会请先加入公会", this, "确认返回");
                    player.showFormWindow(messageWindow);
                    return;
                }

                MemberListWindow memberListWindow = WindowManager.getMemberListWindow(society, Arrays.asList(society.getPost().keySet().toArray(new String[society.getPost().keySet().size()])), this);
                memberListWindow.setBack(true);
                player.showFormWindow(memberListWindow);
                break;
            case 5:
                ContributionRankingWindow contributionRankingWindow = WindowManager.getContributionRankingWindow(this);
                player.showFormWindow(contributionRankingWindow);
                break;
            case 6:
                LevelRankWindow levelRankWindow = WindowManager.getLevelRankWindow(this);
                player.showFormWindow(levelRankWindow);
                break;
            case 7:
                if (WindowManager.societyPlugin.getSocieties().size() == 0) {
                    messageWindow = WindowManager.getMessageWindow("§c当前还没加入公会请先加入公会", this, "确认返回");
                    player.showFormWindow(messageWindow);
                    return;
                }

                ContributionWindow contributionWindow = WindowManager.getContributionWindow(society.getSid());
                contributionWindow.setParent(this);
                player.showFormWindow(contributionWindow);
        }

    }
}
