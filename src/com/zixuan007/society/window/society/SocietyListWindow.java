package com.zixuan007.society.window.society;

import cn.nukkit.Player;
import cn.nukkit.event.Event;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.event.society.PlayerApplyJoinSocietyEvent;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.ModalWindow;
import com.zixuan007.society.window.SimpleWindow;
import com.zixuan007.society.window.WindowManager;
import java.util.List;

public class SocietyListWindow extends SimpleWindow {
    private List<Society> societyList;
    private int cuurentPage;
    private int limit = 10;
    private int totalPage;

    public SocietyListWindow(String title, String content, int cuurentPage, int totalPage, List<Society> societyList) {
        super(title, content);
        this.cuurentPage = cuurentPage;
        this.totalPage = totalPage;
        if (cuurentPage != 1) addButton(new ElementButton("上一页"));
        this.societyList = societyList;
        for (Society society : societyList) {
            addButton(new ElementButton("§e公会ID §b" + society.getSid() + " §e公会名称 §b" + society.getSocietyName() + " §e会长 §b" + society.getPresidentName()));
        }
        if (cuurentPage < totalPage) addButton(new ElementButton("下一页"));

    }

    public void nextPage(int cuurentPage, Player player) {
        SocietyListWindow societyListWindow = WindowManager.getSocietyListWindow(++cuurentPage, getParent());
        player.showFormWindow((FormWindow)societyListWindow);
    }

    public void upPage(int cuurentPage, Player player) {
        SocietyListWindow societyListWindow = WindowManager.getSocietyListWindow(--cuurentPage, getParent());
        player.showFormWindow((FormWindow)societyListWindow);
    }

    public void onClick(int id, Player player) {
        Society society;
        if (id == 0 && this.cuurentPage != 1) {
            upPage(this.cuurentPage, player);
            return;
        }
        if (id == 10) {
            nextPage(this.cuurentPage, player);
            return;
        }
        if (SocietyUtils.isJoinSociety(player.getName())) {
            MessageWindow messageWindow = WindowManager.getMessageWindow("§c您当前已经加入过公会,请勿二次点击加入", getParent(), "返回上级");
            player.showFormWindow((FormWindow)messageWindow);
            return;
        }
        ModalWindow affirmWindow = null;

        if (this.cuurentPage == 1) {
            society = this.societyList.get(id);
            affirmWindow = WindowManager.getAffrimWindow("§e您确定要加入 §b" + ((Society)this.societyList.get(id)).getSocietyName() + " §e公会吗?", "§a确认加入", "§c取消加入");
        } else {
            society = this.societyList.get(id - 1);
            affirmWindow = WindowManager.getAffrimWindow("§e您确定要加入 §b" + ((Society)this.societyList.get(id - 1)).getSocietyName() + " §e公会吗?", "§a确认加入", "§c取消加入");
        }
        affirmWindow.setButtonClickedListener((affirm, player1) -> {
            if (affirm.booleanValue()) {
                SocietyPlugin.getInstance().getServer().getPluginManager().callEvent((Event)new PlayerApplyJoinSocietyEvent(player1, society));
            } else {
                player1.showFormWindow(getParent());
            }
        });

        player.showFormWindow((FormWindow)affirmWindow);
    }


    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}


