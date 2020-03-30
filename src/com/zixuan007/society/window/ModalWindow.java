/*     */ package com.zixuan007.society.window;
/*     */ 
/*     */ import cn.nukkit.Player;
/*     */ import cn.nukkit.form.response.FormResponse;
/*     */ import cn.nukkit.form.response.FormResponseModal;
/*     */ import cn.nukkit.form.window.FormWindow;
/*     */ import cn.nukkit.form.window.FormWindowModal;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.zixuan007.society.window.response.ResponseListenerModal;
/*     */ import java.util.Objects;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ 
/*     */ public class ModalWindow
/*     */   extends FormWindowModal
/*     */   implements ResponseListenerModal
/*     */ {
/*  19 */   protected transient BiConsumer<Boolean, Player> buttonClickedListener = null;
/*  20 */   protected transient Consumer<Player> windowClosedListener = null;
/*     */   private transient FormWindow parent;
/*  22 */   private Boolean isBack = Boolean.valueOf(false);
/*     */   
/*     */   public ModalWindow(String title, String content, String trueButtonText, String falseButtonText) {
/*  25 */     super(title, content, trueButtonText, falseButtonText);
/*     */   }
/*     */   
/*     */   public static boolean onEvent(FormWindow formWindow, FormResponse response, Player player) {
/*  29 */     if (formWindow instanceof ModalWindow) {
/*  30 */       ModalWindow window = (ModalWindow)formWindow;
/*     */
/*  32 */       if (window.wasClosed() || response == null) {
/*  33 */         if (window.isBack.booleanValue()) {
/*  34 */           window.callBack(player);
/*     */         } else {
/*  36 */           window.callClosed(player);
/*     */         } 
/*  38 */         window.closed = false;
/*     */       } else {
/*  40 */         window.callClicked((((FormResponseModal)response).getClickedButtonId() == 0), player);
/*     */       } 
/*  42 */       return true;
/*     */     } 
/*  44 */     return false;
/*     */   }
/*     */   
/*     */   private void callBack(Player player) {
/*  48 */     player.showFormWindow(this.parent);
/*     */   }
/*     */   
/*     */   private void callClicked(boolean b, Player player) {
/*  52 */     Objects.requireNonNull(Boolean.valueOf(b));
/*  53 */     Objects.requireNonNull(player);
/*  54 */     onClick(b, player);
/*  55 */     if (this.buttonClickedListener != null)
/*  56 */       this.buttonClickedListener.accept(Boolean.valueOf(b), player); 
/*     */   }
/*     */   
/*     */   private void callClosed(Player player) {
/*  60 */     Objects.requireNonNull(player);
/*  61 */     onClose(player);
/*  62 */     if (this.windowClosedListener != null) {
/*  63 */       this.windowClosedListener.accept(player);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ModalWindow onClick(@NotNull BiConsumer<Boolean, Player> listener) {
/*  73 */     Objects.requireNonNull(listener);
/*  74 */     this.buttonClickedListener = listener;
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void onClosed(@NotNull Consumer<Player> listener) {
/*  85 */     Objects.requireNonNull(listener);
/*  86 */     this.windowClosedListener = listener;
/*     */   }
/*     */   
/*     */   public BiConsumer<Boolean, Player> getButtonClickedListener() {
/*  90 */     return this.buttonClickedListener;
/*     */   }
/*     */   
/*     */   public void setButtonClickedListener(BiConsumer<Boolean, Player> buttonClickedListener) {
/*  94 */     this.buttonClickedListener = buttonClickedListener;
/*     */   }
/*     */   
/*     */   public Consumer<Player> getWindowClosedListener() {
/*  98 */     return this.windowClosedListener;
/*     */   }
/*     */   
/*     */   public void setWindowClosedListener(Consumer<Player> windowClosedListener) {
/* 102 */     this.windowClosedListener = windowClosedListener;
/*     */   }
/*     */   
/*     */   public FormWindow getParent() {
/* 106 */     return this.parent;
/*     */   }
/*     */   
/*     */   public void setParent(FormWindow parent) {
/* 110 */     this.parent = parent;
/*     */   }
/*     */   
/*     */   public Boolean getBack() {
/* 114 */     return this.isBack;
/*     */   }
/*     */   
/*     */   public void setBack(Boolean back) {
/* 118 */     this.isBack = back;
/*     */   }
/*     */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\window\ModalWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */