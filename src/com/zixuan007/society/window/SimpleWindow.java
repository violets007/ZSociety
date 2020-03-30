/*     */ package com.zixuan007.society.window;
/*     */ 
/*     */ import cn.nukkit.Player;
/*     */ import cn.nukkit.form.response.FormResponse;
/*     */ import cn.nukkit.form.response.FormResponseSimple;
/*     */ import cn.nukkit.form.window.FormWindow;
/*     */ import cn.nukkit.form.window.FormWindowSimple;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.zixuan007.society.window.response.ResponseListenerSimple;
/*     */ import java.util.Objects;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleWindow
/*     */   extends FormWindowSimple
/*     */   implements ResponseListenerSimple
/*     */ {
/*  20 */   protected transient BiConsumer<Integer, Player> buttonClickedListener = null;
/*  21 */   protected transient Consumer<Player> windowClosedListener = null;
/*     */   private transient FormWindow parent;
/*  23 */   private Boolean isBack = Boolean.valueOf(false);
/*     */   
/*     */   public SimpleWindow(String title, String content) {
/*  26 */     super(title, content);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean onEvent(FormWindow formWindow, FormResponse response, Player player) {
/*  37 */     if (formWindow instanceof SimpleWindow) {
/*  38 */       SimpleWindow window = (SimpleWindow)formWindow;
/*     */       
/*  40 */       if (window.wasClosed() || response == null) {
/*  41 */         if (window.isBack.booleanValue()) {
/*  42 */           window.callBack(player);
/*     */         } else {
/*  44 */           window.callClosed(player);
/*     */         } 
/*  46 */         window.closed = false;
/*     */       } else {
/*     */         
/*  49 */         window.callClicked(((FormResponseSimple)response).getClickedButtonId(), player);
/*     */       } 
/*     */       
/*  52 */       return true;
/*     */     } 
/*  54 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void callClicked(int clickedButtonId, Player player) {
/*  64 */     onClick(clickedButtonId, player);
/*  65 */     if (this.buttonClickedListener != null) {
/*  66 */       this.buttonClickedListener.accept(Integer.valueOf(clickedButtonId), player);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void callClosed(Player player) {
/*  75 */     onClose(player);
/*  76 */     if (this.buttonClickedListener != null) {
/*  77 */       this.windowClosedListener.accept(player);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void callBack(Player player) {
/*  86 */     player.showFormWindow(this.parent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void onClicked(@NotNull BiConsumer<Integer, Player> listener) {
/*  96 */     Objects.requireNonNull(listener);
/*  97 */     if (this.buttonClickedListener != null) {
/*  98 */       this.buttonClickedListener = listener;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void onClosed(@NotNull Consumer<Player> listener) {
/* 108 */     Objects.requireNonNull(listener);
/* 109 */     if (this.windowClosedListener != null) {
/* 110 */       this.windowClosedListener = listener;
/*     */     }
/*     */   }
/*     */   
/*     */   public BiConsumer<Integer, Player> getButtonClickedListener() {
/* 115 */     return this.buttonClickedListener;
/*     */   }
/*     */   
/*     */   public void setButtonClickedListener(BiConsumer<Integer, Player> buttonClickedListener) {
/* 119 */     this.buttonClickedListener = buttonClickedListener;
/*     */   }
/*     */   
/*     */   public Consumer<Player> getWindowClosedListener() {
/* 123 */     return this.windowClosedListener;
/*     */   }
/*     */   
/*     */   public void setWindowClosedListener(Consumer<Player> windowClosedListener) {
/* 127 */     this.windowClosedListener = windowClosedListener;
/*     */   }
/*     */   
/*     */   public FormWindow getParent() {
/* 131 */     return this.parent;
/*     */   }
/*     */   
/*     */   public void setParent(FormWindow parent) {
/* 135 */     this.parent = parent;
/*     */   }
/*     */   
/*     */   public Boolean getBack() {
/* 139 */     return this.isBack;
/*     */   }
/*     */   
/*     */   public void setBack(Boolean back) {
/* 143 */     this.isBack = back;
/*     */   }
/*     */ }


/* Location:              D:\下载\ZSociety-1.0.3alpha.jar!\com\zixuan007\society\window\SimpleWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */