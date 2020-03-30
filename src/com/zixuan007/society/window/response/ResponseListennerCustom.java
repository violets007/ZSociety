package com.zixuan007.society.window.response;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseCustom;

public interface ResponseListennerCustom extends ResponseListener {
  default void onClick(FormResponseCustom response, Player player) {}
}