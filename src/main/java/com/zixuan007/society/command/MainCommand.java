package com.zixuan007.society.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.society.SocietyWindow;

/**
 * 公会命令
 * @author zixuan007
 */
public class MainCommand extends Command {
    public final static String COMMAND_NAME=SocietyPlugin.getInstance().getConfig().getString("comands");

    public MainCommand() {
        super(COMMAND_NAME, "§e显示公会功能窗口", "");
        this.setPermission("ZSociety.command.user");
        this.getCommandParameters().clear();
    }

    @Override
    public boolean execute(CommandSender sender, String commandName, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("§c禁止控制台输入命令");
        } else {
            Player player = (Player)sender;
            if (commandName.equals(this.getName())) {
                SocietyWindow societyWindow = WindowManager.getSocietyWindow(player);
                player.showFormWindow(societyWindow);
            }

        }
        return false;
    }
}