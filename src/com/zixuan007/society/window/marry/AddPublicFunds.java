package com.zixuan007.society.window.marry;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import com.zixuan007.society.domain.Lang;
import com.zixuan007.society.window.CustomWindow;

public class AddPublicFunds extends CustomWindow {
    public AddPublicFunds() {
        super(Lang.AddPublicFundsWindow_Title);
        addElement(new ElementInput("","请输入增加的金额"));

    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {
        String str_Money = response.getInputResponse(0);

    }
}
