package com.zixuan007.society.window;

import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.marry.AddPublicFunds;
import com.zixuan007.society.window.marry.MoneyRankWindow;
import com.zixuan007.society.window.marry.ProposeWindow;
import com.zixuan007.society.window.society.president.PlayerApplyListWindow;
import com.zixuan007.society.window.society.SocietyListWindow;
import com.zixuan007.society.window.society.president.SetJobWindow;
import com.zixuan007.society.window.vip.admin.PrivilegeManagerWindow;

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

    private final static HashMap<WindowType,Class> registerWindow=new HashMap<>();
    /**
     * 储存玩家已经打开过的GUI
     */
    private static HashMap<String, FormWindow> alreadyOpenForms = new HashMap();

    private WindowManager() {

    }

    /**
     * 获取到指定类型的表单窗口
     * @param windowType 窗口类型
     * @param parameter 窗口初始化数据
     * @return
     */
    public static FormWindow getFormWindow(WindowType windowType, Object ...parameter){
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


    public static SocietyListWindow getSocietyListWindow(int cuurent, WindowType windowType) {
        List<Society> societyList = SocietyUtils.getSocietyList(cuurent);
        int limit = 10;
        int totalPage = SocietyUtils.getSocietyListTotalPage(cuurent, limit);
        String content = "§a当前第 §b" + cuurent + " §a总页数 §b" + totalPage;
        FormWindow formWindow = WindowManager.getFormWindow(windowType);
        SocietyListWindow societyListWindow= (SocietyListWindow) WindowManager.getFormWindow(WindowType.SOCIETY_LIST_WINDOW,content,cuurent,totalPage,societyList,formWindow);
        return societyListWindow;
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

    public static PrivilegeManagerWindow getPrivilegeManagerWindow(){
        return new PrivilegeManagerWindow();
    }

    public static HashMap<WindowType, Class> getRegisterWindow() {
        return registerWindow;
    }


}