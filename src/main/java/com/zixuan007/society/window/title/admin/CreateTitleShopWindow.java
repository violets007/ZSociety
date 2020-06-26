package com.zixuan007.society.window.title.admin;
import cn.nukkit.Player;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Lang;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.utils.TitleUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowManager;

import java.util.HashMap;

/**
 * @author zixuan007
 */
public class CreateTitleShopWindow extends CustomWindow {

    public CreateTitleShopWindow() {
        super(Lang.createTitleShopWindow_title);
        init();
    }

    public void init(){
        addElement((Element)new ElementInput("", "需要售卖的称号"));
        addElement((Element)new ElementInput("", "金额"));
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {
        final String title = response.getInputResponse(0);
        final String money = response.getInputResponse(1);

        if (title.isEmpty() || title.equals(" ")) {
            player.showFormWindow((FormWindow)WindowManager.getMessageWindow("§c售卖的称号不能为空", (FormWindow)this, "返回上级"));
            return;
        }
        if (money.equals("") || !SocietyUtils.isNumeric(money)) {
            player.showFormWindow((FormWindow)WindowManager.getMessageWindow("§c输入的金额不是数字", (FormWindow)this, "返回上级"));
            return;
        }
        if (SocietyPlugin.getInstance().getTitleShopConfig().get(title) != null) {
            player.showFormWindow((FormWindow)WindowManager.getMessageWindow("§c此称号已经存在", (FormWindow)this, "返回上级"));
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


