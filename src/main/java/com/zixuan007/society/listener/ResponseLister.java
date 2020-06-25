package com.zixuan007.society.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerSettingsRespondedEvent;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.window.FormWindow;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.ModalWindow;
import com.zixuan007.society.window.SimpleWindow;

/**
 * 窗口事件响应
 */
public class ResponseLister implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onResponded(PlayerFormRespondedEvent event) {
        this.handleResponse(event.getWindow(), event.getResponse(), event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onSettingsResponded(PlayerSettingsRespondedEvent event) {
        this.handleResponse(event.getWindow(), event.getResponse(), event.getPlayer());
    }

    public void handleResponse(FormWindow formWindow, FormResponse response, Player player) {
        if (ModalWindow.onEvent(formWindow, response, player))
            return;
        if (CustomWindow.onEvent(formWindow, response, player))
            return;
        if (SimpleWindow.onEvent(formWindow, response, player))
            return;
    }

}