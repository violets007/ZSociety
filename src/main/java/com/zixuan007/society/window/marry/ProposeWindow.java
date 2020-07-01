package com.zixuan007.society.window.marry;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.event.marry.PlayerMarryEvent;
import com.zixuan007.society.utils.MarryUtils;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.window.*;
import me.onebone.economyapi.EconomyAPI;

import java.util.Date;

/**
 * 结婚功能界面
 * @author zixuan007
 */
public class ProposeWindow extends CustomWindow implements WindowLoader {
    public ProposeWindow() {
        super(PluginUtils.getWindowConfigInfo("proposeWindow.title"));

    }

    @Override
    public FormWindow init(Object... objects) {
        getElements().clear();
        addElement(new ElementInput("","被求婚者姓名"));
        return this;
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {
        String playerName = response.getInputResponse(0);
        FormWindow proposeWindow = WindowManager.getFormWindow(WindowType.PROPOSE_WINDOW);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");
        if (!PluginUtils.isOnlineByName(playerName)) {
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, "§c你已经存在公会,无法创建请先退出当前公会", proposeWindow, backButtonName, backButtonImage));
            return;
        }
        if(playerName.equals(player.getName())){
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, "§c求婚的人不能是自己", proposeWindow, backButtonName, backButtonImage));
            return;
        }
        double proposeMoney = PluginUtils.getProposeMoney();
        double myMoney = EconomyAPI.getInstance().myMoney(player);
        if(myMoney < proposeMoney){
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, "§c求婚资金不足,求婚需要: "+proposeMoney, proposeWindow, backButtonName, backButtonImage));
            return;
        }

        Player player1 = Server.getInstance().getPlayer(playerName);
        
        ModalWindow affrimWindow = (ModalWindow) WindowManager.getFormWindow(WindowType.MODAL_WINDOW,"§b"+player.getName()+" §c求婚请求","§a接受求婚","§c拒绝求婚");

        affrimWindow.setButtonClickedListener((affrim,clickPlayer)->{
            if(affrim){
                Server.getInstance().broadcastMessage("§b"+player.getName()+" §a向§c "+clickPlayer.getName()+" §a求婚成功");
                Server.getInstance().getPluginManager().callEvent(new PlayerMarryEvent(player.getName(),clickPlayer.getName(),new Date()));
            }else{
                MarryUtils.proposeFailName.put(player.getName(),clickPlayer.getName());
            }
            EconomyAPI.getInstance().reduceMoney(player.getPlayer(),PluginUtils.getProposeMoney());
        });
        player1.showFormWindow(affrimWindow);
    }


}
