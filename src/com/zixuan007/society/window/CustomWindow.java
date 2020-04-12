package com.zixuan007.society.window;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowCustom;
import com.sun.istack.internal.NotNull;
import com.zixuan007.society.window.response.ResponseListennerCustom;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;



public class CustomWindow extends FormWindowCustom implements ResponseListennerCustom {
    protected transient BiConsumer<FormResponseCustom, Player> buttonClickedListener = null;
    protected transient Consumer<Player> windowClosedListener = null;
    private transient FormWindow parent;
    private Boolean isBack = Boolean.valueOf(false);

    public CustomWindow(String title) {
        super(title);
    }

    public static boolean onEvent(FormWindow formWindow, FormResponse response, Player player) {
        if (formWindow instanceof CustomWindow) {
            CustomWindow window = (CustomWindow)formWindow;
            if (window.wasClosed() || response == null) {
                if (window.isBack.booleanValue()) {
                    window.callBack(player);
                } else {
                    window.callClosed(player);
                }
                window.closed = false;
            } else {

                window.callClicked((FormResponseCustom)response, player);
            }
            return true;
        }
        return false;
    }

    private void callClicked(FormResponseCustom response, Player player) {
        Objects.requireNonNull(response);
        Objects.requireNonNull(player);
        onClick(response, player);
        if (this.buttonClickedListener != null)
            this.buttonClickedListener.accept(response, player);
    }

    private void callClosed(Player player) {
        Objects.requireNonNull(player);
        onClose(player);
        if (this.windowClosedListener != null)
            this.windowClosedListener.accept(player);
    }

    public void callBack(Player player) {
        Objects.requireNonNull(player);
        player.showFormWindow(this.parent);
    }

    public final void onClick(@NotNull BiConsumer<FormResponseCustom, Player> listener) {
        Objects.requireNonNull(listener);
        this.buttonClickedListener = listener;
    }

    public void onClose(@NotNull Consumer<Player> listener) {
        Objects.requireNonNull(listener);
        this.windowClosedListener = listener;
    }

    public BiConsumer<FormResponseCustom, Player> getButtonClickedListener() {
        return this.buttonClickedListener;
    }

    public void setButtonClickedListener(BiConsumer<FormResponseCustom, Player> buttonClickedListener) {
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

