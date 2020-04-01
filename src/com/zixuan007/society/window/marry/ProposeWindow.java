package com.zixuan007.society.window.marry;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import com.zixuan007.society.SocietyPlugin;
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
        super("求婚窗口界面");
        addElement(new ElementInput("","被求婚者姓名"));
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {
        String playerName = response.getInputResponse(0);
        if (!PluginUtils.isOnlineByName(playerName)) {
            WindowManager.getMessageWindow("§c当前玩家不在线,请上线在进行求婚",this,"返回上级");
            return;
        }
        Object proposeMoneyOBJ = SocietyPlugin.getInstance().getConfig().get("proposeMoney");
        double proposeMoney = 0;
        if(proposeMoneyOBJ instanceof Integer){
            proposeMoney=((Integer) proposeMoneyOBJ).doubleValue();
        }else{
            proposeMoney= (double) proposeMoneyOBJ;
        }
        double myMoney = EconomyAPI.getInstance().myMoney(player);
        if(myMoney < proposeMoney){
            WindowManager.getMessageWindow("§c求婚资金不足,求婚需要: "+proposeMoney,this,"返回上级");
            return;
        }

        Player player1 = Server.getInstance().getPlayer(playerName);
        ModalWindow affrimWindow = WindowManager.getAffrimWindow("§c求婚请求", "接受求婚", "拒绝求婚");
        affrimWindow.setButtonClickedListener((affrim,clickPlayer)->{
            if(affrim){
                Server.getInstance().broadcastMessage("§b"+player.getName()+" §a向§c "+clickPlayer.getName()+" §a求婚成功");
                Server.getInstance().getPluginManager().callEvent(new PlayerMarryEvent(player.getName(),clickPlayer.getName(),new Date()));
            }else{
                MarryUtils.proposeFailName.put(player.getName(),clickPlayer.getName());
            }
        });
        player1.showFormWindow(affrimWindow);
    }
}
