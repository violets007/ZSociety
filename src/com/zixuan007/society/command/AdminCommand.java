package com.zixuan007.society.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

public class AdminCommand extends Command {
    public AdminCommand(String name) {
        super(name);
    }

    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        return false;
    }
}