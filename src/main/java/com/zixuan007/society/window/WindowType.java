package com.zixuan007.society.window;


/**
 * 窗口类型
 * @author zixuan007
 */
public enum  WindowType {

    /**
     * VIP功能窗口
     */
    PRIVILEGE_WINDOW("PrivilegeWindow"),
    /**
     * SVIP功能窗口
     */
    ADVANCED_PRIVILEGE_WINDOW("AdvancedPrivilegeWindow"),
    /**
     * 特权信息窗口
     */
    PRIVILEGE_INFO_WINDOW("PrivilegeInfoWindow"),
    /**
     * 特权管理窗口
     */
    PRIVILEGE_MANAGER_WINDOW("PrivilegeManagerWindow"),
    /**
     * 玩家特权信息列表
     */
    PRIVILEGE_LIST_WINDOW("PrivilegeListWindow"),

    /**
     * 移除特权窗口
     */
    REMOVE_PRIVILEGE_WINDOW("RemovePrivilegeWindow"),

    /**
     * 设置特权窗口
     */
    SET_PRIVILEGE_WINDOW("SetPrivilegeWindow"),

    /**
     * 称号列表窗口
     */
    TITLE_WINDOW("TitleWindow"),

    /**
     * 设置称号窗口
     */
    SET_TITLE_WINDOW("SetTitleWindow"),

    /**
     * 移除称号窗口
     */
    REMOVE_TITLE_WINDOW("RemoveTitleWindow"),

    /**
     * 创建称号商店窗口
     */
    CREATE_TITLE_SHOP_WINDOW("CreateTitleShopWindow"),
    ;


    public String windowName;

    WindowType(String windowName){
        this.windowName=windowName;

    }

    @Override
    public String toString() {
        return "WindowType{" +
                "windowName='" + windowName + '\'' +
                '}';
    }


}
