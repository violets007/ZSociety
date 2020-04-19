package com.zixuan007.society.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import com.zixuan007.society.window.WindowManager;

public class AdminCommand extends Command {
    public AdminCommand() {
        super("管理");
        setPermission("op");
        getCommandParameters().clear();
        setParameter();
    }

    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if(commandSender instanceof ConsoleCommandSender) return false;
        if(!commandSender.isOp())return false;
        if(!s.equals(getName())) return false;
        if(args.length < 1) return false;
        Player player= (Player) commandSender;
        switch (args[0]){
            case "公会":
                break;
            case "称号":
                player.showFormWindow(WindowManager.getTitleManagerWindow());
                break;
            case "结婚":
                break;
            case "特权":
                player.showFormWindow(WindowManager.getPrivilegeManagerWindow());
                break;
        }
        return false;
    }

    /**
     * 设置参数
     */
    public void setParameter(){
        getCommandParameters().put("称号",new CommandParameter[]{
           new CommandParameter("称号", CommandParamType.STRING,true)
        });
        getCommandParameters().put("公会",new CommandParameter[]{
                new CommandParameter("公会", CommandParamType.STRING,true)
        });
        getCommandParameters().put("结婚",new CommandParameter[]{
                new CommandParameter("结婚", CommandParamType.STRING,true)
        });
    }


}