package com.zixuan007.society.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import com.zixuan007.society.utils.PrivilegeUtils;
import com.zixuan007.society.window.WindowManager;

public class VipCommand extends Command {
    public VipCommand() {
        super("特权");
        getCommandParameters().clear();
        setPermission("");
    }

    @Override
    public boolean execute(CommandSender commandSender, String commandName, String[] strings) {
        if(commandSender instanceof ConsoleCommandSender) return false;
        Player player= (Player) commandSender;
        if(commandName.equals(getName())){
            if(strings.length <1){
                if(PrivilegeUtils.isVIP(player.getName())){
                    //展示VIP功能界面
                    player.showFormWindow(WindowManager.getVipWindow());
                }else if(PrivilegeUtils.isSvip(player.getName())){
                    //展示SVIP功能界面
                    player.showFormWindow(WindowManager.getSvipWindow());
                }else{
                    player.sendMessage(">> §c你还没有特权,请先联系管理员购买");
                }
            }else{
                String string = strings[0];
                if (string.equals("管理") && player.isOp()) {
                    player.showFormWindow(WindowManager.getPrivilegeManagerWindow());
                }
            }
        }
        return false;
    }
}
