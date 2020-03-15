package com.zixuan007.society.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseData;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindow;
import com.google.gson.internal.$Gson$Preconditions;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.Utils;
import com.zixuan007.society.window.*;
import me.onebone.economyapi.EconomyAPI;
import sun.security.krb5.Config;

import java.util.ArrayList;

public class WindowListener implements Listener {
    private SocietyPlugin societyPlugin;

    public WindowListener(SocietyPlugin societyPlugin) {
        this.societyPlugin = societyPlugin;
    }

    /**
     * 玩家响应窗口数据事件
     *
     * @param event
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onResponse(PlayerFormRespondedEvent event) {
        FormWindow window = event.getWindow();
        Player player = event.getPlayer();
        if (window instanceof ManageWindow) {
            ManageWindow manageWindow = (ManageWindow) window;
            Society society = Utils.getSocietysByID(manageWindow.getSid());
            FormResponseSimple response = manageWindow.getResponse();
            if (response == null) return;
            ElementButton clickedButton = response.getClickedButton();
            int clickedButtonId = response.getClickedButtonId();
            switch (clickedButtonId) {
                case 0: //移除公会人员
                    //显示列表
                    if (society.getPost().size() == 1) {
                        player.sendMessage(">> §c没有可以移除的列表人员");
                        return;
                    }
                    player.showFormWindow(new MemberListWindow(society.getSocietyName() + "§e成员列表", "", manageWindow.getSid(), 1));
                    break;
                case 1:
                    player.showFormWindow(new SetPostWindow("§e设置成员职位", society.getSid()));
                    break;
                case 2:
                    player.sendMessage(">> §a解散公会成功");
                    Utils.removeSociety(society.getSocietyName());
                    SocietyPlugin.getInstance().getSocieties().remove(society);
                    break;
                case 3:
                    if ((society.getTempApply().size() == 0)) {
                        player.showFormWindow(new MessageWindow("§e错误提示", "§c暂无申请人员,请稍后重试!", society.getSid()));
                        return;
                    }
                    player.showFormWindow(new SocietyApplyWindow("§e玩家申请列表", society.getSid()));
                    break;
                case 4:
                    player.showFormWindow(new AffirmUpdateWindow("§e确认升级提示","§6您确认给公会进行升级么","§a确认升级","§c取消升级",society.getSid()));
                    break;
            }
        } else if (window instanceof MemberListWindow) {
            MemberListWindow memberListWindow = (MemberListWindow) window;
            Society society = Utils.getSocietysByID(memberListWindow.getSid());
            int currentPage = memberListWindow.getCurrentPage();
            if (memberListWindow.getResponse() == null) return;
            int clickedButtonId = memberListWindow.getResponse().getClickedButtonId();
            switch (clickedButtonId) {
                case 10: //点击了下级列表
                    player.showFormWindow(new MemberListWindow(society.getSocietyName() + "§e成员列表", "", memberListWindow.getSid(), currentPage + 1));
                    break;
                case 0:
                    if (currentPage >= 2) {
                        player.showFormWindow(new MemberListWindow(society.getSocietyName() + "§e成员列表", "", memberListWindow.getSid(), currentPage - 1));
                        return;
                    }
                default:
                    //移除成功
                    ElementButton clickedButton = memberListWindow.getResponse().getClickedButton();
                    society.getPost().remove(clickedButton.getText());
                    society.saveData();//时时更新
                    player.showFormWindow(new MessageWindow("§a成功提示", "成功移除成员 " + clickedButton.getText(), society.getSid()));
                    break;
            }
        }
        if (window instanceof MessageWindow) {
            MessageWindow messageWindow = (MessageWindow) window;
            Society society = Utils.getSocietysByID(messageWindow.getSid());
            if (messageWindow.getResponse() == null) return;
            if (messageWindow.getResponse().getClickedButtonId() == 0)
                player.showFormWindow(new ManageWindow(society.getSocietyName() + "§e管理界面", "", society.getSid()));


        } else if (window instanceof SetPostWindow) {
            SetPostWindow setPostWindow = (SetPostWindow) window;
            Society society = Utils.getSocietysByID(setPostWindow.getSid());
            FormResponseCustom response = setPostWindow.getResponse();
            if (response == null) return;
            String name = response.getInputResponse(0);
            FormResponseData dropdownResponse = response.getDropdownResponse(1);
            String gradeName = dropdownResponse.getElementContent();
            int grade = Utils.getPostGradeByName(gradeName);
            boolean isexist = society.getPost().get(name) != null;
            if (player.getName().equals(name)) {
                player.showFormWindow(new MessageWindow("§e错误提示", "§c不能设置自己的职位", society.getSid()));
                return;
            }
            if (!isexist) {
                player.showFormWindow(new MessageWindow("§e错误提示", "§c设置的玩家名字不存在,请重新设置", society.getSid()));
                return;
            }
            society.getPost().put(name, new ArrayList<Object>() {
                {
                    add(gradeName);
                    add(grade);
                }
            });
            society.saveData();
            player.showFormWindow(new MessageWindow("§e错误提示", "§a设置玩家 §l§a" + name + " §a职位为§c" + gradeName + "§a成功", society.getSid()));
        }
        if (window instanceof SocietyApplyWindow) {
            SocietyApplyWindow applyWindow = (SocietyApplyWindow) window;
            if (!applyWindow.wasClosed()) {
                FormResponseSimple response = applyWindow.getResponse();
                ElementButton clickedButton = response.getClickedButton();
                String name = clickedButton.getText();
                Society society = Utils.getSocietysByID(applyWindow.getSid());
                if (Utils.isJoinSociety(name)) {
                    society.getTempApply().remove(name);
                    player.showFormWindow(new MessageWindow("§e错误提示", "§c此玩家已经加入公会", society.getSid()));
                    return;
                }
                player.showFormWindow(new AffirmWindow("§e确认提示", "§6您确认同意玩家 §l§b" + name + " §6加入公会吗?", "§a同意加入", "§c拒绝加入", society.getSid(), name));
            }
        }
        if (window instanceof AffirmWindow) {
            AffirmWindow affirmWindow = (AffirmWindow) window;
            if (!affirmWindow.wasClosed()) {
                FormResponseModal response = affirmWindow.getResponse();
                Society society = Utils.getSocietysByID(affirmWindow.getSid());
                int clickedButtonId = response.getClickedButtonId();
                String name = affirmWindow.getName();
                society.getTempApply().remove(name);
                switch (clickedButtonId) {
                    case 0:
                        society.getPost().put(name, new ArrayList<Object>() {
                            {
                                add("玩家");
                                add(0);
                            }
                        });
                    case 1:
                        society.saveData();
                        player.showFormWindow(new ManageWindow(society.getSocietyName() + "§e管理界面", "", society.getSid()));
                        break;
                }
            }
        } else if (window instanceof AffirmUpdateWindow) {
            AffirmUpdateWindow affirmUpdateWindow = (AffirmUpdateWindow) window;
            if (!affirmUpdateWindow.wasClosed()) {
                FormResponseModal response = affirmUpdateWindow.getResponse();
                int clickedButtonId = response.getClickedButtonId();
                Society society = Utils.getSocietysByID(affirmUpdateWindow.getSid());
                switch (clickedButtonId) {
                    case 0:
                        Double societyMoney = society.getSocietyMoney();
                        ArrayList<Object> list = (ArrayList<Object>) societyPlugin.getConfig().get("grade"+society.getGrade());
                        int updateMoney = (int) list.get(1);
                        if (societyMoney < updateMoney) {
                            player.showFormWindow(new MessageWindow("§e错误提示", "§c公会升级需要 §b"+updateMoney+"§c,公会当前资金 §b"+societyMoney, society.getSid()));
                            return;
                        } else {
                            //扣钱操作
                            society.setSocietyMoney(societyMoney - updateMoney);
                            society.setGrade(society.getGrade() + 1);
                            society.saveData();
                            player.showFormWindow(new MessageWindow("§e成功提示", "§a公会升级成功", society.getSid()));
                        }
                        break;
                    case 1:
                        player.showFormWindow(new ManageWindow(society.getSocietyName() + "§e管理界面", "", society.getSid()));
                        break;
                }
            }
        }
    }
}
