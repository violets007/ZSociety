package com.zixuan007.society.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.utils.Config;
import com.zixuan007.society.SocietyPlugin;
import com.zixuan007.society.utils.MarryUtils;
import com.zixuan007.society.window.ModalWindow;
import com.zixuan007.society.window.WindowManager;

/**
 * 插件事件监听器
 */
public class BaseListener implements Listener {
    private SocietyPlugin societyPlugin;

    public BaseListener(SocietyPlugin societyPlugin){
        this.societyPlugin=societyPlugin;
    }

    /**
     * 进入游戏进行性别选择
     * @param event
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if(SocietyPlugin.getInstance().getMarryConfig().get(player.getName()) == null){
            ModalWindow affrimWindow = WindowManager.getAffrimWindow("§e请选择当前的性别", "男", "女");
            Config marryConfig = SocietyPlugin.getInstance().getMarryConfig();
            affrimWindow.setButtonClickedListener((affrim,player1)->{
                String gender="";
                if(affrim){
                    marryConfig.set(player1.getName(),"男");
                    gender="§b男";
                }else{
                    marryConfig.set(player1.getName(),"女");
                    gender="§c女";
                }
                player.showFormWindow(WindowManager.getMessageWindow("§a成功选择为 "+gender,null,"关闭窗口"));
                marryConfig.save();
            });
            affrimWindow.setBack(true);
            affrimWindow.setParent(affrimWindow);
            player.showFormWindow(affrimWindow);
        }
        String name = MarryUtils.proposeFailName.get(player.getName());
        if(name != null){
            player.showFormWindow(WindowManager.getMessageWindow("§b"+name+"§c拒绝了你的求婚",null,"关闭窗口"));
            MarryUtils.proposeFailName.remove(player.getName());
        }
    }
}
