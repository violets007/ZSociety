package com.zixuan007.society.task;

import cn.nukkit.Player;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

import java.util.List;

/**
 * 检测公会数据文件夹当前状态
 *
 * @author zixuan007
 */
public class CheckWatStatus extends PluginTask<SocietyPlugin> {

    public CheckWatStatus(SocietyPlugin owner) {
        super(owner);
    }

    @Override
    public void onRun(int i) {
        //检测公会战争数据文件夹!
        List<Config> societyWarList = SocietyPlugin.getInstance().getSocietyWarList();

        String closeButtonName = PluginUtils.getWindowConfigInfo("messageWindow.close.button");
        String closeButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.close.button.imgPath");
        if (societyWarList == null) {
            return;
        }

        for (Config config : societyWarList) {
            String status = config.getString("status");
            switch (status) {
                case "WAIT":

                    break;
                case "SEND":
                    //检测玩家是否同意公会战

                    //检测公会战是否失效
                    break;
                case "RUN":

                    break;
                case "END":

                    break;
                case "REFUSE":
                    Long sid = config.getLong("sid");
                    Society society = SocietyUtils.getSocietysByID(sid);
                    if (PluginUtils.isOnlineByName(society.getPresidentName())) {
                        //当前公会会长在线
                        Player player = owner.getServer().getPlayer(society.getPresidentName());
                        player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("对方公会拒绝了公会战"), null, closeButtonName, closeButtonImage));

                        //TODO 扣除当前公会资金的百分之2

                        //移除配置文件

                    }
                    break;
                default:
                    break;
            }
        }


    }
}
