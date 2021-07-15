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
 *
 * @author zixuan007
 */
public class ShowTipTask extends PluginTask<SocietyPlugin> {

    private static final String ENABLE_TIP = "底部显示开启";
    private static final String ENABLE_NAMETAG = "玩家标签显示开启";
    private static final String NAMETAG_FORMAT = "玩家名称标签格式";
    private static final String TIP_FORMAT = "底部格式化";

    public ShowTipTask(SocietyPlugin owner) {
        super(owner);
    }

    @Override
    public void onRun(int i) {
        Collection<Player> players = owner.getServer().getOnlinePlayers().values();
        Config config = SocietyPlugin.getInstance().getConfig();
        String tempText = config.getString(TIP_FORMAT);
        String configNameTag = (String) config.get(NAMETAG_FORMAT);
        if (config.getBoolean(ENABLE_TIP)) {
            for (Player player : players) {
                tempText = SocietyUtils.formatButtomText(tempText, player);
                player.sendTip(tempText);
            }
        }

        if (config.getBoolean(ENABLE_NAMETAG)) {
            for (Player player : players) {
                String formatNameTag = PluginUtils.formatText(configNameTag, player);
                player.setNameTag(formatNameTag);
            }
        }
    }

}
