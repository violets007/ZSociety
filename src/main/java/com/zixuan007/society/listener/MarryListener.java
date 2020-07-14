package com.zixuan007.society.listener;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Marry;
import com.zixuan007.society.event.marry.DivorceMarryEvent;
import com.zixuan007.society.event.marry.PlayerMarryEvent;
import com.zixuan007.society.utils.MarryUtils;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.window.ModalWindow;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zixuan007
 */
public class MarryListener implements Listener {

    private SocietyPlugin societyPlugin;

    public MarryListener(SocietyPlugin societyPlugin) {
        this.societyPlugin = societyPlugin;
    }

    /**
     * 进入游戏进行性别选择
     *
     * @param event
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        String closeButtonName = PluginUtils.getWindowConfigInfo("messageWindow.close.button");
        String closeButtonImagePath = PluginUtils.getWindowConfigInfo("messageWindow.close.button.imgPath");
        if (SocietyPlugin.getInstance().getMarryConfig().get(player.getName()) == null) {

            ModalWindow affrimWindow = (ModalWindow) WindowManager.getFormWindow(WindowType.MODAL_WINDOW, "§e请选择当前的性别", "男", "女");
            Config marryConfig = SocietyPlugin.getInstance().getMarryConfig();
            affrimWindow.setButtonClickedListener((affrim, player1) -> {
                String gender = "";
                if (affrim) {
                    marryConfig.set(player1.getName(), 1);
                    gender = "§b男";
                } else {
                    marryConfig.set(player1.getName(), 0);
                    gender = "§c女";
                }
                player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, "§a成功选择为 " + gender, null, closeButtonName, closeButtonImagePath));
                marryConfig.save();
            });
            affrimWindow.setBack(true);
            affrimWindow.setParent(affrimWindow);
            player.showFormWindow(affrimWindow);
        }
        String name = MarryUtils.proposeFailName.get(player.getName());
        if (name != null) {
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, "§b" + name + "§c拒绝了你的求婚", null, closeButtonName, closeButtonImagePath));
            MarryUtils.proposeFailName.remove(player.getName());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMarry(PlayerMarryEvent event) {
        String propose = event.getPropose();
        String recipient = event.getRecipient();
        Date marryDate = event.getMarryDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String marryConfigPath = PluginUtils.MARRY_FOLDER + propose + "_" + recipient + ".yml";
        Config config = new Config(marryConfigPath, Config.YAML);
        Marry marry = new Marry();
        marry.setMid(MarryUtils.nextMid());
        marry.setPropose(propose);
        marry.setRecipient(recipient);
        marry.setMoney(0D);
        marry.setMarryDate(simpleDateFormat.format(marryDate));
        marry.setProposeSex(MarryUtils.getGenderByPlayerName(propose));
        marry.setRecipientSex(MarryUtils.getGenderByPlayerName(recipient));
        MarryUtils.saveMarry(marry);
        MarryUtils.marrys.add(marry);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDivorceMarry(DivorceMarryEvent event) {
        Player player = event.getPlayer();
        Marry marry = MarryUtils.getMarryByName(player.getName());
        String propose = marry.getPropose();
        String recipient = marry.getRecipient();
        MarryUtils.removeMarry(marry);
        Server.getInstance().broadcastMessage("§b" + propose + " §a和 §b" + recipient + "§a离婚了");
    }


}
