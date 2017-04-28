/*    */ package LThinker;
/*    */ 
/*    */ import Network.HelpPanel;
/*    */ import java.applet.Applet;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Toolkit;
/*    */ 
/*    */ public class LThinker
/*    */   extends Applet
/*    */ {
/* 11 */   static final String titleText = new String("WEBWEAVR-IV : Belief Network Lazy-Inference Engine");
/*    */   
/* 13 */   static final String welcomeText = new String("Welcome to Belief Network Lazy-Inference Engine!");
/*    */   
/*    */   static MainFrame mainFrame;
/*    */   
/*    */   public static void main(String[] paramArrayOfString)
/*    */   {
/* 19 */     mainFrame = new MainFrame(titleText, false);
/* 20 */     Dimension localDimension = calcFrameSize();
/* 21 */     mainFrame.setSize(localDimension);
/* 22 */     mainFrame.setVisible(true);
/* 23 */     HelpPanel.addHelp(welcomeText);
/*    */   }
/*    */   
/*    */   private static Dimension calcFrameSize()
/*    */   {
/* 28 */     Toolkit localToolkit = Toolkit.getDefaultToolkit();
/* 29 */     Dimension localDimension = localToolkit.getScreenSize();
/* 30 */     localDimension.width /= 2;
/* 31 */     localDimension.height = (localDimension.height * 3 / 4);
/* 32 */     return localDimension;
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/LThinker/LThinker.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */