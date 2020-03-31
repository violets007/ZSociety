package com.zixuan007.society.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.society.SocietyWindow;

public class MainCommand extends Command {
    private SocietyPlugin societyPlugin = SocietyPlugin.getInstance();


    public MainCommand() {
        super((String)SocietyPlugin.getInstance().getConfig().get("comands"), "公会插件总命令", "/§b公会 §e帮助");
        this.setPermission("");
        this.getCommandParameters().clear();
    }

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