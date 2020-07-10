package com.zixuan007.society.window.society;

import cn.nukkit.Player;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zixuan007
 */
public class MemberListWindow extends SimpleWindow implements WindowLoader {
    private List<String> memberList;
    private Society society;

    public MemberListWindow() {
        super(PluginUtils.getWindowConfigInfo("memberListWindow.title"), "");

    }

    @Override
    public FormWindow init(Object... objects) {
        getButtons().clear();
        Player player= (Player) objects[0];
        if(objects.length >= 2 && objects[1] != null){
            setParent((FormWindow) objects[1]);
            setBack(true);
        }
        this.society = SocietyUtils.getSocietyByPlayerName(player.getName());
        this.memberList= new ArrayList(society.getPost().keySet());
        StringBuilder sb = new StringBuilder();
        sb.append("§l§d公会成员列表\n");
        /*for (int i = memberList.size()-1; i >= 0; i--) {
            String name = memberList.get(i);
            String postByName = SocietyUtils.getPostByName(name, society);
            sb.append("职位>> §c" + postByName + " §f名称>> §b§l" + name + "\n");
        }*/
        memberList.forEach(name -> {
            String postByName = SocietyUtils.getPostByName(name, society);
            sb.append("职位>> §c" + postByName + " §f名称>> §b§l" + name + "\n");
        });
        setContent(sb.toString());
        return this;
    }


    public Society getSociety() {
        return this.society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }


}


