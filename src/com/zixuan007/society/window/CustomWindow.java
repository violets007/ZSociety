/*     */ package com.zixuan007.society.window;
/*     */ 
/*     */ import cn.nukkit.Player;
/*     */ import cn.nukkit.form.response.FormResponse;
/*     */ import cn.nukkit.form.response.FormResponseCustom;
/*     */ import cn.nukkit.form.window.FormWindow;
/*     */ import cn.nukkit.form.window.FormWindowCustom;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.zixuan007.society.window.response.ResponseListennerCustom;
/*     */ import java.util.Objects;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomWindow
/*     */   extends FormWindowCustom
/*     */   implements ResponseListennerCustom
/*     */ {
/*  20 */   protected transient BiConsumer<FormResponseCustom, Player> buttonClickedListener = null;
/*  21 */   protected transient Consumer<Player> windowClosedListener = null;
/*     */   private transient FormWindow parent;
/*  23 */   private Boolean isBack = Boolean.valueOf(false);
/*     */   
/*     */   public CustomWindow(String title) {
/*  26 */     super(title);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean onEvent(FormWindow formWindow, FormResponse response, Player player) {
/*  31 */     if (formWindow instanceof CustomWindow) {
/*     */       
/*  33 */       CustomWindow window = (CustomWindow)formWindow;
/*     */ 
/*     */       
/*  36 */       if (window.wasClosed() || response == null) {
/*     */         
/*  38 */         if (window.isBack.booleanValue()) {
/*  39 */           window.callBack(player);
/*     */         } else {
/*  41 */           window.callClosed(player);
/*     */         } 
/*  43 */         window.closed = false;
/*     */       } else {
/*     */         
/*  46 */         window.callClicked((FormResponseCustom)response, player);
/*     */       } 
/*  48 */       return true;
/*     */     } 
/*  50 */     return false;
/*     */   }
/*     */   
/*     */   private void callClicked(FormResponseCustom response, Player player) {
/*  54 */     Objects.requireNonNull(response);
/*  55 */     Objects.requireNonNull(player);
/*  56 */     onClick(response, player);
/*  57 */     if (this.buttonClickedListener != null)
/*  58 */       this.buttonClickedListener.accept(response, player); 
/*     */   }
/*     */   
/*     */   private void callClosed(Player player) {
/*  62 */     Objects.requireNonNull(player);
/*  63 */     onClose(player);
/*  64 */     if (this.windowClosedListener != null)
/*  65 */       this.windowClosedListener.accept(player); 
/*     */   }
/*     */   
/*     */   public void callBack(Player player) {
/*  69 */     Objects.requireNonNull(player);
/*  70 */     player.showFormWindow(this.parent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void onClick(@NotNull BiConsumer<FormResponseCustom, Player> listener) {
/*  80 */     Objects.requireNonNull(listener);
/*  81 */     this.buttonClickedListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClose(@NotNull Consumer<Player> listener) {
/*  90 */     Objects.requireNonNull(listener);
/*  91 */     this.windowClosedListener = listener;
/*     */   }
/*     */ 
/*     */   
/*     */   public BiConsumer<FormResponseCustom, Player> getButtonClickedListener() {
/*  96 */     return this.buttonClickedListener;
/*     */   }
/*     */   
/*     */   public void setButtonClickedListener(BiConsumer<FormResponseCustom, Player> buttonClickedListener) {
/* 100 */     this.buttonClickedListener = buttonClickedListener;
/*     */   }
/*     */   
/*     */   public Consumer<Player> getWindowClosedListener() {
/* 104 */     return this.windowClosedListener;
/*     */   }
/*     */   
/*     */   public void setWindowClosedListener(Consumer<Player> windowClosedListener) {
/* 108 */     this.windowClosedListener = windowClosedListener;
/*     */   }
/*     */   
/*     */   public FormWindow getParent() {
/* 112 */     return this.parent;
/*     */   }
/*     */   
/*     */   public void setParent(FormWindow parent) {
/* 116 */     this.parent = parent;
/*     */   }
/*     */   
/*     */   public Boolean getBack() {
/* 120 */     return this.isBack;
/*     */   }
/*     */   
/*     */   public void setBack(Boolean back) {
/* 124 */     this.isBack = back;
/*     */   }
/*     */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\window\CustomWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */