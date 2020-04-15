package com.zixuan007.society.window;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowModal;
import com.sun.istack.internal.NotNull;
import com.zixuan007.society.window.response.ResponseListenerModal;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class ModalWindow extends FormWindowModal implements ResponseListenerModal {
    protected transient BiConsumer<Boolean, Player> buttonClickedListener = null;
    protected transient Consumer<Player> windowClosedListener = null;
    private transient FormWindow parent;
    private Boolean isBack = Boolean.valueOf(false);

    public ModalWindow(String title, String content, String trueButtonText, String falseButtonText) {
        super(title, content, trueButtonText, falseButtonText);
    }

    public static boolean onEvent(FormWindow formWindow, FormResponse response, Player player) {
        if (formWindow instanceof ModalWindow) {
            ModalWindow window = (ModalWindow)formWindow;

            if (window.wasClosed() || response == null) {
                if (window.isBack.booleanValue()) {
                    window.callBack(player);
                } else {
                    window.callClosed(player);
                }
                window.closed = false;
            } else {
                window.callClicked((((FormResponseModal)response).getClickedButtonId() == 0), player);
            }
            return true;
        }
        return false;
    }

    private void callBack(Player player) {
        player.showFormWindow(this.parent);
    }

    private void callClicked(boolean b, Player player) {
        Objects.requireNonNull(Boolean.valueOf(b));
        Objects.requireNonNull(player);
        onClick(b, player);
        if (this.buttonClickedListener != null)
            this.buttonClickedListener.accept(Boolean.valueOf(b), player);
    }

    private void callClosed(Player player) {
        Objects.requireNonNull(player);
        onClose(player);
        if (this.windowClosedListener != null) {
            this.windowClosedListener.accept(player);
        }
    }






    public final ModalWindow onClick(@NotNull BiConsumer<Boolean, Player> listener) {
        Objects.requireNonNull(listener);
        this.buttonClickedListener = listener;
        return this;
    }







    public final void onClosed(@NotNull Consumer<Player> listener) {
        Objects.requireNonNull(listener);
        this.windowClosedListener = listener;
    }

    public BiConsumer<Boolean, Player> getButtonClickedListener() {
        return this.buttonClickedListener;
    }

    public void setButtonClickedListener(BiConsumer<Boolean, Player> buttonClickedListener) {
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
        /* 114 */     return this.isBack;
    }

    public void setBack(Boolean back) {
        /* 118 */     this.isBack = back;
    }
}

