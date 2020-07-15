package com.zixuan007.society.window.society;

import cn.nukkit.Player;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;

/**
 * @author zixuan007
 */
public class SocietyInfoWindow extends SimpleWindow implements WindowLoader {


    public SocietyInfoWindow() {
        super("公会信息窗口", "");
    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        Player player = (Player) objects[0];
        Society society = SocietyUtils.getSocietyByPlayerName(player.getName());

        StringBuilder sb = new StringBuilder();
        sb.append("§eSID§f: §6"+society.getSid()+"\n");
        sb.append("§e公会名称§f: §6"+society.getSocietyName()+"\n");
        sb.append("§e会长名称§f: §6"+society.getPresidentName()+"\n");
        sb.append("§e创建时间§f: §6"+society.getCreateTime()+"\n");
        sb.append("§e公会贡献§f: §6"+society.getSocietyMoney()+"\n");
        if(society.getPosition() != null && society.getPosition().length() > 0 ){
            sb.append("§e公会坐标§f: §6"+society.getPosition());
        }
        if(society.getDescription() != null){
            sb.append("§e公会简介§f: §6"+society.getDescription());
        }
        setContent(sb.toString());
        return this;
    }
}
