package com.zixuan007.society.window.society;

import cn.nukkit.Player;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowLoader;

import java.util.*;

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
        Player player = (Player) objects[0];
        if (objects.length >= 2 && objects[1] != null) {
            setParent((FormWindow) objects[1]);
            setBack(true);
        }
        this.society = SocietyUtils.getSocietyByPlayerName(player.getName());
        StringBuilder sb = new StringBuilder();
        sb.append("§l§d公会成员列表\n");

        ArrayList<ArrayList<Object>> memberList = new ArrayList<>();

        for (Map.Entry<String, ArrayList<Object>> entry : society.getPost().entrySet()) {
            String playerName = entry.getKey();
            ArrayList<Object> value = entry.getValue();
            String postName = (String) value.get(0);
            Integer postGrade = ((int) value.get(1));
            memberList.add(new ArrayList<Object>() {
                {
                    add(playerName);
                    add(postName);
                    add(postGrade);
                }
            });
        }

        memberList.sort(new Comparator<ArrayList<Object>>() {
            @Override
            public int compare(ArrayList<Object> o1, ArrayList<Object> o2) {
                Integer postGrade1 = (Integer) o1.get(2);
                Integer postGrade2 = (Integer) o2.get(2);
                return (postGrade1 > postGrade2) ? -1 : (postGrade2 > postGrade1) ? 1 : 0;
            }
        });


        memberList.forEach(arrayList -> {
            String playerName = (String) arrayList.get(0);
            String postName = (String) arrayList.get(1);
            Integer postGrade = (Integer) arrayList.get(2);
            sb.append("§r职位: §c" + postName + " §f名称: §b§l" + playerName + " §r职位等级: §6" + postGrade + "\n");
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


