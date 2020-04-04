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
public class BottomTask extends PluginTask<SocietyPlugin> {

    public BottomTask(SocietyPlugin owner) {
        super(owner);
    }

    public void onRun(int i) {
        Collection<Player> players = owner.getServer().getOnlinePlayers().values();
        Config config = SocietyPlugin.getInstance().getConfig();
        String tipText = config.getString("tipText", null);
        String tempText;
        String configNameTag = (String) config.get("头部更改");
        for (Player player : players) {
            tempText = SocietyUtils.formatButtomText(tipText, player);
            player.sendTip(tempText);
            if (config.getBoolean("是否开启更改头部", false)) {
                String formatNameTag = PluginUtils.formatText(configNameTag, player);
                player.setNameTag(formatNameTag);
            }
        }
    }

}
