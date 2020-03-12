package com.zixuan007.society.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.plugin.PluginManager;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.event.PlayerCreateSocietyEvent;
import com.zixuan007.society.event.PlayerApplyJoinSocietyEvent;
import com.zixuan007.society.event.PlayerQuitSocietyEvent;
import com.zixuan007.society.utils.Utils;
import com.zixuan007.society.window.ManageWindow;
import me.onebone.economyapi.EconomyAPI;

import java.util.*;

/**
 * 公会插件主命令
 */
public class MainCommand extends Command {
    private SocietyPlugin societyPlugin = SocietyPlugin.getInstance();

    public MainCommand() {
        super("公会", "公会插件总命令","/§b公会 §e帮助");

        setPermission(""); //设置权限级别为玩家
        setParameter();
    }

    /**
     * 当玩家输入（/公会）执行下面方法
     *
     * @param sender      (Player)父类 玩家
     * @param commandName 命令名称 不包含(/)
     * @param args        参数
     * @return 执行结果 true 执行成功 false 则会调用显示usageMessage
     */
    @Override
    public boolean execute(CommandSender sender, String commandName, String[] args) {
        Player player = (Player) sender; //向下强转
        PluginManager pluginManager = this.societyPlugin.getServer().getPluginManager();
        if (commandName.equals("公会")) {
            if (args.length < 1){
                player.sendMessage("/§b公会 §e帮助");
                return false;//显示usageMessage
            }
            switch (args[0]) { //第一个参数
                case "帮助":
                    player.sendMessage("/§b公会 §e创建 §6公会名称§f(§c可带颜色§f) §f>>§a创建公会");
                    player.sendMessage("/§b公会 §e公会列表 §f>>§a查看公会列表");
                    player.sendMessage("/§b公会 §e成员列表 §f>>§a查看成员列表");
                    player.sendMessage("/§b公会 §e经济排名 §f>>§a显示公会经济排名");
                    player.sendMessage("/§b公会 §e等级排名 §f>>§a显示公会等级排名");
                    player.sendMessage("/§b公会 §e贡献 §6数量§f(§c只能贡献整数§f) §f>>§a贡献金额");
                    player.sendMessage("/§b公会 §e加入 §6公会ID§f(§c建议先查看列表§f) §f>>§a申请加入指定公会");
                    player.sendMessage("/§b公会 §e退出 §6公会ID §f>>§a退出指定公会");
                    player.sendMessage("/§b公会 §e管理 §f>>§a显示公会UI界面§f(§c只能会长管理§f)");
                    return true;
                case "创建":
                    if(args.length < 2){ //第二个参数为空
                        player.sendMessage("/§b公会 §e创建 §6公会名称(可带颜色) §f>>§a创建公会");
                        return true;
                    }
                    if(Utils.isSocietyNameExist(args[1])){ //公会名字已经存在
                        player.sendMessage("§f>> §c此公会名称已经存在");
                        return true;
                    }
                    if(Utils.isJoinSociety(player.getName())){
                        player.sendMessage(">> §c你已经存在公会,无法创建请先退出当前公会");
                        return true;
                    }
                    Double createSocietyMoney = (Double) societyPlugin.getConfig().get("createSocietyMoney");
                    double myMoney = EconomyAPI.getInstance().myMoney(player);
                    if(myMoney < createSocietyMoney){
                        player.sendMessage("§f>> §c当前余额不足,创建公会需要: §l§a"+createSocietyMoney);
                        return true;
                    }
                    EconomyAPI.getInstance().reduceMoney(player,createSocietyMoney);
                    long count = Utils.getNextSid(); //获取下一级公会ID防止冲突``
                    String societyName=args[1];
                    Society society = new Society(count,societyName,player.getName(),Utils.getFormatDateTime(),0D,new HashMap<String,ArrayList<Object>>());
                    pluginManager.callEvent(new PlayerCreateSocietyEvent(player, society));
                    return true;
                case "加入":
                    if(args.length < 2){ //第二个参数为空
                        player.sendMessage("/§b公会 §e加入 §6公会ID§f(§c建议先查看列表§f) §f>>§a申请加入指定公会");
                        return true;
                    }
                    if(Utils.isJoinSociety(player.getName())){
                        player.sendMessage(">> §c你已经存在公会,请勿二次加入");
                        return true;
                    }
                    for (Society society1 : societyPlugin.getSocieties()) {
                        if (args[1].equals(society1.getSid()+"")) {
                            pluginManager.callEvent(new PlayerApplyJoinSocietyEvent(player,society1));
                        }
                    }
                    return true;
                case "退出":
                    if(args.length < 2){ //第二个参数为空
                        player.sendMessage("/§b公会 §e退出 §6公会名称§f(§c建议复制§f) §f>>§a申请加入指定公会");
                        return true;
                    }
                    if(!Utils.isJoinSociety(player.getName())){
                        player.sendMessage(">> §c您当前还没创建公会,请先创建公会");
                        return true;
                    }
                    society = Utils.getSocietyByName(player.getName());
                    if(society.getPresidentName().equals(player.getName())){
                        player.sendMessage(">> §c您是会长无法退出公会,请解散");
                        return true;
                    }
                    pluginManager.callEvent(new PlayerQuitSocietyEvent(player,society));
                    return true;
                case "成员列表":
                    if(args.length < 2){ //第二个参数为空
                        player.sendMessage("/§b公会 §e成员列表 §6页数 §f>>§a查看公会列表人员");
                        return true;
                    }
                    if(!Utils.isJoinSociety(player.getName())){
                        player.sendMessage(">> §c您当前还没有加入公会,请先申请加入");
                        return true;
                    }
                    if(!Utils.isNumeric(args[1])){
                        player.sendMessage(">> §c输入的页数不是数字请重新输入");
                        return true;
                    }
                    society = Utils.getSocietyByName(player.getName());
                    List<String> memberList = Utils.getMemberList(society, Integer.parseInt(args[1]));
                    player.sendMessage("§9=======§l§f"+society.getSocietyName()+"§e成员列表§9=======");
                    player.sendMessage("§f当前页数 >>§l§d"+args[1]+" §f总页数>> §l§d"+(Utils.getTotalMemberPage(society)));
                    for (String name : memberList) {
                        String postByName = Utils.getPostByName(name, society);
                        if(name.equals(society.getPresidentName())){
                            player.sendMessage("职位>> §c"+postByName+" §f名称>> §b§l"+name);
                            continue;
                        }
                        player.sendMessage("职位>> §a"+postByName+" §f名称>> §7"+name);
                    }
                    return true;
                case "公会列表":
                    if(args.length < 2){ //第二个参数为空
                        player.sendMessage("/§b公会 §e公会列表 §6页数 §f>>§a查看公会列表");
                        return true;
                    }
                    List<Society> societyList = Utils.getSocietyList(Integer.parseInt(args[1]));
                    if(societyList == null){
                        player.sendMessage(">> §c当前还没有玩家创建公会或者没当前页数!");
                        return true;
                    }
                    player.sendMessage("§9=======§l§d公会列表§9=======");
                    player.sendMessage("§f当前页数 >>§l§d"+args[1]+" §f总页数>> §l§d"+(Utils.getTotalSocietiesPage()));
                    for (Society society1 : societyList) {
                        player.sendMessage("公会ID>> §c§l"+society1.getSid()+" §f名称>> §b§l"+society1.getSocietyName()+" §f会长 >>§a"+society1.getPresidentName());
                    }
                    return true;
                case "贡献":
                    if(args.length < 2){ //第二个参数为空
                        player.sendMessage("/§b公会 §e贡献 §6金额数量 §f>>§a给公会贡献");
                        return true;
                    }
                    if(!Utils.isJoinSociety(player.getName())){
                        player.sendMessage(">> §c您当前还没有加入公会,请先申请加入");
                        return true;
                    }
                    if(!Utils.isNumeric(args[1])){
                        player.sendMessage(">> §c输入的金额不是数字");
                        return true;
                    }
                    myMoney = EconomyAPI.getInstance().myMoney(player);
                    double contributeMoney = Double.parseDouble(args[1]);
                    if(myMoney < contributeMoney){
                        player.sendMessage(">> §c当前的金额不足,请及时联系管理员充值");
                        return true;
                    }
                    EconomyAPI.getInstance().reduceMoney(player, contributeMoney, true);
                    society = Utils.getSocietyByName(player.getName());
                    society.setSocietyMoney(society.getSocietyMoney()+contributeMoney);
                    society.saveData();//保存数据
                    player.sendMessage(">> §a 贡献成功,当前公会总金额为: §l§b"+society.getSocietyMoney());
                    return true;
                case "管理":
                    if(!Utils.isJoinSociety(player.getName())){
                        player.sendMessage(">> §c您当前还没有加入公会,请先申请加入");
                        return true;
                    }
                    society = Utils.getSocietyByName(player.getName());
                    if(!society.getPresidentName().equals(player.getName())){
                        player.sendMessage(">> §c您不是会长没有权限管理公会");
                        return true;
                    }
                    //显示公会管理界面
                    player.showFormWindow(new ManageWindow(society.getSocietyName()+"§e管理界面","",society.getSid()));
                    return true;
                case "经济排名":
                    Collections.sort(societyPlugin.getSocieties(), new Comparator<Society>() {
                        @Override
                        public int compare(Society o1, Society o2) {
                            return (o1.getSocietyMoney()<o2.getSocietyMoney())?1:(o1.getSocietyMoney()>o2.getSocietyMoney())?-1:0;
                        }
                    });
                    player.sendMessage("§9=======§l§d公会经济排名§f(§c前五§f)§9=======");
                    for (int i = 0; i < societyPlugin.getSocieties().size() && i<=4; i++) {
                        society = societyPlugin.getSocieties().get(i);
                        long sid=society.getSid();
                        String societyName1 = society.getSocietyName();
                        Double societyMoney = society.getSocietyMoney();
                        player.sendMessage("§f§l公会ID>> §c"+sid+" §f公会名称>> §a"+societyName1+" §f公会经济>> §b"+societyMoney);
                    }
                    break;
                case "等级排名":
                    Collections.sort(societyPlugin.getSocieties(), new Comparator<Society>() {
                        @Override
                        public int compare(Society o1, Society o2) {
                            return (o1.getGrade()<o2.getGrade())?1:(o1.getGrade()>o2.getGrade())?-1:0;
                        }
                    });
                    player.sendMessage("§9=======§l§d公会等级排名§f(§c前五§f)§9=======");
                    for (int i = 0; i < societyPlugin.getSocieties().size() && i<=4; i++) {
                        society = societyPlugin.getSocieties().get(i);
                        long sid=society.getSid();
                        String societyName1 = society.getSocietyName();
                        int grade = society.getGrade();
                        player.sendMessage("§f§l公会ID>> §c"+sid+" §f公会名称>> §a"+societyName1+" §f公会等级>> §b"+grade);
                    }
                    break;
                default:
                    player.sendMessage("/§b公会 §e帮助");
                    return true;
            }
        }
        return false;
    }

    /**
     * @deprecated 设置/公会命令的参数
     */
    public void setParameter(){
        getCommandParameters().clear();//清除所有公会参数
        getCommandParameters().put("创建", new CommandParameter[]{
                new CommandParameter("§e创建§f", CommandParamType.STRING,true),
                new CommandParameter("§6公会名称§f(§c可带颜色§f)", CommandParamType.STRING,true)
        });
        getCommandParameters().put("公会列表", new CommandParameter[]{
                new CommandParameter("§e公会列表§f", CommandParamType.STRING,true),
                new CommandParameter("§6页数§f", CommandParamType.WILDCARD_INT,true)
        });
        getCommandParameters().put("成员列表", new CommandParameter[]{
                new CommandParameter("§e成员列表§f", CommandParamType.STRING,true),
                new CommandParameter("§6页数§f", CommandParamType.WILDCARD_INT,true)
        });
        getCommandParameters().put("贡献", new CommandParameter[]{
                new CommandParameter("§e贡献§f", CommandParamType.STRING,true),
                new CommandParameter("§6金额§f", CommandParamType.WILDCARD_INT,true)
        });
        getCommandParameters().put("加入", new CommandParameter[]{
                new CommandParameter("§e加入§f", CommandParamType.STRING,true),
                new CommandParameter("§6公会ID§f", CommandParamType.WILDCARD_INT,true)
        });
        getCommandParameters().put("退出", new CommandParameter[]{
                new CommandParameter("§e退出§f", CommandParamType.STRING,true),
                new CommandParameter("§6公会ID§f", CommandParamType.WILDCARD_INT,true)
        });
        getCommandParameters().put("管理", new CommandParameter[]{
                new CommandParameter("§e管理§f", CommandParamType.STRING,true),
        });
        getCommandParameters().put("帮助", new CommandParameter[]{
                new CommandParameter("§e帮助§f", CommandParamType.STRING,true),
        });
        getCommandParameters().put("经济排名", new CommandParameter[]{
                new CommandParameter("§e经济排名§f", CommandParamType.STRING,true),
        });
        getCommandParameters().put("等级排名", new CommandParameter[]{
                new CommandParameter("§e等级排名§f", CommandParamType.STRING,true),
        });
        setCommandParameters(getCommandParameters()); //设置参数
    }
}
