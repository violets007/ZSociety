package com.zixuan007.society.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.event.society.PlayerApplyJoinSocietyEvent;
import com.zixuan007.society.event.society.PlayerCreateSocietyEvent;
import com.zixuan007.society.event.society.PlayerQuitSocietyEvent;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.society.MessageWindow;
import com.zixuan007.society.window.society.SocietyWindow;
import java.util.ArrayList;
import java.util.HashMap;

public class SocietyListener implements Listener {
    private SocietyPlugin societyPlugin;

    public SocietyListener(SocietyPlugin societyPlugin) {
        this.societyPlugin = societyPlugin;
    }

    /**
     * 当创建公会的时候调用此事件
     * @param event
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onCreate(PlayerCreateSocietyEvent event) {
        Player player = event.getPlayer();
        Society society = event.getSociety();
        final Config config = this.societyPlugin.getConfig();
        society.getPost().put(player.getName(), new ArrayList<Object>() {
            {
                ArrayList<Object> post = (ArrayList)config.get("post");
                HashMap<String, Object> map = (HashMap)post.get(0);
                this.add(map.get("name"));
                this.add(map.get("grade"));
            }
        });
        society.saveData();
        SocietyPlugin.getInstance().getSocieties().add(event.getSociety());
        SocietyPlugin.getInstance().getLogger().info("§a玩家: §b" + player.getName() + " §a创建公会名称: §e" + society.getSocietyName());
        MessageWindow messageWindow = WindowManager.getMessageWindow("§a创建 §l§b" + society.getSocietyName() + " §a公会成功", new SocietyWindow(player), "返回上级");
        player.showFormWindow(messageWindow);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerApplyJoinSocietyEvent event) {
        Player player = event.getPlayer();
        Society society = event.getSociety();
        Config config = this.societyPlugin.getConfig();
        society.getTempApply().remove(player.getName());
        society.getTempApply().add(player.getName());
        society.saveData();
        MessageWindow messageWindow = WindowManager.getMessageWindow(" §a成功申请加入 §l§b" + society.getSocietyName() + " §a公会,请耐心等待§c会长进行处理", new SocietyWindow(player), "返回上级");
        player.showFormWindow(messageWindow);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuic(PlayerQuitSocietyEvent event) {
        Player player = event.getPlayer();
        Society society = event.getSociety();
        society.getPost().remove(player.getName());
        society.saveData();
        MessageWindow messageWindow = WindowManager.getMessageWindow("§a成功退出 §l§c" + society.getSocietyName() + " §a公会", new SocietyWindow(player), "返回上级");
        player.showFormWindow(messageWindow);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (this.societyPlugin.getConfig().getBoolean("isChat")) {
            message = SocietyUtils.formatChat(player, message);
            event.setFormat(message);
        }
    }
}