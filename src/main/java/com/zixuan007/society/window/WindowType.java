package com.zixuan007.society.window;


/**
 * 窗口类型
 * @author zixuan007
 */
public enum  WindowType {

    VIPWINDOW("VipWIndow"),
    SVIPWINDOW("SvipWindow"),
    PRIVILEGEWINDOW("PrivilegeWindow");

    public String windowName;

    private WindowType(String windowName){
        this.windowName=windowName;

    }

    @Override
    public String toString() {
        return "WindowType{" +
                "windowName='" + windowName + '\'' +
                '}';
    }


}
