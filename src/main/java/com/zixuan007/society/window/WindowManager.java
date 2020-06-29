package com.zixuan007.society.window;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Lang;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.marry.AddPublicFunds;
import com.zixuan007.society.window.marry.MarryWindow;
import com.zixuan007.society.window.marry.MoneyRankWindow;
import com.zixuan007.society.window.marry.ProposeWindow;
import com.zixuan007.society.window.society.ContributionRankingWindow;
import com.zixuan007.society.window.society.ContributionWindow;
import com.zixuan007.society.window.society.CreateSocietyWindow;
import com.zixuan007.society.window.society.LevelRankWindow;
import com.zixuan007.society.window.society.MemberListWindow;
import com.zixuan007.society.window.society.MessageWindow;
import com.zixuan007.society.window.society.president.PlayerApplyListWindow;
import com.zixuan007.society.window.society.SocietyListWindow;
import com.zixuan007.society.window.society.SocietyWindow;
import com.zixuan007.society.window.society.president.PresidentWindow;
import com.zixuan007.society.window.society.president.RemoveMemberWindow;
import com.zixuan007.society.window.society.president.SetJobWindow;
import com.zixuan007.society.window.society.shop.CreateShopWindow;
import com.zixuan007.society.window.title.TitleWindow;
import com.zixuan007.society.window.title.admin.CreateTitleShopWindow;
import com.zixuan007.society.window.title.admin.RemoveTitleWindow;
import com.zixuan007.society.window.title.admin.SetTitleWindow;
import com.zixuan007.society.window.title.admin.TitleManagerWindow;
import com.zixuan007.society.window.vip.AdvancedPrivilegeWindow;
import com.zixuan007.society.window.vip.PrivilegeWindow;
import com.zixuan007.society.window.vip.admin.PrivilegeListWindow;
import com.zixuan007.society.window.vip.admin.PrivilegeManagerWindow;
import com.zixuan007.society.window.vip.admin.RemovePrivilegeWindow;
import com.zixuan007.society.window.vip.admin.SetPrivilegeWindow;

import java.util.HashMap;
import java.util.List;

/**
 * 窗口管理界面
 * @author zixuan007
 */
public class WindowManager {

    /**
     * 公会插件基类
     */
    public static SocietyPlugin societyPlugin = SocietyPlugin.getInstance();

    private static HashMap<WindowType,Class> registerWindow=new HashMap<>();
    //储存玩家已经打开过的GUI
    private static final HashMap<String, FormWindow> alreadyOpenForms = new HashMap();

    private WindowManager() {

    }

    /**
     * 获取到指定类型的表单窗口
     * @param windowType
     * @param parameter
     * @return
     */
    public static FormWindow getFromWindow(WindowType windowType, Object ...parameter){
        Class clazz = registerWindow.get(windowType);
        if(clazz != null){
            try {
                if(alreadyOpenForms.containsKey(windowType.windowName)){
                    FormWindow formWindow = alreadyOpenForms.get(windowType.windowName);
                    if(formWindow instanceof WindowLoader){
                        return ((WindowLoader) formWindow).init(parameter);
                    }
                }else{
                    FormWindow formWindow = (FormWindow) clazz.newInstance();
                    if(formWindow instanceof WindowLoader){
                        alreadyOpenForms.put(windowType.windowName,formWindow);
                        return  ((WindowLoader) formWindow).init(parameter);
                    }
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    public static SocietyWindow getSocietyWindow(Player player) {
        return new SocietyWindow(player);
    }

    public static MessageWindow getMessageWindow(String content, FormWindow formWindow, String buttonName) {
        MessageWindow messageWindow = new MessageWindow(Lang.messageWindow_Title, content);
        messageWindow.setParent(formWindow);
        messageWindow.addButton(new ElementButton(buttonName));
        return messageWindow;
    }

    public static CreateSocietyWindow getCreateSocietyWindow(boolean isBack) {
        CreateSocietyWindow createSocietyWindow = new CreateSocietyWindow();
        createSocietyWindow.setBack(isBack);
        return createSocietyWindow;
    }

    public static ModalWindow getAffrimWindow(String content, String trueButtonName, String falseButtoName) {
        ModalWindow modalWindow = new ModalWindow(Lang.affrimWindow_Title, content, trueButtonName, falseButtoName);
        return modalWindow;
    }

    public static SocietyListWindow getSocietyListWindow(int cuurent, FormWindow formWindow) {
        List<Society> societyList = SocietyUtils.getSocietyList(cuurent);
        int limit = 10;
        int totalPage = SocietyUtils.getSocietyListTotalPage(cuurent, limit);
        String content = "§a当前第 §b" + cuurent + " §a总页数 §b" + totalPage;
        SocietyListWindow societyListWindow = new SocietyListWindow(Lang.societyListWindow_Title, content, cuurent, totalPage, societyList);
        societyListWindow.setBack(true);
        societyListWindow.setParent(formWindow);
        societyListWindow.setLimit(limit);
        return societyListWindow;
    }

    public static MemberListWindow getMemberListWindow(Society society, List<String> memberList, FormWindow formWindow) {
        MemberListWindow memberListWindow = new MemberListWindow(society, memberList);
        memberListWindow.setBack(true);
        memberListWindow.setParent(formWindow);
        return memberListWindow;
    }

    public static ContributionRankingWindow getContributionRankingWindow(FormWindow formWindow) {
        ContributionRankingWindow contributionRankingWindow = new ContributionRankingWindow();
        contributionRankingWindow.setBack(true);
        contributionRankingWindow.setParent(formWindow);
        return contributionRankingWindow;
    }

    public static LevelRankWindow getLevelRankWindow(FormWindow formWindow) {
        LevelRankWindow levelRankWindow = new LevelRankWindow();
        levelRankWindow.setBack(true);
        levelRankWindow.setParent(formWindow);
        return levelRankWindow;
    }

    public static PresidentWindow getChairmanWindow(long sid) {
        PresidentWindow chairmanWindow = new PresidentWindow(sid);
        return chairmanWindow;
    }

    public static SetJobWindow getSetJobWindow(FormWindow formWindow) {
        SetJobWindow setJobWindow = new SetJobWindow();
        setJobWindow.setBack(true);
        setJobWindow.setParent(formWindow);
        return setJobWindow;
    }

    public static PlayerApplyListWindow getPlayerApplyListWindow(List<String> tempApply, long sid) {
        PlayerApplyListWindow playerApplyListWindow = new PlayerApplyListWindow(tempApply, sid);
        return playerApplyListWindow;
    }

    public static ContributionWindow getContributionWindow(long sid) {
        return new ContributionWindow(sid);
    }

    public static RemoveMemberWindow getRemoveMemberWindow(long sid, List<String> memberList) {
        return new RemoveMemberWindow(sid, memberList);
    }

    public static TitleWindow getTitleWindow(String playerName){
        return new TitleWindow(playerName);
    }

    public static TitleManagerWindow getTitleManagerWindow() {
        TitleManagerWindow titleManagerWindow = new TitleManagerWindow();
        return titleManagerWindow;
    }

    public static SetTitleWindow getSetTitleWindow(FormWindow formWindow) {
        SetTitleWindow setTitleWindow = new SetTitleWindow();
        setTitleWindow.setBack(true);
        setTitleWindow.setParent(formWindow);
        return setTitleWindow;
    }

    public static RemoveTitleWindow getRemoveTitleWindow(FormWindow formWindow) {
        RemoveTitleWindow removeTitleWindow = new RemoveTitleWindow();
        removeTitleWindow.setBack(true);
        removeTitleWindow.setParent(formWindow);
        return removeTitleWindow;
    }


    public static CreateTitleShopWindow getCreateTitleShopWindow() {
        return new CreateTitleShopWindow();
    }

    public static MarryWindow getMarryWindow(){
        return new MarryWindow();
    }

    public static ProposeWindow getProposeWindow(){
        return new ProposeWindow();
    }

    public static AddPublicFunds getAddPublicFunds(){
        return new AddPublicFunds();
    }

    public static MoneyRankWindow getMoneyRankWindow(){
        return new MoneyRankWindow();
    }

    public static SetPrivilegeWindow getSetPrivilegeWindow(){
        return new SetPrivilegeWindow();
    }

    public static PrivilegeManagerWindow getPrivilegeManagerWindow(){
        return new PrivilegeManagerWindow();
    }

    public static RemovePrivilegeWindow getRemovePrivilegeWindow(){
        return new RemovePrivilegeWindow();
    }

    public static PrivilegeWindow getVipWindow(){
        return new PrivilegeWindow();
    }

    public static AdvancedPrivilegeWindow getSvipWindow(){
        return new AdvancedPrivilegeWindow();
    }

    public static PrivilegeListWindow getPrivilegeListWindow(){
        return new PrivilegeListWindow();
    }

    public static CreateShopWindow getCreateShopWindow(Player player){
        return new CreateShopWindow(player);
    }

    public static HashMap<WindowType, Class> getRegisterWindow() {
        return registerWindow;
    }

    public static void setRegisterWindow(HashMap<WindowType, Class> registerWindow) {
        WindowManager.registerWindow = registerWindow;
    }
}