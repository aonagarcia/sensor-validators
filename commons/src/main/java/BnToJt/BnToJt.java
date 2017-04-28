/*    */ package BnToJt;
/*    */ 
/*    */ import Network.HelpPanel;
/*    */ import java.applet.Applet;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Toolkit;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BnToJt
/*    */   extends Applet
/*    */ {
/* 15 */   static final String titleText = new String("WEBWEAVR-IV : Bayesian Network Compiler");
/*    */   
/* 17 */   static final String welcomeText = new String("Welcome to Bayesian Network Compiler!");
/*    */   
/*    */   static MainFrame mainFrame;
/*    */   
/*    */   public static void main(String[] paramArrayOfString)
/*    */   {
/* 23 */     mainFrame = new MainFrame(titleText, false);
/* 24 */     Dimension localDimension = calcFrameSize();
/* 25 */     mainFrame.setSize(localDimension);
/* 26 */     mainFrame.setVisible(true);
/* 27 */     HelpPanel.addHelp(welcomeText);
/*    */   }
/*    */   
/*    */   private static Dimension calcFrameSize()
/*    */   {
/* 32 */     Toolkit localToolkit = Toolkit.getDefaultToolkit();
/* 33 */     Dimension localDimension = localToolkit.getScreenSize();
/* 34 */     localDimension.width /= 2;
/* 35 */     localDimension.height = (localDimension.height * 3 / 4);
/* 36 */     return localDimension;
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/BnToJt/BnToJt.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */