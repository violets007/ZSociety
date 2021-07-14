package com.zixuan007.society.window.society.president;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.level.Position;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.domain.SocietyWar;
import com.zixuan007.society.domain.WarStatus;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zixuan007
 */
public class PresidentWindow extends SimpleWindow implements WindowLoader {
    private long sid;

    public PresidentWindow() {
        super("", "");
    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        Player player = (Player) objects[0];
        this.sid = SocietyUtils.getSocietyByPlayerName(player.getName()).getSid();
        String societyName = SocietyUtils.getSocietysByID(sid).getSocietyName();
        setTitle(PluginUtils.getWindowConfigInfo("presidentWindow.title").replace("${societyName}", societyName));
        ElementButtonImageData img1 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("presidentWindow.setJobWindow.button.imgPath"));
        ElementButtonImageData img2 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("presidentWindow.playerApplyList.button.imgPath"));
        ElementButtonImageData img3 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("presidentWindow.upGrade.button.imgPath"));
        ElementButtonImageData img4 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("presidentWindow.removeMember.button.imgPath"));
        ElementButtonImageData img5 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("presidentWindow.dissolve.button.imgPath"));
        ElementButtonImageData img6 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("presidentWindow.setSpawn.button.imgPath"));
        ElementButtonImageData img7 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("presidentWindow.modifySocietyInfoWindow.button.imgPath"));
        ElementButtonImageData img8 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("presidentWindow.sendSocietyWar.button.imgPath"));
        ElementButtonImageData img9 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("presidentWindow.sendSocietyWarStatus.button.imgPath"));

        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("presidentWindow.setJobWindow.button"), img1));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("presidentWindow.playerApplyList.button"), img2));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("presidentWindow.upGrade.button"), img3));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("presidentWindow.removeMember.button"), img4));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("presidentWindow.dissolve.button"), img5));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("presidentWindow.setSpawn.button"), img6));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("presidentWindow.modifySocietyInfoWindow.button"), img7));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("presidentWindow.sendSocietyWar.button"), img8));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("presidentWindow.lookSocietyWarStatus.button"), img9));
        return this;
    }

    @Override
    public void onClick(int id, Player player) {
        /*ArrayList<String> tempApply;
        PlayerApplyListWindow playerApplyListWindow;*/
        ArrayList<Object> list;
        Double societyMoney;
        int updateMoney;
        RemoveMemberWindow removeMemberWindow;
        int clickedButtonId = getResponse().getClickedButtonId();
        SocietyPlugin societyPlugin = SocietyPlugin.getInstance();
        Society society = SocietyUtils.getSocietyByPlayerName(player.getName());
        FormWindow presidentWindow = WindowManager.getFormWindow(WindowType.PRESIDENT_WINDOW, player);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");

        String closeButtonName = PluginUtils.getWindowConfigInfo("messageWindow.close.button");
        String closeButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.close.button.imgPath");
        switch (clickedButtonId) {
            case 0:
                player.showFormWindow(WindowManager.getFormWindow(WindowType.SET_JOB_WINDOW, player));
                break;
            case 1:
                if (society.getTempApply() == null || society.getTempApply().size() <= 0) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.presidentWindow.isApplyPlayer"), presidentWindow, backButtonName, backButtonImage));
                    return;
                }

                player.showFormWindow(WindowManager.getFormWindow(WindowType.PLAYER_APPLY_LIST_WINDOW, player));
                break;
            case 2:
                list = (ArrayList<Object>) societyPlugin.getConfig().get("等级" + society.getGrade());
                if (list == null || list.size() <= 0) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.presidentWindow.maxGrade"), presidentWindow, backButtonName, backButtonImage));
                    return;
                }
                societyMoney = society.getSocietyMoney();
                updateMoney = (Integer) list.get(1);
                if (societyMoney < updateMoney) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.presidentWindow.upGradeUnableMoney", new String[]{"${updateMoney}", "${societyMoney}"}, new String[]{updateMoney + "", societyMoney + ""}), presidentWindow, backButtonName, backButtonImage));
                    return;
                }
                society.setSocietyMoney(societyMoney - updateMoney);
                society.setGrade(society.getGrade() + 1);
                SocietyUtils.saveSociety(society);
                player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.presidentWindow.upGrade"), presidentWindow, backButtonName, backButtonImage));
                break;

            case 3:
                if (society.getPost().size() == 1) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.presidentWindow.notMember"), presidentWindow, backButtonName, backButtonImage));
                    return;
                }

                player.showFormWindow(WindowManager.getFormWindow(WindowType.REMOVE_MEMBER_WINDOW, player));
                break;
            case 4:
                player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.presidentWindow.dissolveSociety", new String[]{"${societyName}"}, new String[]{society.getSocietyName()}), null, closeButtonName, closeButtonImage));
                SocietyUtils.sendMemberTitle(PluginUtils.getLanguageInfo("message.presidentWindow.presidentDissolveSociety", new String[]{"${playerName}"}, new String[]{player.getName()}), society);
                //移除指定的公会商店
                SocietyUtils.removeSocietyShopBySid(society);
                SocietyUtils.getSocieties().remove(society);
                SocietyUtils.removeSociety(society.getSocietyName());
                break;
            case 5:
                Position position = player.getPosition();
                double x = position.x;
                double y = position.y;
                double z = position.z;
                String levelName = position.getLevel().getName();
                society.setPosition(x + "," + y + "," + z + "," + levelName);
                SocietyUtils.saveSociety(society);
                player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.presidentWindow.setSpawn"), null, backButtonName, backButtonImage));
                break;
            case 6:
                player.showFormWindow(WindowManager.getFormWindow(WindowType.MODIFY_SOCIETY_INFO_WINDOW, player));
                break;
            case 7:
                if (!SocietyUtils.isSetSocietyWarData()) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.presidentWindow.noSetWarData"), presidentWindow, backButtonName, backButtonImage));
                    return;
                }


                player.showFormWindow(WindowManager.getFormWindow(WindowType.SEND_SOCIETY_WAR_WINDOW, player));
                break;

            case 8:
                if (SocietyUtils.societyWars.size() == 0) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.presidentWindow.notWarData"), presidentWindow, backButtonName, backButtonImage));
                    return;
                }

                if (SocietyUtils.getSocietyWarBySociety(SocietyUtils.getSocietyByPlayerName(player.getName())) == null) {
                    player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.presidentWindow.notWarData"), presidentWindow, backButtonName, backButtonImage));
                    return;
                }

                ModalWindow modalWindow = (ModalWindow) WindowManager.getFormWindow(WindowType.MODAL_WINDOW, "你是否接受当前发起的公会战", "接受", "拒绝");
                modalWindow.setButtonClickedListener((affrim, player1) -> {
                    SocietyWar societyWar = SocietyUtils.getSocietyWarBySociety(society);
                    if (affrim) {
                        //检查当前公会战书是否过期
                        boolean expiredWar = SocietyUtils.isExpiredWar(societyWar);
                        if (expiredWar) {
                            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("当前发起的公会战已经过期!"), presidentWindow, backButtonName, backButtonImage));
                            return;

                        }

                        societyWar.setStatus(WarStatus.WAIT.toString());
                        SocietyUtils.saveSocietyWar(societyWar);
                    } else {

                    }
                });
                break;
            default:
                break;
        }
    }


}

