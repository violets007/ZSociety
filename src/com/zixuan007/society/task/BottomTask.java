package com.zixuan007.society.task;

import cn.nukkit.Player;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.scheduler.Task;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.utils.SocietyUtils;
import java.util.Collection;


public class BottomTask extends PluginTask<SocietyPlugin> {

    public BottomTask(SocietyPlugin owner) {
        super(owner);
    }

    public void onRun(int i) {
        Collection<Player> players = owner.getServer().getOnlinePlayers().values();
        String tipText = SocietyPlugin.getInstance().getConfig().getString("tipText", null);
        for (Player player : players) {
            tipText = SocietyUtils.formatButtomText(tipText, player);
            player.sendTip(tipText);
        }
    }

}
