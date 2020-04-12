package com.zixuan007.society.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;

public class AdminCommand extends Command {
    public AdminCommand() {
        super("管理");
        setPermission("op");
       getCommandParameters().clear();
    }

    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if(commandSender instanceof ConsoleCommandSender) return false;
        if(commandSender.isOp())return false;
        if(!s.equals(getName())) return false;
        if(args.length < 1) return false;
        switch (args[0]){
            case "公会":
                break;
            case "称号":
                break;
            case "结婚":
                break;
            case "特权":
                break;
        }
        return false;
    }

    /**
     * 设置参数
     */
    public void setParameter(){

    }
}