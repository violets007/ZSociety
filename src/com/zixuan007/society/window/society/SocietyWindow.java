package com.zixuan007.society.window.society;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import com.zixuan007.society.domain.Lang;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.event.society.PlayerQuitSocietyEvent;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.ModalWindow;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.society.president.PresidentWindow;
import java.util.Arrays;
import java.util.List;

public class SocietyWindow extends SimpleWindow {
    public SocietyWindow(Player player) {
        super(Lang.societyWindow_Title, "");
        init();
    }

    /**
     * 初始化窗口按钮
     */
    public void init(){
        ElementButtonImageData buttonImageData = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, Lang.societyWindow_CreateSocietyButton_ImgPath);
        this.addButton(new ElementButton(Lang.societyWindow_CreateSocietyButton,buttonImageData));
        buttonImageData=new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, Lang.societyWindow_ManagerSocietyButton_ImgPath);
        this.addButton(new ElementButton(Lang.societyWindow_ManagerSocietyButton,buttonImageData));
        buttonImageData=new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH,Lang.societyWindow_QuitSocietyButton_ImgPath);
        this.addButton(new ElementButton(Lang.societyWindow_QuitSocietyButton,buttonImageData));
        buttonImageData=new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH,Lang.societyWindow_JoinSocietyButton_ImgPath);
        this.addButton(new ElementButton(Lang.societyWindow_JoinSocietyButton,buttonImageData));
        buttonImageData=new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH,Lang.societyWindow_MenberListButton_ImgPath);
        this.addButton(new ElementButton(Lang.societyWindow_MenberListButton,buttonImageData));
        buttonImageData=new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH,Lang.societyWindow_ContributionRankingButton_ImgPath);
        this.addButton(new ElementButton(Lang.societyWindow_ContributionRankingButton,buttonImageData));
        buttonImageData=new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH,Lang.societyWindow_LevelRankButton_ImgPath);
        this.addButton(new ElementButton(Lang.societyWindow_LevelRankButton,buttonImageData));
        buttonImageData=new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH,Lang.societyWindow_ContributionButton_ImgPath);
        this.addButton(new ElementButton(Lang.societyWindow_ContributionButton,buttonImageData));
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
                        SocietyUtils.sendMemberTitle("§a玩家 §b"+player1.getName()+" §a成功退出公会",society);
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
                List<String> postList = Arrays.asList(society.getPost().keySet().toArray(new String[society.getPost().keySet().size()]));
                MemberListWindow memberListWindow = WindowManager.getMemberListWindow(society,postList, this);
                memberListWindow.setBack(true);
                memberListWindow.setParent(this);
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
                contributionWindow.setBack(true);
                contributionWindow.setParent(this);
                player.showFormWindow(contributionWindow);
        }

    }
}
