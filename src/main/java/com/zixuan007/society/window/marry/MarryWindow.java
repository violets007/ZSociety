package com.zixuan007.society.window.marry;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.pojo.Marry;
import com.zixuan007.society.event.marry.PlayerDivorceEvent;
import com.zixuan007.society.utils.MarryUtils;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

/**
 * 结婚功能窗口
 *
 * @author zixuan007
 */
public class MarryWindow extends SimpleWindow implements WindowLoader {
    public MarryWindow() {
        super(PluginUtils.getWindowConfigInfo("marryWindow.title"), "");
    }


    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        ElementButtonImageData img1 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("marryWindow.propose.button.imgPath"));
        ElementButtonImageData img2 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("marryWindow.addPublicFunds.button.imgPath"));
        ElementButtonImageData img3 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("marryWindow.transfer.button.imgPath"));
        ElementButtonImageData img4 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("marryWindow.divorceMarry.button.imgPath"));
        ElementButtonImageData img5 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("marryWindow.moneyRankWindow.button.imgPath"));

        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("marryWindow.propose.button"), img1));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("marryWindow.addPublicFunds.button"), img2));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("marryWindow.transfer.button"), img3));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("marryWindow.divorceMarry.button"), img4));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("marryWindow.moneyRankWindow.button"), img5));
        return this;
    }

    @Override
    public void onClick(int id, Player player) {
        FormWindow marryWindow = WindowManager.getFormWindow(WindowType.MARRY_WINDOW);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");
        switch (id) {
            case 0:
                if (MarryUtils.isMarry(player.getName())) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.marryWindow.isMarry"), marryWindow, backButtonName, backButtonImage));
                    return;
                }

                ProposeWindow proposeWindow = (ProposeWindow) WindowManager.getFormWindow(WindowType.PROPOSE_WINDOW);
                proposeWindow.setBack(true);
                proposeWindow.setParent(this);
                player.showFormWindow(proposeWindow);
                break;
            case 1:
                if (!MarryUtils.isMarry(player.getName())) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.marryWindow.notMarry"), marryWindow, backButtonName, backButtonImage));
                    return;
                }
                player.showFormWindow(WindowManager.getAddPublicFunds());
                break;
            case 2:
                Marry marry;
                if (!MarryUtils.isMarry(player.getName())) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.marryWindow.notMarry"), marryWindow, backButtonName, backButtonImage));
                    return;
                }
                marry = MarryUtils.getMarryByName(player.getName());
                String recipientName = marry.getRecipient();
                String proposeName = marry.getPropose();
                if (PluginUtils.isOnlineByName(recipientName) && PluginUtils.isOnlineByName(proposeName)) {
                    Player recipientPlayer = Server.getInstance().getPlayer(recipientName);
                    Player proposePlayer = Server.getInstance().getPlayer(proposeName);
                    if (!player.getName().equals(recipientName)) {
                        proposePlayer.teleport(recipientPlayer);
                    } else {
                        recipientPlayer.teleport(proposePlayer);
                    }

                    player.sendMessage(PluginUtils.getLanguageInfo("message.marryWindow.tp"));
                    return;
                } else {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.marryWindow.recipientNotOnline"), marryWindow, backButtonName, backButtonImage));
                }
                break;
            case 3:
                if (!MarryUtils.isMarry(player.getName())) {

                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.marryWindow.notMarry"), marryWindow, backButtonName, backButtonImage));
                    return;
                }
                marry = MarryUtils.getMarryByName(player.getName());
                player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.marryWindow.divorceMarry"), marryWindow, backButtonName, backButtonImage));

                Server.getInstance().getPluginManager().callEvent(new PlayerDivorceEvent(player, marry));
                break;
            case 4:
                if (MarryUtils.marrys.size() < 1) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.marryWindow.notLovers"), marryWindow, backButtonName, backButtonImage));
                    return;
                }
                MarryUtils.marrys.sort((marry1, marry2) -> (marry1.getMoney() > marry2.getMoney()) ? -1 : (marry2.getMoney() < marry1.getMoney()) ? 1 : 0);
                StringBuilder sb = new StringBuilder();
                sb.append("§l§d夫妻公共资产排名§f(§c前五§f)\n");
                int rankNumber = 4;
                for (int i = 0; i < MarryUtils.marrys.size() && i <= rankNumber; i++) {
                    Marry marry1 = MarryUtils.marrys.get(i);
                    sb.append("§f丈夫 " + marry1.getPropose() + " §f妻子 §a" + marry1.getRecipient() + " §f公会经济 §b" + marry1.getMoney() + "\n");
                }
                MoneyRankWindow moneyRankWindow = WindowManager.getMoneyRankWindow();
                moneyRankWindow.setContent(sb.toString());
                moneyRankWindow.setBack(true);
                moneyRankWindow.setParent(this);
                player.showFormWindow(moneyRankWindow);
                break;
            default:
                break;
        }
    }


}
