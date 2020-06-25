package com.zixuan007.society.window.society;

import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Lang;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.SimpleWindow;
import java.util.List;

public class MemberListWindow extends SimpleWindow {
    private List<String> memberList;
    private Society society;

    public MemberListWindow(Society society, List<String> memberList) {
        super(Lang.memberListWindow_Title, "");
        this.society = society;
        StringBuilder sb = new StringBuilder();
        sb.append("§l§d公会成员列表\n");
        memberList.forEach(name -> {
            String postByName = SocietyUtils.getPostByName(name, society);
            sb.append("职位>> §c" + postByName + " §f名称>> §b§l" + name + "\n");
        });
        setContent(sb.toString());
    }

    public List<String> getMemberList() {
        return this.memberList;
    }

    public void setMemberList(List<String> memberList) {
        this.memberList = memberList;
    }

    public Society getSociety() {
        return this.society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }
}


