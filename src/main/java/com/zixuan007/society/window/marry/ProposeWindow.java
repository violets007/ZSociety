package com.zixuan007.society.window.marry;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Lang;
import com.zixuan007.society.event.marry.PlayerMarryEvent;
import com.zixuan007.society.utils.MarryUtils;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.ModalWindow;
import com.zixuan007.society.window.WindowManager;
import me.onebone.economyapi.EconomyAPI;

import java.util.Date;

/**
 * 结婚功能界面
 */
public class ProposeWindow extends CustomWindow {
    public ProposeWindow() {
        super(Lang.marryWindow_Propose_Title);
        addElement(new ElementInput("","被求婚者姓名"));
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {
        String playerName = response.getInputResponse(0);
        if (!PluginUtils.isOnlineByName(playerName)) {
            player.showFormWindow(WindowManager.getMessageWindow("§c当前玩家不在线,请上线在进行求婚",this,"返回上级"));
            return;
        }
        if(playerName.equals(player.getName())){
            player.showFormWindow(WindowManager.getMessageWindow("§c求婚的人不能是自己",this,"返回上级"));
            return;
        }
        double proposeMoney = PluginUtils.getproposeMoney();
        double myMoney = EconomyAPI.getInstance().myMoney(player);
        if(myMoney < proposeMoney){
            player.showFormWindow(WindowManager.getMessageWindow("§c求婚资金不足,求婚需要: "+proposeMoney,this,"返回上级"));
            return;
        }

        Player player1 = Server.getInstance().getPlayer(playerName);
        ModalWindow affrimWindow = WindowManager.getAffrimWindow("§b"+player.getName()+" §c求婚请求", "§a接受求婚", "§c拒绝求婚");

        affrimWindow.setButtonClickedListener((affrim,clickPlayer)->{
            if(affrim){
                Server.getInstance().broadcastMessage("§b"+player.getName()+" §a向§c "+clickPlayer.getName()+" §a求婚成功");
                Server.getInstance().getPluginManager().callEvent(new PlayerMarryEvent(player.getName(),clickPlayer.getName(),new Date()));
            }else{
                MarryUtils.proposeFailName.put(player.getName(),clickPlayer.getName());
            }
            EconomyAPI.getInstance().reduceMoney(player.getPlayer(),PluginUtils.getproposeMoney());
        });
        player1.showFormWindow(affrimWindow);
    }
}
