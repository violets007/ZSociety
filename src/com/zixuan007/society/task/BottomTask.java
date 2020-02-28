package com.zixuan007.society.task;

import cn.nukkit.Player;
import cn.nukkit.scheduler.Task;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.utils.Utils;

import java.util.Collection;

public class BottomTask extends Task {
    private SocietyPlugin societyPlugin=null;

    public BottomTask(SocietyPlugin societyPlugin) {
        this.societyPlugin=societyPlugin;
    }

    @Override
    public void onRun(int i) {
        Boolean isShowTip = (Boolean) societyPlugin.getConfig().get("isTip");
        if(isShowTip){
            Collection<Player> players = societyPlugin.getServer().getOnlinePlayers().values();
            String tipText = (String) SocietyPlugin.getInstance().getConfig().get("tipText");
            for (Player player : players) {
                tipText= Utils.formatButtomText(tipText,player);
                player.sendTip(tipText);
            }
        }
    }
}
