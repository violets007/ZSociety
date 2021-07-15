package com.zixuan007.society.window;

import cn.nukkit.Player;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.pojo.Society;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.marry.AddPublicFunds;
import com.zixuan007.society.window.marry.MoneyRankWindow;
import com.zixuan007.society.window.society.SocietyListWindow;

import java.util.HashMap;
import java.util.List;

/**
 * 窗口管理界面
 *
 * @author zixuan007
 */
public class WindowManager {

    /**
     * 公会插件基类
     */
    public static SocietyPlugin societyPlugin = SocietyPlugin.getInstance();

    private final static HashMap<WindowType, Class> registerWindow = new HashMap<>();
    /**
     * 储存玩家已经打开过的GUI
     */
    private static HashMap<String, FormWindow> alreadyOpenForms = new HashMap();

    private WindowManager() {

    }

    /**
     * 获取到指定类型的表单窗口
     *
     * @param windowType 窗口类型
     * @param parameter  窗口初始化数据
     * @return
     */
    public static FormWindow getFormWindow(WindowType windowType, Object... parameter) {
        Class clazz = registerWindow.get(windowType);
        FormWindow formWindow;
        if (clazz != null) {
            try {
                if (alreadyOpenForms.containsKey(windowType.windowName)) {
                    formWindow = alreadyOpenForms.get(windowType.windowName);
                } else {
                    formWindow = (FormWindow) clazz.newInstance();
                }
                if (formWindow instanceof WindowLoader) {
                    return ((WindowLoader) formWindow).init(parameter);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static SocietyListWindow getSocietyListWindow(int current, WindowType windowType, Player player) {
        List<Society> societyList = SocietyUtils.getSocietyList(current);
        int limit = 10;
        int totalPage = SocietyUtils.getSocietyListTotalPage(current, limit);
        String content = "§a当前第 §b" + current + " §a总页数 §b" + totalPage;
        FormWindow formWindow = WindowManager.getFormWindow(windowType, player);
        SocietyListWindow societyListWindow = (SocietyListWindow) WindowManager.getFormWindow(WindowType.SOCIETY_LIST_WINDOW, content, current, totalPage, societyList, formWindow);
        return societyListWindow;
    }


    public static AddPublicFunds getAddPublicFunds() {
        return new AddPublicFunds();
    }

    public static MoneyRankWindow getMoneyRankWindow() {
        return new MoneyRankWindow();
    }

    public static HashMap<WindowType, Class> getRegisterWindow() {
        return registerWindow;
    }


}
