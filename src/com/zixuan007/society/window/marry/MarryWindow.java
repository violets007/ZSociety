package com.zixuan007.society.window.marry;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Lang;
import com.zixuan007.society.domain.Marry;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.event.marry.DivorceMarryEvent;
import com.zixuan007.society.utils.MarryUtils;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * 结婚功能窗口
 */
public class MarryWindow extends SimpleWindow {
    public MarryWindow() {
        super(Lang.marryWindow_Title, "");
        init();
    }

    public void init(){
        ElementButtonImageData buttonImageData = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH,Lang.marryWindow_ProposeButton_ImgPath);

        addButton(new ElementButton("求婚",buttonImageData));
        addButton(new ElementButton("增加公共资金",buttonImageData));
        addButton(new ElementButton("传送至情侣身边",buttonImageData));
        addButton(new ElementButton("离婚",buttonImageData));
        addButton(new ElementButton("夫妻公共财产排行榜",buttonImageData));

    }

    @Override
    public void onClick(int id, Player player) {
        switch (id){
            case 0:
                if(MarryUtils.isMarry(player.getName())){
                    player.showFormWindow(WindowManager.getMessageWindow("§c您当前已经结婚,请勿重复",this,"返回上级"));
                    return;
                }
                ProposeWindow proposeWindow = WindowManager.getProposeWindow();
                proposeWindow.setBack(true);
                proposeWindow.setParent(this);
                player.showFormWindow(proposeWindow);
                break;
            case 1:
                if(!MarryUtils.isMarry(player.getName())){
                    player.showFormWindow(WindowManager.getMessageWindow("§c您当前还没有伴侣,请先求婚",this,"返回上级"));
                    return;
                }
                player.showFormWindow(WindowManager.getAddPublicFunds());
                break;
            case 2:
                if(!MarryUtils.isMarry(player.getName())){
                    player.showFormWindow(WindowManager.getMessageWindow("§c您当前还没有伴侣,请先求婚",this,"返回上级"));
                    return;
                }
                Marry marry = MarryUtils.getMarryByName(player.getName());
                String recipientName = marry.getRecipient();
                String proposeName = marry.getPropose();
                if(PluginUtils.isOnlineByName(recipientName) && PluginUtils.isOnlineByName(proposeName)){
                    if(!player.getName().equals(recipientName)){
                        Player player1 = Server.getInstance().getPlayer(recipientName);
                        player.teleport(player1);
                        player.sendMessage("§a传送成功");
                    }
                    return;
                }else{
                    player.showFormWindow(WindowManager.getMessageWindow("§c当前伴侣不在线",this,"返回上级"));
                }
                break;
            case 3:
                if(!MarryUtils.isMarry(player.getName())){
                    player.showFormWindow(WindowManager.getMessageWindow("§c您当前还没有伴侣,请先求婚",this,"返回上级"));
                    return;
                }
                player.showFormWindow(WindowManager.getMessageWindow("§c离婚成功",this,"返回上级"));
                SocietyPlugin.getInstance().getServer().getPluginManager().callEvent(new DivorceMarryEvent());
                break;
            case 4:
                if(MarryUtils.marrys.size() <1){
                    player.showFormWindow(WindowManager.getMessageWindow("§c当前还没有一对夫妻",this,"返回上级"));
                    return;
                }
                Collections.sort(MarryUtils.marrys, new Comparator<Marry>() {
                    @Override
                    public int compare(Marry marry1, Marry marry2) {
                        return (marry1.getMoney() > marry2.getMoney()) ? -1:(marry2.getMoney()<marry1.getMoney())?1:0;
                    }
                });
                StringBuilder sb = new StringBuilder();
                sb.append("§l§d夫妻公共资产排名§f(§c前五§f)\n");
                for (int i = 0; i < MarryUtils.marrys.size() && i <= 4; i++) {
                    Marry marry1 = MarryUtils.marrys.get(i);
                    sb.append("§f丈夫 "+marry1.getPropose()+" §f妻子 §a" + marry1.getRecipient() + " §f公会经济 §b" + marry1.getMoney() + "\n");
                }
                MoneyRankWindow moneyRankWindow = WindowManager.getMoneyRankWindow();
                moneyRankWindow.setContent(sb.toString());
                moneyRankWindow.setBack(true);
                moneyRankWindow.setParent(this);
                player.showFormWindow(moneyRankWindow);
                break;
        }
    }
}
