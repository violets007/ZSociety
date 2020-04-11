package com.zixuan007.society.task;

import cn.nukkit.scheduler.PluginTask;
import com.zixuan007.society.SocietyPlugin;

/**
 * 校验玩家特权到期时间任务
 */
public class CheckPrivilegeTimeTask extends PluginTask<SocietyPlugin> {

    public CheckPrivilegeTimeTask(SocietyPlugin owner) {
        super(owner);
    }

    @Override
    public void onRun(int i) {

    }
}
