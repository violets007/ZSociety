package com.zixuan007.society.window.title.admin;
import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Lang;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowManager;

public class TitleManagerWindow extends SimpleWindow {
    public TitleManagerWindow() {
        super(Lang.titleManagerWindow_Title, "");
        ElementButtonImageData buttonImageData = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, Lang.titleManagerWindow_SetTitleButton_ImgPath);
        addButton(new ElementButton(Lang.titleManagerWindow_SetTitleButton,buttonImageData));
        buttonImageData = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, Lang.titleManagerWindow_RemoveTitleButton_ImgPath);
        addButton(new ElementButton(Lang.titleManagerWindow_RemoveTitleButton,buttonImageData));
        buttonImageData = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, Lang.titleManagerWindow_createTitleShopButton_ImgPath);
        addButton(new ElementButton(Lang.titleManagerWindow_createTitleShopButton,buttonImageData));
    }

    public void onClick(int id, Player player) {
        switch (id) {
            case 0:
                player.showFormWindow((FormWindow)WindowManager.getSetTitleWindow((FormWindow)this));
                break;
            case 1:
                player.showFormWindow((FormWindow)WindowManager.getRemoveTitleWindow((FormWindow)this));
                break;
            case 2:
                player.showFormWindow((FormWindow)WindowManager.getCreateTitleShopWindow());
                break;
        }
    }
}
