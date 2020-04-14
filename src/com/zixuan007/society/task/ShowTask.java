package com.zixuan007.society.task;

import cn.nukkit.Player;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import java.util.Collection;

/**
 * 底部Tip显示
 */
public class ShowTask extends PluginTask<SocietyPlugin> {

    public ShowTask(SocietyPlugin owner) {
        super(owner);
    }

    public void onRun(int i) {
        Collection<Player> players = owner.getServer().getOnlinePlayers().values();
        Config config = SocietyPlugin.getInstance().getConfig();
        String tipText = config.getString("tipText", null);
        String tempText="";
        String configNameTag = (String) config.get("nameTagFormat");
        if (owner.getConfig().getBoolean("isTip")) {
            for (Player player : players) {
                tempText = SocietyUtils.formatButtomText(tipText, player);
                player.sendTip(tempText);
            }
        }

        if (config.getBoolean("isSetNameTag")) {
            for (Player player : players) {
                String formatNameTag = PluginUtils.formatText(configNameTag, player);
                player.setNameTag(formatNameTag);
            }
        }
    }

}
