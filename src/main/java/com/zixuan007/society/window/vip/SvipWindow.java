package com.zixuan007.society.window.vip;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.domain.Vip;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.PrivilegeUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

/**
 * @author zixuan007
 */
public class SvipWindow extends SimpleWindow implements WindowLoader {
    public SvipWindow() {
        super(PluginUtils.getWindowConfigInfo("svipwindow.title"), "");
    }

    @Override
    public void onClick(int id, Player player) {
        switch (id) {
            case 0:
                player.showFormWindow(WindowManager.getFromWindow(WindowType.VIPWINDOW, player));
                break;
            case 1:
                boolean creative = player.isCreative();
                player.setGamemode(!creative ? 1 : 0);
                player.sendMessage(">> §a创造模式 §e" + (!creative ? "开启" : "关闭"));
                break;
            case 2:
                Vip svip = PrivilegeUtils.getPivilegeByPlayerName(player.getName());
                player.showFormWindow(WindowManager.getFromWindow(WindowType.PRIVILEGEWINDOW,player, svip));
                break;
        }
    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        Player player = (Player) objects[0];
        ElementButtonImageData img1 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("svipwindow.openvip.button.imgpath"));
        ElementButtonImageData img2 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("svipwindow.switchcreate.button.imgpath"));
        ElementButtonImageData img3 = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, PluginUtils.getWindowConfigInfo("svipwindow.opensvip.button.imgpath"));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo("svipwindow.openvip.button"),img1));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo(player, "svipwindow.switchcreate.button"),img2));
        addButton(new ElementButton(PluginUtils.getWindowConfigInfo(player, "svipwindow.opensvip.button"),img3));
        return this;
    }
}
