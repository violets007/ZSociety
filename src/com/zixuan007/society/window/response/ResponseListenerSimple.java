package com.zixuan007.society.window.response;

import cn.nukkit.Player;

public interface ResponseListenerSimple extends ResponseListener {
  default void onClick(int id, Player player) {}
}