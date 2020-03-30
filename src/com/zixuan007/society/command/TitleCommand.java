package com.zixuan007.society.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import com.zixuan007.society.window.WindowManager;

public class TitleCommand extends Command {
    public TitleCommand() {
        super("称号", "设置称号主命令");
        this.getCommandParameters().clear();
        this.setPermission("op");
    }

    public boolean execute(CommandSender sender, String name, String[] args) {
        Player player = (Player)sender;
        if (!player.isOp()) {
            return false;
        } else if (name.equals(this.getName())) {
            player.showFormWindow(WindowManager.getTitleWindow());
            return true;
        } else {
            return false;
        }
    }
}