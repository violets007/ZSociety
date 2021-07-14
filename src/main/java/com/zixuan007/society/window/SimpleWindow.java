package com.zixuan007.society.window;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowSimple;
import com.zixuan007.society.window.response.ResponseListenerSimple;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;



/**
 * @author zixuan007
 */
public class SimpleWindow extends FormWindowSimple implements ResponseListenerSimple {
    protected transient BiConsumer<Integer, Player> buttonClickedListener = null;
    protected transient Consumer<Player> windowClosedListener = null;
    private transient FormWindow parent;
    private Boolean isBack = Boolean.valueOf(false);

    public SimpleWindow(String title, String content) {
        super(title, content);
    }

    /**
     * 响应表单事件调用
     * @param formWindow 当前类型窗口
     * @param response 响应数据
     * @param player 玩家
     * @return
     */
    public static boolean onEvent(FormWindow formWindow, FormResponse response, Player player) {
        if (formWindow instanceof SimpleWindow) {
            SimpleWindow window = (SimpleWindow)formWindow;

            if (window.wasClosed() || response == null) {
                if (window.isBack) {
                    window.callBack(player);
                } else {
                    window.callClosed(player);
                }
                window.closed = false;
            } else {
                window.callClicked(((FormResponseSimple)response).getClickedButtonId(), player);
            }

            return true;
        }
        return false;
    }


    private void callClicked(int clickedButtonId, Player player) {
        onClick(clickedButtonId, player);
        if (this.buttonClickedListener != null) {
            this.buttonClickedListener.accept(clickedButtonId, player);
        }
    }

    private void callClosed(Player player) {
        onClose(player);
        if (this.buttonClickedListener != null) {
            this.windowClosedListener.accept(player);
        }
    }

    public void callBack(Player player) {
        player.showFormWindow(this.parent);
    }


    public final void onClicked(BiConsumer<Integer, Player> listener) {
        Objects.requireNonNull(listener);
        if (this.buttonClickedListener != null) {
            this.buttonClickedListener = listener;
        }
    }

    public final void onClosed(Consumer<Player> listener) {
        Objects.requireNonNull(listener);
        if (this.windowClosedListener != null) {
            this.windowClosedListener = listener;
        }
    }

    public BiConsumer<Integer, Player> getButtonClickedListener() {
        return this.buttonClickedListener;
    }

    public void setButtonClickedListener(BiConsumer<Integer, Player> buttonClickedListener) {
        this.buttonClickedListener = buttonClickedListener;
    }

    public Consumer<Player> getWindowClosedListener() {
        return this.windowClosedListener;
    }

    public void setWindowClosedListener(Consumer<Player> windowClosedListener) {
        this.windowClosedListener = windowClosedListener;
    }

    public FormWindow getParent() {
        return this.parent;
    }

    public void setParent(FormWindow parent) {
        this.parent = parent;
    }

    public Boolean getBack() {
        return this.isBack;
    }

    public void setBack(Boolean back) {
        this.isBack = back;
    }
}


