package com.zixuan007.society;


import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import com.zixuan007.society.command.*;
import com.zixuan007.society.listener.*;
import com.zixuan007.society.task.ShowTask;
import com.zixuan007.society.task.CheckPrivilegeTimeTask;
import com.zixuan007.society.utils.*;
import com.zixuan007.society.window.ModalWindow;
import com.zixuan007.society.window.WindowType;
import com.zixuan007.society.window.marry.AddPublicFunds;
import com.zixuan007.society.window.marry.MarryWindow;
import com.zixuan007.society.window.marry.MoneyRankWindow;
import com.zixuan007.society.window.marry.ProposeWindow;
import com.zixuan007.society.window.marry.admin.MarryAdminWindow;
import com.zixuan007.society.window.marry.admin.RemoveMarryWindow;
import com.zixuan007.society.window.marry.admin.SetMarryMoneyWindow;
import com.zixuan007.society.window.society.*;
import com.zixuan007.society.window.society.admin.*;
import com.zixuan007.society.window.society.president.*;
import com.zixuan007.society.window.society.shop.CreateSocietyShopWindow;
import com.zixuan007.society.window.title.TitleWindow;
import com.zixuan007.society.window.title.admin.CreateTitleShopWindow;
import com.zixuan007.society.window.title.admin.RemoveTitleWindow;
import com.zixuan007.society.window.title.admin.SetTitleWindow;
import com.zixuan007.society.window.title.admin.TitleManagerWindow;
import com.zixuan007.society.window.privilege.PrivilegeInfoWindow;
import com.zixuan007.society.window.privilege.AdvancedPrivilegeWindow;
import com.zixuan007.society.window.privilege.PrivilegeWindow;
import com.zixuan007.society.window.privilege.admin.PrivilegeListWindow;
import com.zixuan007.society.window.privilege.admin.PrivilegeManagerWindow;
import com.zixuan007.society.window.privilege.admin.RemovePrivilegeWindow;
import com.zixuan007.society.window.privilege.admin.SetPrivilegeWindow;


import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * @author zixuan007
 */
public class SocietyPlugin extends PluginBase {

    private Config config;
    private final List<Config> societyConfigList = new ArrayList<>();
    private Config titleConfig;
    private Config languageConfig;
    private Config windowConfig;
    private Config marryConfig;
    private Config titleShopConfig;
    private Config societyShopConfig;
//    private Config societyWarConfig;
    private static SocietyPlugin instance;


    @Override
    public void onEnable() {
        this.init();
        this.getLogger().info("§2公会插件开启 §c作者§f:§bzixuan007");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("§2公会插件关闭 §c数据保存中...");
        SocietyUtils.societies.forEach(SocietyUtils::saveSociety);
        config.save();
        marryConfig.save();
        titleShopConfig.save();
        societyShopConfig.save();
    }

    /**
     * 初始化插件数据
     */
    public void init() {
        new MetricsLite(this);

        checkPlugin("EconomyAPI");

        if (instance == null) {
            instance = this;
        }

        checkConfig();

        loadConfig();
        registerCommand();
        registerWindow();

        getServer().getScheduler().scheduleRepeatingTask(new ShowTask(this), 10);
        getServer().getScheduler().scheduleRepeatingTask(new CheckPrivilegeTimeTask(this), 20 * 60);

        resisterListener();
    }

    /**
     * 注册事件监听器
     */
    public void resisterListener() {
        getServer().getPluginManager().registerEvents(new ResponseLister(), this);
        getServer().getPluginManager().registerEvents(new SocietyListener(this), this);
        getServer().getPluginManager().registerEvents(new TitleListener(this), this);
        getServer().getPluginManager().registerEvents(new MarryListener(this), this);
        getServer().getPluginManager().registerEvents(new PrivilegeListener(this), this);
    }

    /**
     * 注册插件命令
     */
    public void registerCommand() {
        getServer().getCommandMap().register("society", new SocietyCommand());
        getServer().getCommandMap().register("title", new TitleCommand());
        getServer().getCommandMap().register("marry", new MarryCommand());
        getServer().getCommandMap().register("privilege", new VipCommand());
        getServer().getCommandMap().register("admin", new AdminCommand());
    }


    /**
     * 加载配置文件
     */
    public void loadConfig() {
        String titleConfigPath = PluginUtils.CONFIG_FOLDER + "Title.yml";
        String language = (String) config.get("language");
        String languagePath = PluginUtils.CONFIG_FOLDER + "lang" + PluginUtils.FILE_SEPARATOR + language;
        String titleShopPath = PluginUtils.CONFIG_FOLDER + "TitleShop.yml";
        String marryPath = PluginUtils.CONFIG_FOLDER + "Marry.yml";
        String societyShopConfigPath = PluginUtils.CONFIG_FOLDER + "SocietyShop.yml";
        String windowConfig = PluginUtils.CONFIG_FOLDER + "WindowConfig.yml";
        String societyWarConfig = PluginUtils.CONFIG_FOLDER + "SocietyWarConfig.yml";

        this.titleConfig = new Config(titleConfigPath);
        this.titleShopConfig = new Config(titleShopPath);
        this.marryConfig = new Config(marryPath);
        this.societyShopConfig = new Config(societyShopConfigPath);
        this.languageConfig = new Config(languagePath);
        this.windowConfig = new Config(windowConfig);
//        this.societyWarConfig=new Config(societyWarConfig);

        //需要工具类初始化配置文件
        MarryUtils.loadMarryConfig();
        SocietyUtils.loadSocietyConfig();
        PrivilegeUtils.loadVipConfig();
        TitleUtils.loadConfig();
    }

    /**
     * 检测插件
     *
     * @param pluginName 插件名称
     */
    public void checkPlugin(String pluginName) {
        Plugin plugin = getServer().getPluginManager().getPlugin(pluginName);
        if (plugin == null) {
            getLogger().error("§c检测到 §b" + pluginName + " §c插件不存在,请先安装");
            getServer().shutdown();
        }
    }

    /**
     * 检测主配置文件的版本是否相同
     */
    public void checkConfig() {
        String configPath = PluginUtils.CONFIG_FOLDER + "Config.yml";
        String windowConfigPath = PluginUtils.CONFIG_FOLDER + "WindowConfig.yml";
        String languagePath = getDataFolder() + File.separator + "lang" + File.separator + "zh-CN.yml";

        config = new Config(PluginUtils.checkConfig("Config.yml", configPath), Config.YAML);
        languageConfig = new Config(PluginUtils.checkConfig("lang/zh-CN.yml", languagePath), Config.YAML);
        languageConfig = new Config(PluginUtils.checkConfig("WindowConfig.yml", windowConfigPath), Config.YAML);

    }

    /**
     * 注册指定的窗口
     */
    public void registerWindow() {
        PluginUtils.addWindowClass(WindowType.PRIVILEGE_WINDOW, PrivilegeWindow.class);
        PluginUtils.addWindowClass(WindowType.ADVANCED_PRIVILEGE_WINDOW, AdvancedPrivilegeWindow.class);
        PluginUtils.addWindowClass(WindowType.PRIVILEGE_WINDOW, PrivilegeInfoWindow.class);
        PluginUtils.addWindowClass(WindowType.PRIVILEGE_INFO_WINDOW, PrivilegeInfoWindow.class);
        PluginUtils.addWindowClass(WindowType.PRIVILEGE_MANAGER_WINDOW, PrivilegeManagerWindow.class);
        PluginUtils.addWindowClass(WindowType.SOCIETY_ADMIN_WINDOW, SocietyAdminWindow.class);
        PluginUtils.addWindowClass(WindowType.PRIVILEGE_LIST_WINDOW, PrivilegeListWindow.class);
        PluginUtils.addWindowClass(WindowType.REMOVE_PRIVILEGE_WINDOW, RemovePrivilegeWindow.class);
        PluginUtils.addWindowClass(WindowType.SET_PRIVILEGE_WINDOW, SetPrivilegeWindow.class);
        PluginUtils.addWindowClass(WindowType.TITLE_MANAGER_WINDOW, TitleManagerWindow.class);
        PluginUtils.addWindowClass(WindowType.TITLE_WINDOW, TitleWindow.class);
        PluginUtils.addWindowClass(WindowType.SET_TITLE_WINDOW, SetTitleWindow.class);
        PluginUtils.addWindowClass(WindowType.REMOVE_TITLE_WINDOW, RemoveTitleWindow.class);
        PluginUtils.addWindowClass(WindowType.CREATE_TITLE_SHOP_WINDOW, CreateTitleShopWindow.class);
        PluginUtils.addWindowClass(WindowType.MARRY_WINDOW, MarryWindow.class);
        PluginUtils.addWindowClass(WindowType.ADD_PUBLIC_FUNDS, AddPublicFunds.class);
        PluginUtils.addWindowClass(WindowType.MONEY_RANK_WINDOW, MoneyRankWindow.class);
        PluginUtils.addWindowClass(WindowType.PROPOSE_WINDOW, ProposeWindow.class);
        PluginUtils.addWindowClass(WindowType.MARRY_ADMIN_WINDOW, MarryAdminWindow.class);
        PluginUtils.addWindowClass(WindowType.REMOVE_MARRY_WINDOW, RemoveMarryWindow.class);
        PluginUtils.addWindowClass(WindowType.Set_Society_War_Data_Window, SetSocietyWarDataWindow.class);
        PluginUtils.addWindowClass(WindowType.SEND_SOCIETY_WAR_WINDOW, SendSocietyWarWindow.class);
        PluginUtils.addWindowClass(WindowType.SET_MARRY_MONEY_WINDOW, SetMarryMoneyWindow.class);
        PluginUtils.addWindowClass(WindowType.SOCIETY_WINDOW, SocietyWindow.class);
        PluginUtils.addWindowClass(WindowType.SOCIETY_INFO_WINDOW,SocietyInfoWindow.class);
        PluginUtils.addWindowClass(WindowType.CREATE_SOCIETY_WINDOW, CreateSocietyWindow.class);
        PluginUtils.addWindowClass(WindowType.Member_List_Window, MemberListWindow.class);
        PluginUtils.addWindowClass(WindowType.CONTRIBUTION_RANKING_WINDOW, ContributionRankingWindow.class);
        PluginUtils.addWindowClass(WindowType.LEVEL_RANK_WINDOW, LevelRankWindow.class);
        PluginUtils.addWindowClass(WindowType.CONTRIBUTION_WINDOW, ContributionWindow.class);
        PluginUtils.addWindowClass(WindowType.MODIFY_SOCIETY_INFO_WINDOW, ModifySocietyInfoWindow.class);
        PluginUtils.addWindowClass(WindowType.SOCIETY_LIST_WINDOW, SocietyListWindow.class);
        PluginUtils.addWindowClass(WindowType.CREATE_SOCIETY_SHOP_WINDOW, CreateSocietyShopWindow.class);
        PluginUtils.addWindowClass(WindowType.PRESIDENT_WINDOW, PresidentWindow.class);
        PluginUtils.addWindowClass(WindowType.PLAYER_APPLY_LIST_WINDOW, PlayerApplyListWindow.class);
        PluginUtils.addWindowClass(WindowType.REMOVE_MEMBER_WINDOW, RemoveMemberWindow.class);
        PluginUtils.addWindowClass(WindowType.SET_CONTRIBUTE_WINDOW, SetContributeWindow.class);
        PluginUtils.addWindowClass(WindowType.SET_JOB_WINDOW, SetJobWindow.class);
        PluginUtils.addWindowClass(WindowType.SET_GRADE_WINDOW, SetGradeWindow.class);
        PluginUtils.addWindowClass(WindowType.DISSOLVE_WINDOW, DissolveWindow.class);
        PluginUtils.addWindowClass(WindowType.MESSAGE_WINDOW, MessageWindow.class);
        PluginUtils.addWindowClass(WindowType.MODAL_WINDOW, ModalWindow.class);
    }


    @Override
    public Config getConfig() {
        return this.config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public static SocietyPlugin getInstance() {
        return instance;
    }

    public Config getTitleConfig() {
        return this.titleConfig;
    }

    public void setTitleConfig(Config titleConfig) {
        this.titleConfig = titleConfig;
    }

    public List<Config> getSocietyConfigList() {
        return this.societyConfigList;
    }

    public Config getTitleShopConfig() {
        return this.titleShopConfig;
    }

    public Config getMarryConfig() {
        return marryConfig;
    }

    public Config getSocietyShopConfig() {
        return societyShopConfig;
    }

    public Config getLanguageConfig() {
        return languageConfig;
    }

    public Config getWindowConfig() {
        return windowConfig;
    }

    /*public Config getSocietyWarConfig() {
        return societyWarConfig;
    }

    public void setSocietyWarConfig(Config societyWarConfig) {
        this.societyWarConfig = societyWarConfig;
    }*/
}