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
 * 管理命令
 * @author zixuan007
 */
public class AdminCommand extends Command {
    public final static int ONE_ARGS_LENGTH=1;
    public final static int TWO_ARGS_LENGTH=2;
    public final static int THREE_ARGS_LENGTH=3;
    public final static int FOUR_ARGS_LENGTH=4;

    public final static String SOCIETY_ARGS="公会";
    public final static String TITLE_ARGS="称号";
    public final static String MARRY_ARGS="结婚";
    public final static String PRIVILEGE_ARGS="特权";
    public final static String GIVE_TITLE_ARGS="给予";

    public final static String COMMAND_NAME="管理";

    public AdminCommand() {
        super(COMMAND_NAME);
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
        if(args.length < ONE_ARGS_LENGTH) {
            return sendHelp(player);
        }
        switch (args[0]){
            case SOCIETY_ARGS:
                player.showFormWindow(new SocietyAdminWindow());
                return true;
            case TITLE_ARGS:
                player.showFormWindow(WindowManager.getTitleManagerWindow());
                return true;
            case MARRY_ARGS:
                player.showFormWindow(new MarryAdminWindow());
                return true;
            case PRIVILEGE_ARGS:
                player.showFormWindow(WindowManager.getPrivilegeManagerWindow());
                return true;
            case GIVE_TITLE_ARGS:
                if (args.length < FOUR_ARGS_LENGTH){
                    player.sendMessage("§4>> /§b管理 §a给予 §e称号 §6[玩家名字] §6[称号]");
                    return false;
                }

                if(!TITLE_ARGS.equals(args[ONE_ARGS_LENGTH])){
                    player.sendMessage("§4>> /§b管理 §a给予 §e称号 §6[玩家名字] §6[称号]");
                    return false;
                }
                if(SocietyPlugin.getInstance().getTitleConfig().get(args[TWO_ARGS_LENGTH]) == null){
                    player.sendMessage("§4>> §c此玩家不存在!");
                    return false;
                }
                ArrayList<String> titles = TitleUtils.getTitles(args[TWO_ARGS_LENGTH]);
                String title=args[THREE_ARGS_LENGTH];
                if(TitleUtils.isExistTitle(args[TWO_ARGS_LENGTH],title)){
                    player.sendMessage(">> §c玩家 §b"+args[TWO_ARGS_LENGTH]+" §c已拥有此称号,请勿重复给予");
                    return false;
                }
                TitleUtils.addTitle(args[TWO_ARGS_LENGTH],title);
                if (PluginUtils.isOnlineByName(args[TWO_ARGS_LENGTH])) {
                    Server.getInstance().getPlayer(args[TWO_ARGS_LENGTH]).sendMessage(">> §a成功被给予称号 §e"+args[THREE_ARGS_LENGTH]);
                }
                return true;

            default:
                return sendHelp(player);
        }

    }

    /**
     * 设置参数
     */
    public void setParameter(){
        getCommandParameters().put(SOCIETY_ARGS,new CommandParameter[]{
                new CommandParameter(SOCIETY_ARGS,new String[]{SOCIETY_ARGS})
        });

        getCommandParameters().put(TITLE_ARGS,new CommandParameter[]{
                new CommandParameter(TITLE_ARGS,new String[]{TITLE_ARGS})
        });

        getCommandParameters().put(MARRY_ARGS,new CommandParameter[]{
                new CommandParameter(MARRY_ARGS,new String[]{MARRY_ARGS})
        });

        getCommandParameters().put(PRIVILEGE_ARGS,new CommandParameter[]{
                new CommandParameter(PRIVILEGE_ARGS,new String[]{PRIVILEGE_ARGS})
        });

        getCommandParameters().put(GIVE_TITLE_ARGS,new CommandParameter[]{
                new CommandParameter(GIVE_TITLE_ARGS,new String[]{GIVE_TITLE_ARGS}),
                new CommandParameter(TITLE_ARGS,new String[]{TITLE_ARGS}),
                new CommandParameter("§e玩家名§r", CommandParamType.STRING,false),
                new CommandParameter("§6设置的称号§r", CommandParamType.STRING,true)
        });
    }


    public boolean sendHelp(Player player){
        player.sendMessage("§f==========§bZsociety§4管理命令§f==========");
        player.sendMessage(">> §b/管理 §a称号");
        player.sendMessage(">> §b/管理 §a公会");
        player.sendMessage(">> §b/管理 §a结婚");
        player.sendMessage(">> §b/管理 §a特权");
        player.sendMessage(">> §b/管理 §e给予 §e称号 §e玩家名 §6[称号]");
        return true;
    }
}