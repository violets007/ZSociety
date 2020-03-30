package com.zixuan007.society.window.response;

import cn.nukkit.Player;

public interface ResponseListenerModal extends ResponseListener {
  default void onClick(boolean confirmation, Player player) {}
}