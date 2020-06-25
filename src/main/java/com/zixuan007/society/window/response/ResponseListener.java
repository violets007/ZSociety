package com.zixuan007.society.window.response;

import cn.nukkit.Player;

public interface ResponseListener {
  default void onClick(Player player) {}
  
  default void onClose(Player player) {}
  
  default void goBack(Player player) {}
}
