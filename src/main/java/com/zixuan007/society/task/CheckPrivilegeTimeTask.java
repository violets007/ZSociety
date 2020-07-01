package com.zixuan007.society.task;

import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.PrivilegeUtils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 校验玩家特权到期时间任务
 * @author zixuan007
 */
public class CheckPrivilegeTimeTask extends PluginTask<SocietyPlugin> {

    public CheckPrivilegeTimeTask(SocietyPlugin owner) {
        super(owner);
    }

    @Override
    public void onRun(int i) {
        if(PrivilegeUtils.privilegeList.size() > 0){
            String privilegeFolder = PluginUtils.PRIVILEGE_FOLDER;
            File file = new File(privilegeFolder);
            File[] files = file.listFiles();
            for (File dataFile : files) {
                if(dataFile.getName().endsWith(".yml")){
                    Config config = new Config(dataFile, Config.YAML);
                    String holdTime = (String) config.get("holdTime");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                    Date parse;
                    try {
                        parse=simpleDateFormat.parse(holdTime);
                        if(System.currentTimeMillis() >= parse.getTime()){
                            String playerName = (String) config.get("playerName");
                            if(!PrivilegeUtils.removePrivilegeName.contains(playerName)) {
                                PrivilegeUtils.removePrivilegeName.add(playerName);
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
