package com.zixuan007.society.window.society;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.event.society.PlayerQuitSocietyEvent;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.ModalWindow;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.society.president.PresidentWindow;

import java.util.Arrays;
import java.util.List;

/**
 * @author zixuan007
 */
public class SocietyWindow extends SimpleWindow implements WindowLoader {
    public SocietyWindow() {
        super(PluginUtils.getWindowConfigInfo("societyWindow.title"), "");
    }


    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        ElementButtonImageData img1 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyWindow.createSociety.button.imgPath"));
        ElementButtonImageData img2 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyWindow.managerSociety.button.imgPath"));
        ElementButtonImageData img3 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyWindow.quitSociety.button.imgPath"));
        ElementButtonImageData img4 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyWindow.joinSociety.button.imgPath"));
        ElementButtonImageData img5 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyWindow.memberList.button.imgPath"));
        ElementButtonImageData img6 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyWindow.contributionRanking.button.imgPath"));
        ElementButtonImageData img7 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyWindow.levelRank.button.imgPath"));
        ElementButtonImageData img8 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyWindow.contribution.button.imgPath"));
        ElementButtonImageData img9 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyWindow.createShopWindow.button.imgPath"));

        this.addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyWindow.createSociety.button"), img1));
        this.addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyWindow.managerSociety.button"), img2));
        this.addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyWindow.quitSociety.button"), img3));
        this.addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyWindow.joinSociety.button"), img4));
        this.addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyWindow.memberList.button"), img5));
        this.addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyWindow.contributionRanking.button"), img6));
        this.addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyWindow.levelRank.button"), img7));
        this.addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyWindow.contribution.button"), img8));
        this.addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyWindow.createShopWindow.button"), img9));
        return this;
    }

    @Override
    public void onClick(int id, Player player) {
        int clickedButtonId = this.getResponse().getClickedButtonId();
        Society society = SocietyUtils.getSocietyByPlayerName(player.getName());
        MessageWindow messageWindow ;
        switch (clickedButtonId) {
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

                if (society == null || society.getPresidentName().equals(player.getName())) {
                    messageWindow = WindowManager.getMessageWindow("§c你是会长无法退出公会,请解散公会!", this, "返回上级");
                    player.showFormWindow(messageWindow);
                    return;
                }

                ModalWindow modalWindow = WindowManager.getAffrimWindow("§c您确认要退出 §b" + society.getSocietyName() + " §c公会吗?", "§a确认退出", "§c取消退出");
                modalWindow.setButtonClickedListener((affrim, player1) -> {
                    if (affrim) {
                        SocietyUtils.sendMemberTitle("§a玩家 §b" + player1.getName() + " §a成功退出公会", society);
                        Society society1 = SocietyUtils.getSocietyByPlayerName(player1.getName());
                        WindowManager.societyPlugin.getServer().getPluginManager().callEvent(new PlayerQuitSocietyEvent(player1, society1));
                    } else {
                        player.showFormWindow(this);
                    }

                });
                player.showFormWindow(modalWindow);
                break;
            case 3:
                if (SocietyUtils.societies == null || SocietyUtils.societies.size() <= 0) {
                    messageWindow = WindowManager.getMessageWindow("§c当前还没有公会", this, "确认返回");
                    player.showFormWindow(messageWindow);
                    return;
                }

                SocietyListWindow societyListWindow = WindowManager.getSocietyListWindow(1, this);
                player.showFormWindow(societyListWindow);
                break;
            case 4:
                if (society == null || !SocietyUtils.isJoinSociety(player.getName())) {
                    messageWindow = WindowManager.getMessageWindow("§c当前还没加入公会请先加入公会", this, "确认返回");
                    player.showFormWindow(messageWindow);
                    return;
                }
                List<String> postList = Arrays.asList(society.getPost().keySet().toArray(new String[0]));
                MemberListWindow memberListWindow = WindowManager.getMemberListWindow(society, postList, this);
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
                if (society == null || !SocietyUtils.isJoinSociety(player.getName())) {
                    messageWindow = WindowManager.getMessageWindow("§c当前还没加入公会请先加入公会", this, "确认返回");
                    player.showFormWindow(messageWindow);
                    return;
                }
                ContributionWindow contributionWindow = WindowManager.getContributionWindow(society.getSid());
                contributionWindow.setBack(true);
                contributionWindow.setParent(this);
                player.showFormWindow(contributionWindow);
                break;
            case 8:
                if (!SocietyUtils.isJoinSociety(player.getName())) {
                    player.showFormWindow(WindowManager.getMessageWindow("§c当前还没加入公会,请先加入公会", this, "确认返回"));
                    return;
                }
                player.showFormWindow(WindowManager.getCreateShopWindow(player));
                break;
            default:
                break;
        }

    }


}
