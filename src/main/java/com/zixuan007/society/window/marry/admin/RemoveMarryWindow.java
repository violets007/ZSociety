package com.zixuan007.society.window.marry.admin;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import com.zixuan007.society.domain.Marry;
import com.zixuan007.society.utils.MarryUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowManager;

import java.util.ArrayList;

/**
 * @author zixuan007
 */
public class RemoveMarryWindow extends SimpleWindow {
    public RemoveMarryWindow() {
        super("移除夫妻窗口", "");
        if (MarryUtils.marrys.size() > 0) {
            ArrayList<String> midList = new ArrayList<>();
            for (Marry marry : MarryUtils.marrys) {
               addButton(new ElementButton(marry.getMid()+" 丈夫: §b"+marry.getPropose()+"§r 妻子: §a"+marry.getRecipient()));
            }
        }else{
            setContent("当前还没有一对夫妻");
        }
    }

    @Override
    public void onClick(int id, Player player) {
        if(getButtons().size() < 1) {
            return;
        }
        String midStr = getButtons().get(id).getText().split(" ")[0];
        int mid = Integer.parseInt(midStr);
        Marry marry = MarryUtils.getMarryByID(mid);
        MarryUtils.removeMarry(marry);

        player.showFormWindow(WindowManager.getMessageWindow("§a成功移除mid为 §e"+marry.getMid()+" §a的夫妻",null,"关闭"));
    }
}
