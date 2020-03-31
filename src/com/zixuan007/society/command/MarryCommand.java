package com.zixuan007.society.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;

/**
 * 结婚命令
 */
public class MarryCommand extends Command {
    public MarryCommand(String name) {
        super("结婚");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        return false;
    }
}
