package com.zixuan007.society.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import com.zixuan007.society.utils.TitleUtils;
import com.zixuan007.society.window.WindowManager;

import java.util.ArrayList;

/**
 * @author zixuan007
 */
public class TitleCommand extends Command {

    public final static String COMMAND_NAME= "称号";

    public TitleCommand() {
        super(COMMAND_NAME, "§e显示称号功能窗口");
        this.getCommandParameters().clear();
        this.setPermission("op");
    }

    @Override
    public boolean execute(CommandSender sender, String name, String[] args) {
        Player player = (Player)sender;
        if (name.equals(this.getName())) {
            ArrayList<String> titles = TitleUtils.titleList.get(player.getName());
            if(titles == null || titles.size() <= 0){
                player.sendMessage(">> §c当前还没有称号,请先购买");
                return true;
            }
            player.showFormWindow(WindowManager.getTitleWindow(player.getName()));
            return true;
        }
        return false;
    }
}