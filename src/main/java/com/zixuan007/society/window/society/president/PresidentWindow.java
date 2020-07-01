package com.zixuan007.society.window.society.president;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;
import com.zixuan007.society.window.society.MessageWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zixuan007
 */
public class PresidentWindow extends SimpleWindow implements WindowLoader {
    private long sid;

    public PresidentWindow() {
        super(PluginUtils.getWindowConfigInfo("presidentWindow.title"), "");
    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        Player player= (Player) objects[0];
        this.sid = SocietyUtils.getSocietyByPlayerName(player.getName()).getSid();
        ElementButtonImageData img1 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("presidentWindow.setJobWindow.button.imgPath"));
        ElementButtonImageData img2 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("presidentWindow.playerApplyList.button.imgPath"));
        ElementButtonImageData img3 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("presidentWindow.upGrade.button.imgPath"));
        ElementButtonImageData img4 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("presidentWindow.removeMember.button.imgPath"));
        ElementButtonImageData img5 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("presidentWindow.dissolve.button.imgPath"));

        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("presidentWindow.setJobWindow.button"), img1));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("presidentWindow.playerApplyList.button"), img2));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("presidentWindow.upGrade.button"), img3));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("presidentWindow.removeMember.button"), img4));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("presidentWindow.dissolve.button"), img5));
        return this;
    }

    @Override
    public void onClick(int id, Player player) {
        SetJobWindow setJobWindow;
        ArrayList<String> tempApply;
        PlayerApplyListWindow playerApplyListWindow;
        ArrayList<Object> list;
        Double societyMoney;
        int updateMoney;
        RemoveMemberWindow removeMemberWindow;
        int clickedButtonId = getResponse().getClickedButtonId();
        SocietyPlugin societyPlugin = SocietyPlugin.getInstance();
        Society society = SocietyUtils.getSocietysByID(this.sid);
        FormWindow presidentWindow = WindowManager.getFormWindow(WindowType.PRESIDENT_WINDOW);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");
        switch (clickedButtonId) {
            case 0:
                player.showFormWindow(WindowManager.getFormWindow(WindowType.SET_JOB_WINDOW,player));
                break;
            case 1:
                tempApply = society.getTempApply();
                if (tempApply == null || tempApply.size() <= 0) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,"§c当前还没有玩家申请加入公会",presidentWindow,backButtonName,backButtonImage));
                    return;
                }

                playerApplyListWindow = (PlayerApplyListWindow) WindowManager.getFormWindow(WindowType.PLAYER_APPLY_LIST_WINDOW,player);
                playerApplyListWindow.setBack(true);
                playerApplyListWindow.setParent(this);
                player.showFormWindow(playerApplyListWindow);
                break;
            case 2:
                list = (ArrayList<Object>) societyPlugin.getConfig().get("等级" + society.getGrade());
                if (list == null || list.size() <= 0) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,"§c当前公会已经是最顶级",presidentWindow,backButtonName,backButtonImage));
                    return;
                }
                societyMoney = society.getSocietyMoney();
                updateMoney = (Integer) list.get(1);
                if (societyMoney < updateMoney) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,"§c公会升级需要 §b" + updateMoney + "§c,公会当前资金 §b" + societyMoney,presidentWindow,backButtonName,backButtonImage));
                    return;
                }
                society.setSocietyMoney(societyMoney - updateMoney);
                society.setGrade(society.getGrade() + 1);
                society.saveData();
                player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,"§a公会升级成功",presidentWindow,backButtonName,backButtonImage));
                break;

            case 3:
                if (society.getPost().size() == 1) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,"§c当前没有可以移除的人员",presidentWindow,backButtonName,backButtonImage));
                    return;
                }

                Arrays.asList(society.getPost().keySet().toArray(new String[0]));
                removeMemberWindow = (RemoveMemberWindow) WindowManager.getFormWindow(WindowType.Member_List_Window,player);
                removeMemberWindow.setParent(this);
                player.showFormWindow( removeMemberWindow);
                break;
            case 4:
                FormWindow societyWindow = WindowManager.getFormWindow(WindowType.SOCIETY_WINDOW, player);
                player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,"§a成功解散 §b" + society.getSocietyName(),null,"返回主界面",backButtonImage));
                SocietyUtils.sendMemberTitle("§b" + player.getName() + "§c解散了公会", society);
                //移除指定的公会商店
                SocietyUtils.removeSocietyShopBySid(society);
                SocietyUtils.societies.remove(society);
                SocietyUtils.removeSociety(society.getSocietyName());
                break;
            default:
                break;
        }
    }


}

