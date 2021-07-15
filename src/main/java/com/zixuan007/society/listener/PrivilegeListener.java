package com.zixuan007.society.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.utils.PrivilegeUtils;

/**
 * @author zixuan007
 * @description: 特权检测
 * @date: 2021/7/14 下午2:28
 */
public class PrivilegeListener implements Listener {
    private SocietyPlugin societyPlugin;

    public PrivilegeListener(SocietyPlugin societyPlugin) {
        this.societyPlugin = societyPlugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (PrivilegeUtils.removePrivilegeName.contains(player.getName())) {
            player.setAllowFlight(false);
            PrivilegeUtils.removePivilegeData(player.getName());
            PrivilegeUtils.removePrivilegeName.remove(player.getName());
        }
    }
}
