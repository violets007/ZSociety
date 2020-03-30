package com.zixuan007.society.window.response;

import cn.nukkit.Player;

public interface ResponseListener {
  default void onClick(Player player) {}
  
  default void onClose(Player player) {}
  
  default void goBack(Player player) {}
}


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\window\response\ResponseListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */