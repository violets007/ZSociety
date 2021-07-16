package com.zixuan007.society.window.society;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import com.zixuan007.society.pojo.Society;
import com.zixuan007.society.event.society.PlayerQuitSocietyEvent;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.*;

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
        Player player = (Player) objects[0];
        ElementButtonImageData img1 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyWindow.createSociety.button.imgPath"));
        ElementButtonImageData img2 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyWindow.managerSociety.button.imgPath"));
        ElementButtonImageData img3 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyWindow.quitSociety.button.imgPath"));
        ElementButtonImageData img4 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyWindow.joinSociety.button.imgPath"));
        ElementButtonImageData img5 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyWindow.memberList.button.imgPath"));
        ElementButtonImageData img6 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyWindow.contributionRanking.button.imgPath"));
        ElementButtonImageData img7 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyWindow.levelRank.button.imgPath"));
        ElementButtonImageData img8 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyWindow.contribution.button.imgPath"));
        ElementButtonImageData img9 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyWindow.createShopWindow.button.imgPath"));
        ElementButtonImageData img10 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyWindow.societyChat.button.imgPath"));
        ElementButtonImageData img11 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyWindow.tpaSociety.button.imgPath"));
        ElementButtonImageData img12 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("societyWindow.societyInfo.button.imgPath"));


        this.addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyWindow.createSociety.button"), img1));
        this.addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyWindow.managerSociety.button"), img2));
        this.addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyWindow.joinSociety.button"), img4));
        this.addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyWindow.quitSociety.button"), img3));
        this.addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyWindow.memberList.button"), img5));
        this.addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyWindow.contributionRanking.button"), img6));
        this.addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyWindow.levelRank.button"), img7));
        this.addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyWindow.contribution.button"), img8));
        this.addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyWindow.createShopWindow.button"), img9));
        this.addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyWindow.societyChat.button"), img10));
        this.addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyWindow.tpaSociety.button"), img11));
        this.addButton(new ElementButton(PluginUtils.getWindowConfigInfo("societyWindow.societyInfo.button"), img12));


        return this;
    }

    @Override
    public void onClick(int id, Player player) {
        int clickedButtonId = this.getResponse().getClickedButtonId();
        Society society = SocietyUtils.getSocietyByPlayerName(player.getName());
        FormWindow societyWindow = WindowManager.getFormWindow(WindowType.SOCIETY_WINDOW, player);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");
        switch (clickedButtonId) {
            case 0:
                if (society != null) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.societyWindow.existSociety"), societyWindow, backButtonName, backButtonImage));
                    return;
                }
                player.showFormWindow(WindowManager.getFormWindow(WindowType.CREATE_SOCIETY_WINDOW, societyWindow));
                break;
            case 1:
                if (!SocietyUtils.hasChairman(player.getName())) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.societyWindow.notPresident"), societyWindow, backButtonName, backButtonImage));
                    return;
                }
                player.showFormWindow(WindowManager.getFormWindow(WindowType.PRESIDENT_WINDOW, player));
                break;
            case 2:

                if (SocietyUtils.getSocieties() == null || SocietyUtils.getSocieties().size() <= 0) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.societyWindow.doesNotExist"), societyWindow, backButtonName, backButtonImage));
                    return;
                }

                SocietyListWindow societyListWindow = WindowManager.getSocietyListWindow(1, WindowType.SOCIETY_WINDOW, player);
                player.showFormWindow(societyListWindow);
                break;

            case 3:

                boolean isJoinSociety = SocietyUtils.hasSociety(player.getName());
                if (!isJoinSociety) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.societyWindow.isJoin"), societyWindow, backButtonName, backButtonImage));
                    return;
                }
                if (society == null || society.getPresidentName().equals(player.getName())) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.societyWindow.unableQuitSociety"), societyWindow, backButtonName, backButtonImage));
                    return;
                }

                ModalWindow modalWindow = (ModalWindow) WindowManager.getFormWindow(WindowType.MODAL_WINDOW, PluginUtils.getLanguageInfo("message.societyWindow.isQuitSociety", new String[]{"${societyName}"}, new String[]{society.getSocietyName()}), "§a确认退出", "§c取消退出");
                modalWindow.setButtonClickedListener((affrim, player1) -> {
                    if (affrim) {
                        SocietyUtils.sendMemberTitle(PluginUtils.getLanguageInfo("message.societyWindow.quitSociety", new String[]{"${playerName}"}, new String[]{player1.getName()}), society);
                        Society society1 = SocietyUtils.getSocietyByPlayerName(player1.getName());
                        WindowManager.societyPlugin.getServer().getPluginManager().callEvent(new PlayerQuitSocietyEvent(player1, society1));
                    } else {
                        player.showFormWindow(societyWindow);
                    }
                });

                player.showFormWindow(modalWindow);
                break;

            case 4:
                if (society == null || !SocietyUtils.hasSociety(player.getName())) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.societyWindow.isJoin"), societyWindow, backButtonName, backButtonImage));
                    return;
                }

                MemberListWindow memberListWindow = (MemberListWindow) WindowManager.getFormWindow(WindowType.Member_List_Window, player, societyWindow);
                player.showFormWindow(memberListWindow);
                break;
            case 5:
                player.showFormWindow(WindowManager.getFormWindow(WindowType.CONTRIBUTION_RANKING_WINDOW, societyWindow));
                break;
            case 6:
                player.showFormWindow(WindowManager.getFormWindow(WindowType.LEVEL_RANK_WINDOW, societyWindow));
                break;
            case 7:
                if (society == null || !SocietyUtils.hasSociety(player.getName())) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.societyWindow.isJoin"), societyWindow, backButtonName, backButtonImage));
                    return;
                }
                player.showFormWindow(WindowManager.getFormWindow(WindowType.CONTRIBUTION_WINDOW, player, societyWindow));
                break;
            case 8:
                if (!SocietyUtils.hasSociety(player.getName())) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.societyWindow.isJoin"), societyWindow, backButtonName, backButtonImage));
                    return;
                }
                player.showFormWindow(WindowManager.getFormWindow(WindowType.CREATE_SOCIETY_SHOP_WINDOW, player));
                break;
            case 9:
                if (!SocietyUtils.hasSociety(player.getName())) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.societyWindow.isJoin"), societyWindow, backButtonName, backButtonImage));
                    return;
                }
                if (SocietyUtils.societyChatPlayers.containsKey(player.getName())) {
                    SocietyUtils.societyChatPlayers.remove(player.getName());
                    player.sendMessage(PluginUtils.getLanguageInfo("message.societyWindow.disabledChat"));
                } else {
                    SocietyUtils.societyChatPlayers.put(player.getName(), society.getSocietyName());
                    player.sendMessage(PluginUtils.getLanguageInfo("message.societyWindow.enableChat"));
                }

                break;
            case 10:
                if (!SocietyUtils.hasSociety(player.getName())) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.societyWindow.isJoin"), societyWindow, backButtonName, backButtonImage));
                    return;
                }
                String position = society.getPosition();
                if (position == null || position.length() <= 0) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.societyWindow.notSpawn"), societyWindow, backButtonName, backButtonImage));
                    return;
                }
                String[] split = position.split(",");
                double x = Double.parseDouble(split[0]);
                double y = Double.parseDouble(split[1]);
                double z = Double.parseDouble(split[2]);
                String levelName = split[3];
                Level level = player.getServer().getLevelByName(levelName);
                player.teleport(new Position(x, y, z, level));
                player.sendMessage(PluginUtils.getLanguageInfo("message.societyWindow.spawn"));
                break;
            case 11:
                if (!SocietyUtils.hasSociety(player.getName())) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.societyWindow.isJoin"), societyWindow, backButtonName, backButtonImage));
                    return;
                }

                player.showFormWindow(WindowManager.getFormWindow(WindowType.SOCIETY_INFO_WINDOW, player));
                break;
            case 12:
                player.showFormWindow(WindowManager.getFormWindow(WindowType.VICE_PRESIDENT_WINDOW));
                break;
            default:
                break;
        }
    }


}
