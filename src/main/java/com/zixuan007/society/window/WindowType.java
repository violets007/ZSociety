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

    /**
     * 结婚功能窗口
     */
    MARRY_WINDOW("MarryWindow"),
    /**
     * 添加夫妻之间公共资产
     */
    ADD_PUBLIC_FUNDS("AddPublicFunds"),

    /**
     * 夫妻公共资产排行榜
     */
    MONEY_RANK_WINDOW("MoneyRankWindow"),

    /**
     * 求婚窗口
     */
    PROPOSE_WINDOW("ProposeWindow"),

    /**
     * 结婚管理窗口
     */
    MARRY_ADMIN_WINDOW("MarryAdminWindow"),

    /**
     * 移除夫妻窗口
     */
    REMOVE_MARRY_WINDOW("RemoveMarryWindow"),

    /**
     * 设置公共资产窗口
     */
    SET_MARRY_MONEY_WINDOW("SetMarryMoneyWindow"),

    /**
     * 公会功能列表
     */
    SOCIETY_WINDOW("SocietyWindow"),

    /**
     * 创建公会窗口
     */
    CREATE_SOCIETY_WINDOW("CreateSocietyWindow"),

    /**
     * 成员列表窗口
     */
    Member_List_Window("MemberListWindow"),


    /**
     * 贡献排行榜
     */
    CONTRIBUTION_RANKING_WINDOW("ContributionRankingWindow"),

    /**
     * 等级排行榜
     */
    LEVEL_RANK_WINDOW("LevelRankWindow"),

    /**
     * 公会列表
     */
    SOCIETY_LIST_WINDOW("SocietyListWindow"),

    /**
     * 贡献窗口
     */
    CONTRIBUTION_WINDOW("ContributionWindow"),

    /**
     * 创建公会商店
     */
    CREATE_SOCIETY_SHOP_WINDOW("CreateSocietyShopWindow"),

    /**
     * 会长管理界面
     */
    PRESIDENT_WINDOW("PresidentWindow"),

    /**
     * 玩家申请列表窗口
     */
    PLAYER_APPLY_LIST_WINDOW("PlayerApplyListWindow"),

    /**
     * 移除成员窗口
     */
    REMOVE_MEMBER_WINDOW("RemoveMemberWindow"),

    /**
     * 设置职位窗口
     */
    SET_JOB_WINDOW("SetJobWindow"),

    /**
     * 设置贡献窗口
     */
    SET_CONTRIBUTE_WINDOW("SetContributeWindow"),

    /**
     * 设置公会等级窗口
     */
    SET_GRADE_WINDOW("SetGradeWindow"),

    /**
     * 消息窗口
     */
    MESSAGE_WINDOW("MessageWindow"),

    /**
     * 确认窗口
     */
    MODAL_WINDOW("ModalWindow"),
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
