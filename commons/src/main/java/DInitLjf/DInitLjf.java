/*    */ package DInitLjf;
/*    */ 
/*    */ import Network.HelpPanel;
/*    */ import java.applet.Applet;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Toolkit;
/*    */ 
/*    */ 
/*    */ public class DInitLjf
/*    */   extends Applet
/*    */ {
/* 12 */   static final String titleText = new String("WEBWEAVR-IV : Distributed MSBN Compiler For Belief Initialization");
/*    */   
/* 14 */   static final String welcomeText = new String("Welcome to Distributed MSBN Compiler For Belief Initialization!");
/*    */   
/*    */ 
/*    */ 
/*    */   static MainFrame mainFrame;
/*    */   
/*    */ 
/*    */ 
/*    */   public static void main(String[] paramArrayOfString)
/*    */   {
/* 24 */     mainFrame = new MainFrame(titleText, false);
/* 25 */     Dimension localDimension = calcFrameSize();
/* 26 */     mainFrame.setSize(localDimension);
/* 27 */     mainFrame.setVisible(true);
/* 28 */     HelpPanel.addHelp(welcomeText);
/*    */   }
/*    */   
/*    */   private static Dimension calcFrameSize()
/*    */   {
/* 33 */     Toolkit localToolkit = Toolkit.getDefaultToolkit();
/* 34 */     Dimension localDimension = localToolkit.getScreenSize();
/* 35 */     localDimension.width /= 3;
/* 36 */     localDimension.height = (localDimension.height * 2 / 5);
/* 37 */     return localDimension;
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/DInitLjf/DInitLjf.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */