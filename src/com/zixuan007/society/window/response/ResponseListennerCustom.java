package com.zixuan007.society.window.response;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseCustom;

public interface ResponseListennerCustom extends ResponseListener {
  default void onClick(FormResponseCustom response, Player player) {}
}


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\window\response\ResponseListennerCustom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */