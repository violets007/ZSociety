package com.zixuan007.society.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.domain.Society;
import com.zixuan007.society.event.PlayerCreateSocietyEvent;
import com.zixuan007.society.event.PlayerApplyJoinSocietyEvent;
import com.zixuan007.society.event.PlayerQuitSocietyEvent;
import com.zixuan007.society.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 公会事件监听器
 */
public class SocietyListener implements Listener {
    private SocietyPlugin societyPlugin;

    public SocietyListener(SocietyPlugin societyPlugin){
        this.societyPlugin = societyPlugin;
    }

    /**
     * @deprecated 当玩家创建公会时调用用此方法
     * @param event 创建公会事件
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onCreate(PlayerCreateSocietyEvent event){
        Player player = event.getPlayer();//创建公会的玩家
        Society society = event.getSociety();//公会实例
        Config config = societyPlugin.getConfig();
        society.getPost().put(player.getName(),new ArrayList<Object>(){
            {
                ArrayList<Object> post = (ArrayList<Object>) config.get("post");
                HashMap<String,Object> map = (HashMap<String, Object>) post.get(0);
                add(map.get("name"));
                add(map.get("grade"));
            }
        });
        society.saveData();
        SocietyPlugin.getInstance().getSocieties().add(event.getSociety());
        SocietyPlugin.getInstance().getLogger().info("玩家: "+player.getName()+" 创建公会名称: "+society.getSocietyName());
        player.sendMessage(">> §a创建 §l§b"+society.getSocietyName()+" §a公会成功");
    }

    /**
     * @deprecated 当玩家申请加入指定公会时调用此事件
     * @param event
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerApplyJoinSocietyEvent event){
        Player player = event.getPlayer();
        Society society = event.getSociety();
        Config config = societyPlugin.getConfig();
        society.getTempApply().add(player.getName());
        society.saveData();
        player.sendMessage(">> §a成功申请加入 §l§b"+society.getSocietyName()+" §a公会,请耐心等待§c会长进行处理");
    }

    /**
     * 当玩家退出公会调用此事件
     * @param event
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuic(PlayerQuitSocietyEvent event){
        Player player = event.getPlayer();
        Society society = event.getSociety();
        society.getPost().remove(player.getName());
        society.saveData();
        player.sendMessage(">> §a成功退出 §l§c"+society.getSocietyName()+" §a公会");
    }


    @EventHandler(priority = EventPriority.MONITOR,ignoreCancelled = true)
    public void onChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();
        boolean isChat = (boolean) societyPlugin.getConfig().get("isChat");
        if(isChat){
            message = Utils.addPrefixText(player, message);
            event.setFormat(message);
        }
    }
}
