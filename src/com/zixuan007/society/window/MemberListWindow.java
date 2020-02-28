package com.zixuan007.society.window;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MemberListWindow extends FormWindowSimple {
    private long sid;
    private int currentPage;

    public MemberListWindow(String title, String content, long sid, int currentPage) {
        super(title, content);
        this.currentPage=currentPage;
        this.sid=sid;
        ArrayList<Society> societies = SocietyPlugin.getInstance().getSocieties();
        Society society=null;
        for (Society society1 : societies) {
            if ((society1.getSid() == sid)) {
                society=society1;
            }
        }
        int limit=11;
        setContent("当前第"+currentPage+"页 总页数: "+Utils.getTotalMemberPage(society,limit));
        List<String> memberList = Utils.getMemberList(society,currentPage,limit);
        if(currentPage != 1){
            addButton(new ElementButton("上一成员级列表"));
        }
        for (int i = 0; i < memberList.size(); i++) {
            if(memberList.get(i).equals(society.getPresidentName())) continue;
            addButton(new ElementButton(memberList.get(i)));
        }
        if(memberList.size() == limit){
            List<String> memberList1 = Utils.getMemberList(society, currentPage + 1,limit);
            if(memberList1 != null){
                addButton(new ElementButton("下级列表"));
            }
        }
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
