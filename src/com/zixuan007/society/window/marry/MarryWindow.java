package com.zixuan007.society.window.marry;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import com.zixuan007.society.domain.Lang;
import com.zixuan007.society.utils.MarryUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowManager;

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
        addButton(new ElementButton("公共资金",buttonImageData));
        addButton(new ElementButton("传送至情侣身边",buttonImageData));
    }

    @Override
    public void onClick(int id, Player player) {
        switch (id){
            case 0:
                ProposeWindow proposeWindow = WindowManager.getProposeWindow();
                proposeWindow.setBack(true);
                proposeWindow.setParent(this);
                player.showFormWindow(proposeWindow);
                break;
            case 1:
                if(!MarryUtils.isMarry(player.getName())){

                    player.showFormWindow(WindowManager.getMessageWindow("§e您当前还没有伴侣,请先求婚",this,"返回上级"));
                    return;
                }
                player.showFormWindow(WindowManager.getAddPublicFunds());
        }
    }
}
