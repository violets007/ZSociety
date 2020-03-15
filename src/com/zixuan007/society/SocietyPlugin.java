package com.zixuan007.society;

import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import com.zixuan007.society.command.MainCommand;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.listener.SocietyListener;
import com.zixuan007.society.listener.WindowListener;
import com.zixuan007.society.task.BottomTask;
import com.zixuan007.society.utils.Utils;

import java.io.File;
import java.util.ArrayList;

/**
 * 公会插件主类
 * @author zixuan007
 */
public class SocietyPlugin extends PluginBase {
    private Config config;//公会主配置文件
    private static SocietyPlugin instance;//插件实例对象
    private ArrayList<Society> societies=new ArrayList<Society>(); //公会列表


    /**
     * @Description 插件开启
     */
    @Override
    public void onEnable() {
        init();
        getLogger().info("§2公会插件开启 作者§f:§bzixuan007");
    }

    /**
     * @Description 插件关闭
     */
    @Override
    public void onDisable() {
        getLogger().info("§2公会插件关闭 §c数据保存中...");
        societies.forEach(society -> {
            society.saveData();
        });
    }

    /**
     * @deprecated 插件初始化
     */
    public void init() {
        checkPlugin("EconomyAPI");
        if (instance == null) instance = this;
        saveResource("Config.yml"); //保存配置文件资源
        getServer().getCommandMap().register("society", new MainCommand(), "公会"); //把指定命令加载进命令存储区
        String configPath = getServer().getPluginPath() + getName() + Utils.FILE_SEPARATOR + "Config.yml"; //配置文件路径
        config = new Config(configPath);//把指定配置文件转换为对象
        loadSocietyConfig();//加载配置文件
        //if(((boolean)config.get("isTip")) == true)
        if (config.getBoolean("isTip",false)) {
            getServer().getScheduler().scheduleRepeatingTask(new BottomTask(this), 10);
        }
        getServer().getPluginManager().registerEvents(new SocietyListener(this),this);
        getServer().getPluginManager().registerEvents(new WindowListener(this),this);
    }

    /**
     * @deprecated 加载公会配置文件夹
     */
    public void loadSocietyConfig() {
        File societyFolder = new File(Utils.SOCIETYFOLDER);
        if (!societyFolder.exists()) societyFolder.mkdirs();
        File[] files = societyFolder.listFiles();
        for (File file : files) {
            if(file.getName().endsWith(".yml")) societies.add(Society.init(new Config(file)));
        }
    }

    /**
     * @deprecated 检测依赖插件
     */
    public void checkPlugin(String pluginName) {
        Plugin economyAPI = getServer().getPluginManager().getPlugin(pluginName);
        if (economyAPI == null) {
            getLogger().error("检测到 " + pluginName + " 插件不存在,请先安装");
            getServer().shutdown();
        }
    }

    @Override
    public Config getConfig() {
        return config;
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
        return societies;
    }

    public void setSocieties(ArrayList<Society> societies) {
        this.societies = societies;
    }
}
