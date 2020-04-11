package com.zixuan007.society.window.society.president;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.society.MessageWindow;
import com.zixuan007.society.window.society.PlayerApplyListWindow;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PresidentWindow extends SimpleWindow {
    private long sid;

    public PresidentWindow(long sid) {
        super("", "");
        this.sid = sid;
        addButton(new ElementButton("设置成员职位"));
        addButton(new ElementButton("查看玩家申请"));
        addButton(new ElementButton("升级公会"));
        addButton(new ElementButton("移除人员"));
        addButton(new ElementButton("解散"));
        Society society = SocietyUtils.getSocietysByID(sid);
        setTitle(((String)SocietyPlugin.getInstance().getLangConfig().get("会长公会管理窗口标题")).replaceAll("\\$\\{societyName\\}", society.getSocietyName())); } public void onClick(int id, Player player) { SetJobWindow setJobWindow; ArrayList<String> tempApply; PlayerApplyListWindow playerApplyListWindow; ArrayList<Object> list;
        Double societyMoney;
        int updateMoney;
        List<String> memberList;
        RemoveMemberWindow removeMemberWindow;
        int clickedButtonId = getResponse().getClickedButtonId();
        SocietyPlugin societyPlugin = SocietyPlugin.getInstance();
        Society society = SocietyUtils.getSocietysByID(this.sid);
        switch (clickedButtonId) {
            case 0:
                setJobWindow = WindowManager.getSetJobWindow((FormWindow)this);
                player.showFormWindow((FormWindow)setJobWindow);
                break;
            case 1:
                tempApply = society.getTempApply();
                if (tempApply == null || tempApply.size() <= 0) {
                    MessageWindow messageWindow = WindowManager.getMessageWindow("§c当前还没有玩家申请加入公会", this, "返回上级");
                    player.showFormWindow((FormWindow)messageWindow);
                    return;
                }
                playerApplyListWindow = WindowManager.getPlayerApplyListWindow(tempApply, society.getSid());
                playerApplyListWindow.setBack(true);
                playerApplyListWindow.setParent(this);
                player.showFormWindow(playerApplyListWindow);
                break;
            case 2:
                list = (ArrayList<Object>)societyPlugin.getConfig().get("等级" + society.getGrade());
                if (list == null || list.size() <= 0) {
                    MessageWindow messageWindow = WindowManager.getMessageWindow("§c当前公会已经是最顶级", (FormWindow)this, "返回上级");
                    player.showFormWindow((FormWindow)messageWindow);
                    return;
                }
                societyMoney = society.getSocietyMoney();
                updateMoney = ((Integer)list.get(1)).intValue();
                if (societyMoney.doubleValue() < updateMoney) {
                    MessageWindow messageWindow = WindowManager.getMessageWindow("§c公会升级需要 §b" + updateMoney + "§c,公会当前资金 §b" + societyMoney, (FormWindow)this, "返回上级");
                    player.showFormWindow((FormWindow)messageWindow);

                    return;
                }
                society.setSocietyMoney(Double.valueOf(societyMoney.doubleValue() - updateMoney));
                society.setGrade(society.getGrade() + 1);
                society.saveData();
                player.showFormWindow((FormWindow)WindowManager.getMessageWindow("§a公会升级成功", (FormWindow)this, "返回上级"));
                break;

            case 3:
                if (society.getPost().size() == 1) {
                    MessageWindow messageWindow = WindowManager.getMessageWindow("§c当前没有可以移除的人员", (FormWindow)this, "返回上级");
                    player.showFormWindow((FormWindow)messageWindow);
                    return;
                }
                memberList = Arrays.asList((String[])society.getPost().keySet().toArray((Object[])new String[society.getPost().keySet().size()]));
                removeMemberWindow = WindowManager.getRemoveMemberWindow(society.getSid(), memberList);
                removeMemberWindow.setParent((FormWindow)this);
                player.showFormWindow((FormWindow)removeMemberWindow);
                break;
            case 4:
                player.showFormWindow((FormWindow)WindowManager.getMessageWindow("§a成功解散 §b" + society.getSocietyName(), (FormWindow)WindowManager.getSocietyWindow(player), "返回主界面"));
                SocietyUtils.sendMemberTitle("§b"+player.getName()+"§c解散了公会",society);
                societyPlugin.getSocieties().remove(society);
                SocietyUtils.removeSociety(society.getSocietyName());
                break;
        }  }

}

