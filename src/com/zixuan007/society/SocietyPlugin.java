package com.zixuan007.society;

import cn.nukkit.command.Command;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.Config;
import com.zixuan007.society.command.MainCommand;
import com.zixuan007.society.command.TitleCommand;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.listener.ResponseLister;
import com.zixuan007.society.listener.SocietyListener;
import com.zixuan007.society.listener.TitleListener;
import com.zixuan007.society.task.BottomTask;
import com.zixuan007.society.utils.SocietyUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class SocietyPlugin extends PluginBase {

    private Config config;
    private List<Config> societyConfigList = new ArrayList<>();
    private Config titleConfig;
    private Config LangConfig;
    private Config titleShopConfig;
    private static SocietyPlugin instance;
    private ArrayList<Society> societies = new ArrayList<>();

    public void onEnable() {
        this.init();
        this.getLogger().info("§2公会插件开启 §c作者§f:§bzixuan007");
    }

    public void onDisable() {
        this.getLogger().info("§2公会插件关闭 §c数据保存中...");
        this.societies.forEach((society) -> { society.saveData(); });
    }

    public void init() {
        checkPlugin("EconomyAPI");
        checkPlugin("Tips");
        checkPlugin("FloatingText");
        if (instance == null) instance = this;
        checkConfig();
        saveResource("cn_ language.yml", true);
        registerCommand();
        loadConfig();
        loadSocietyConfig();
        if (this.config.getBoolean("是否开启底部", false)) {
            getServer().getScheduler().scheduleRepeatingTask(new BottomTask(this), 10);
        }
        getServer().getPluginManager().registerEvents(new ResponseLister(), this);
        getServer().getPluginManager().registerEvents(new SocietyListener(this), this);
        getServer().getPluginManager().registerEvents(new TitleListener(this), this);
    }

    public void registerCommand() {
        getServer().getCommandMap().register("society", (Command) new MainCommand(), "公会");
        getServer().getCommandMap().register("title", (Command) new TitleCommand(), "称号");
    }

    public void loadSocietyConfig() {
        File societyFolder = new File(SocietyUtils.SOCIETYFOLDER);
        if (!societyFolder.exists()) societyFolder.mkdirs();
        File[] files = societyFolder.listFiles();
        for (File file : files) {
            Config config = new Config(file);
            this.societyConfigList.add(config);
            if (file.getName().endsWith(".yml")) this.societies.add(Society.init(config));
        }
    }

    public void loadConfig() {
        String titleConfigPath = SocietyUtils.CONFIGFOLDER + "Title.yml";
        String langPath = SocietyUtils.CONFIGFOLDER + "cn_ language.yml";
        String titleShopPath = SocietyUtils.CONFIGFOLDER + "TitleShopData.yml";
        this.titleConfig = new Config(titleConfigPath);
        this.LangConfig = new Config(langPath);
        this.titleShopConfig = new Config(titleShopPath);
    }

    public void checkPlugin(String pluginName) {
        Plugin plugin = getServer().getPluginManager().getPlugin(pluginName);
        if (plugin == null) {
            getLogger().error("§c检测到 §b" + pluginName + " §c插件不存在,请先安装");
            getServer().shutdown();
        }
    }

    public void checkConfig() {
        String path = SocietyUtils.CONFIGFOLDER + "Config.yml";
        File file = new File(path);
        if (!file.exists()) {
            saveResource("Config.yml");
        } else {
            Config config = new Config(file, 2);
            String version = (String) config.get("version");
            String pluginVersion = getDescription().getVersion();
            if (version == null || !version.equals(pluginVersion)) {
                saveResource("Config.yml", true);
                getLogger().info("§c检测到配置文件版本太低,自动进行覆盖");
            } else {
                saveResource("Config.yml");
            }
        }
        String configPath = SocietyUtils.CONFIGFOLDER + "Config.yml";
        this.config = new Config(configPath);
    }

    public Config getConfig() {
        return this.config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public static SocietyPlugin getInstance() {
        return instance;
    }

    public static void setInstance(SocietyPlugin instance) {
        SocietyPlugin.instance = instance;
    }

    public ArrayList<Society> getSocieties() {
        return this.societies;
    }

    public void setSocieties(ArrayList<Society> societies) {
        this.societies = societies;
    }

    public Config getTitleConfig() {
        return this.titleConfig;
    }

    public void setTitleConfig(Config titleConfig) {
        this.titleConfig = titleConfig;
    }

    public Config getLangConfig() {
        return this.LangConfig;
    }

    public void setLangConfig(Config langConfig) {
        this.LangConfig = langConfig;
    }

    public List<Config> getSocietyConfigList() {
        return this.societyConfigList;
    }

    public void setSocietyConfigList(List<Config> societyConfigList) {
        this.societyConfigList = societyConfigList;
    }

    public Config getTitleShopConfig() {
        return this.titleShopConfig;
    }

    public void setTitleShopConfig(Config titleShopConfig) {
        this.titleShopConfig = titleShopConfig;
    }

}