package com.zixuan007.society.window.response;

import cn.nukkit.Player;

public interface ResponseListenerModal extends ResponseListener {
  default void onClick(boolean confirmation, Player player) {}
}


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\window\response\ResponseListenerModal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */