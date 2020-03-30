/*    */ package com.zixuan007.society.utils;
/*    */ 
/*    */ import cn.nukkit.Player;
/*    */ import cn.nukkit.Server;
/*    */ import java.io.File;
/*    */ import java.lang.reflect.Method;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import java.net.URLClassLoader;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PluginUtils
/*    */ {
/*    */   public static void loadJar(String jarPath) throws MalformedURLException {
/* 22 */     File jarFile = new File(jarPath);
/*    */     
/* 24 */     if (!jarFile.exists()) {
/* 25 */       System.out.println("jar file not found.");
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 30 */     Method method = null;
/*    */     try {
/* 32 */       method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
/* 33 */     } catch (NoSuchMethodException|SecurityException e1) {
/* 34 */       e1.printStackTrace();
/*    */     } 
/*    */ 
/*    */     
/* 38 */     boolean accessible = method.isAccessible();
/*    */     
/*    */     try {
/* 41 */       if (!accessible) {
/* 42 */         method.setAccessible(true);
/*    */       }
/*    */ 
/*    */       
/* 46 */       URLClassLoader classLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();
/*    */ 
/*    */       
/* 49 */       URL url = jarFile.toURI().toURL();
/*    */ 
/*    */       
/* 52 */       method.invoke(classLoader, new Object[] { url });
/* 53 */     } catch (Exception e) {
/* 54 */       e.printStackTrace();
/*    */     } finally {
/*    */       
/* 57 */       method.setAccessible(accessible);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isOnlineByName(String playerName) {
/* 67 */     for (Player player : Server.getInstance().getOnlinePlayers().values()) {
/* 68 */       if (player.getName().equals(playerName)) return true; 
/*    */     } 
/* 70 */     return false;
/*    */   }
/*    */ }