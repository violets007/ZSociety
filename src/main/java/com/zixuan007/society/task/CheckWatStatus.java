package com.zixuan007.society.task;

import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;

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
                default:
                    break;
            }
        }


    }
}
