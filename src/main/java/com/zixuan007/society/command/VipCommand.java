package com.zixuan007.society.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.PrivilegeUtils;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

/**
 * @author zixuan007
 */
public class VipCommand extends Command {

    public final static String COMMAND_NAME = SocietyPlugin.getInstance().getLanguageConfig().getString("command.privilege");

    public VipCommand() {
        super(COMMAND_NAME, "§e显示特权功能窗口");
        getCommandParameters().clear();
        setPermission("ZSociety.command.user");
    }

    @Override
    public boolean execute(CommandSender commandSender, String commandName, String[] strings) {
        if (commandSender instanceof ConsoleCommandSender) {
            return false;
        }
        Player player = (Player) commandSender;
        if (commandName.equals(getName())) {
            if (strings.length < 1) {
                if (PrivilegeUtils.isVIP(player.getName())) {
                    //展示VIP功能界面
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.PRIVILEGE_WINDOW, player));
                } else if (PrivilegeUtils.isSvip(player.getName())) {
                    //展示SVIP功能界面
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.ADVANCED_PRIVILEGE_WINDOW, player));
                } else {
                    player.sendMessage(PluginUtils.getLanguageInfo("message.notExistPrivilege"));
                }
            }
        }
        return false;
    }
}
