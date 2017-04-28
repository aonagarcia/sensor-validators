/*    */ package Simulatr;
/*    */ 
/*    */ import Network.HelpPanel;
/*    */ import java.applet.Applet;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Toolkit;
/*    */ import java.net.UnknownHostException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Simulatr
/*    */   extends Applet
/*    */ {
/* 21 */   static final String titleText = new String("WEBWEAVR-IV : Scenario Simulator");
/*    */   
/* 23 */   static final String welcomeText = new String("Welcome to Scenario Simulator!");
/*    */   
/*    */   static MainFrame mainFrame;
/*    */   
/*    */   public static void main(String[] paramArrayOfString)
/*    */   {
/* 29 */     mainFrame = new MainFrame(titleText, false);
/* 30 */     Dimension localDimension = calcFrameSize();
/* 31 */     mainFrame.setSize(localDimension);
/* 32 */     mainFrame.setVisible(true);
/* 33 */     HelpPanel.addHelp(welcomeText);
/*    */     try
/*    */     {
/* 36 */       mainFrame.netPanel.startServer();
/*    */     } catch (UnknownHostException localUnknownHostException) {
/* 38 */       HelpPanel.showError("Start server socket.");
/*    */     }
/*    */   }
/*    */   
/*    */   private static Dimension calcFrameSize()
/*    */   {
/* 44 */     Toolkit localToolkit = Toolkit.getDefaultToolkit();
/* 45 */     Dimension localDimension = localToolkit.getScreenSize();
/* 46 */     localDimension.width /= 2;
/* 47 */     localDimension.height = (localDimension.height * 3 / 4);
/* 48 */     return localDimension;
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Simulatr/Simulatr.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */