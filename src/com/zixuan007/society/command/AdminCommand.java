package com.zixuan007.society.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.TitleUtils;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.marry.admin.MarryAdminWindow;
import com.zixuan007.society.window.society.admin.SocietyAdminWindow;

import java.util.ArrayList;

/**
 * @author zixuan007
 */
public class AdminCommand extends Command {
    public AdminCommand() {
        super("管理");
        setPermission("ZSociety.command.admin");
        getCommandParameters().clear();
        setParameter();
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {

        if(commandSender instanceof ConsoleCommandSender) {
            return false;
        }
        if(!commandSender.isOp()) {
            return false;
        }
        Player player= (Player) commandSender;
        if(args.length < 1) {
            return sendHelp(player);
        }
        switch (args[0]){
            case "公会":
                player.showFormWindow(new SocietyAdminWindow());
                return true;
            case "称号":
                player.showFormWindow(WindowManager.getTitleManagerWindow());
                return true;
            case "结婚":
                player.showFormWindow(new MarryAdminWindow());
                return true;
            case "特权":
                player.showFormWindow(WindowManager.getPrivilegeManagerWindow());
                return true;
            case "给予":
                if (args.length < 4){
                    player.sendMessage("§4>> /§b管理 §a给予 §e称号 §6[玩家名字] §6[称号]");
                    return false;
                }
                if(!args[1].equals("称号")){
                    player.sendMessage("§4>> /§b管理 §a给予 §e称号 §6[玩家名字] §6[称号]");
                    return false;
                }
                if(SocietyPlugin.getInstance().getTitleConfig().get(args[2]) == null){
                    player.sendMessage("§4>> §c此玩家不存在!");
                    return false;
                }
                ArrayList<String> titles = TitleUtils.getTitles(args[2]);
                String title=args[3];
                if(TitleUtils.isExistTitle(args[2],title)){
                    player.sendMessage(">> §c玩家 §b"+args[2]+" §c已拥有此称号,请勿重复给予");
                    return false;
                }
                TitleUtils.addTitle(args[2],title);
                if (PluginUtils.isOnlineByName(args[2])) {
                    Server.getInstance().getPlayer(args[2]).sendMessage(">> §a成功被给予称号 §e"+args[3]);
                }
                return true;
        }
        return sendHelp(player);
    }

    /**
     * 设置参数
     */
    public void setParameter(){

        getCommandParameters().put("称号",new CommandParameter[]{
                new CommandParameter("称号",new String[]{"称号"})
        });
        getCommandParameters().put("公会",new CommandParameter[]{
                new CommandParameter("公会",new String[]{"公会"})
        });
        getCommandParameters().put("结婚",new CommandParameter[]{
                new CommandParameter("结婚",new String[]{"结婚"})
        });
        getCommandParameters().put("给予",new CommandParameter[]{
                new CommandParameter("给予",new String[]{"给予"}),
                new CommandParameter("称号",new String[]{"称号"}),
                new CommandParameter("§e玩家名§r", CommandParamType.STRING,false),
                new CommandParameter("§6设置的称号§r", CommandParamType.STRING,true)
        });
    }


    public boolean sendHelp(Player player){
        player.sendMessage("§f==========§bZsociety§4管理命令§f==========");
        player.sendMessage(">> §b/管理 §a称号");
        player.sendMessage(">> §b/管理 §a公会");
        player.sendMessage(">> §b/管理 §a结婚");
        player.sendMessage(">> §b/管理 §e给予 §e称号 §e玩家名 §6[称号]");
        return true;
    }
}