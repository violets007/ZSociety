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
 * @author zixuan007
 */
public class ShowTask extends PluginTask<SocietyPlugin> {
    private static final String IS_TIP = "isTip";
    private static final String IS_SET_NAME_TAG = "isSetNameTag";

    public ShowTask(SocietyPlugin owner) {
        super(owner);
    }

    @Override
    public void onRun(int i) {
        Collection<Player> players = owner.getServer().getOnlinePlayers().values();
        Config config = SocietyPlugin.getInstance().getConfig();
        String tipText = config.getString(IS_TIP, null);
        String tempText="";
        String configNameTag = (String) config.get(IS_SET_NAME_TAG);
        if (owner.getConfig().getBoolean(IS_TIP)) {
            for (Player player : players) {
                tempText = SocietyUtils.formatButtomText(tipText, player);
                player.sendTip(tempText);
            }
        }

        if (config.getBoolean(IS_SET_NAME_TAG)) {
            for (Player player : players) {
                String formatNameTag = PluginUtils.formatText(configNameTag, player);
                player.setNameTag(formatNameTag);
            }
        }
    }

}
