package com.zixuan007.society.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

/**
 * 结婚命令
 * @author zixuan007
 */
public class MarryCommand extends Command {
    public final static String COMMAND_NAME= SocietyPlugin.getInstance().getLanguageConfig().getString("command.manage.marry");

    public MarryCommand() {
        super(COMMAND_NAME,"§e显示结婚功能窗口");
        getCommandParameters().clear();
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(commandSender instanceof ConsoleCommandSender){
            commandSender.sendMessage(PluginUtils.getLanguageInfo("message.isConsole"));
            return false;
        }
        Player player = (Player) commandSender;
        player.showFormWindow(WindowManager.getFormWindow(WindowType.MARRY_WINDOW));
        return false;
    }
}
