/*    */ package IntMsbn;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IntMsbn
/*    */   extends Applet
/*    */ {
/* 18 */   static final String titleText = new String("WEBWEAVR-IV : MAS MSBN Integrator");
/*    */   
/* 20 */   static final String welcomeText = new String("Welcome to MAS MSBN Integrator!");
/*    */   
/*    */ 
/*    */ 
/*    */   static MainFrame mainFrame;
/*    */   
/*    */ 
/*    */ 
/*    */   public static void main(String[] paramArrayOfString)
/*    */   {
/* 30 */     mainFrame = new MainFrame(titleText, false);
/* 31 */     Dimension localDimension = calcFrameSize();
/* 32 */     mainFrame.setSize(localDimension);
/* 33 */     mainFrame.setVisible(true);
/* 34 */     HelpPanel.addHelp(welcomeText);
/*    */   }
/*    */   
/*    */   private static Dimension calcFrameSize()
/*    */   {
/* 39 */     Toolkit localToolkit = Toolkit.getDefaultToolkit();
/* 40 */     Dimension localDimension = localToolkit.getScreenSize();
/* 41 */     localDimension.width /= 2;
/* 42 */     localDimension.height = (localDimension.height * 3 / 4);
/* 43 */     return localDimension;
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/IntMsbn/IntMsbn.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */