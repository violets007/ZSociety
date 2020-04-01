package com.zixuan007.society.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import com.zixuan007.society.window.WindowManager;

/**
 * 结婚命令
 */
public class MarryCommand extends Command {
    public MarryCommand() {
        super("结婚");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        Player player = (Player) commandSender;
        player.showFormWindow(WindowManager.getMarryWindow());
        return false;
    }
}
