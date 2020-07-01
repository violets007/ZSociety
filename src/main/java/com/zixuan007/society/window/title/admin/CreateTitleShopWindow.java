package com.zixuan007.society.window.title.admin;
import cn.nukkit.Player;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.utils.TitleUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

import java.util.HashMap;

/**
 * @author zixuan007
 */
public class CreateTitleShopWindow extends CustomWindow implements WindowLoader {

    public CreateTitleShopWindow() {
        super(PluginUtils.getWindowConfigInfo("createTitleShopWindow.title"));

    }

    @Override
    public FormWindow init(Object... objects) {
        getElements().clear();
        addElement(new ElementInput("", "需要售卖的称号"));
        addElement(new ElementInput("", "金额"));
        return this;
    }


    @Override
    public void onClick(FormResponseCustom response, Player player) {
        final String title = response.getInputResponse(0);
        final String money = response.getInputResponse(1);
        FormWindow createTitleShopWindow = WindowManager.getFormWindow(WindowType.CREATE_TITLE_SHOP_WINDOW);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");

        if (title.isEmpty() || title.equals(" ")) {
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,"§c售卖的称号不能为空",createTitleShopWindow,backButtonName,backButtonImage));
            return;
        }
        if ("".equals(money) || !SocietyUtils.isNumeric(money)) {
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,"§c输入的金额不是数字",createTitleShopWindow,backButtonName,backButtonImage));
            return;
        }
        if (SocietyPlugin.getInstance().getTitleShopConfig().get(title) != null) {
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,"§c此称号已经存在",createTitleShopWindow,backButtonName,backButtonImage));
            return;
        }
        TitleUtils.onCreateName.put(player.getName(),new HashMap<String,Object>(){
            {
                put("title",title);
                put("money",money);
            }
        });

        player.sendMessage("§e请点击贴在墙上的木牌");
    }


}


