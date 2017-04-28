/*    */ package DMasMsbn;
/*    */ 
/*    */ import Network.HelpPanel;
/*    */ import java.applet.Applet;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Toolkit;
/*    */ 
/*    */ public class DMasMsbn extends Applet
/*    */ {
/* 10 */   static final String titleText = new String("WEBWEAVR-IV : Distributed MAS MSBN Inference Engine");
/*    */   
/* 12 */   static final String welcomeText = new String("Welcome to Distributed MAS MSBN Inference Engine!");
/*    */   
/*    */   static MainFrame mainFrame;
/*    */   
/*    */   public static void main(String[] paramArrayOfString)
/*    */   {
/* 18 */     mainFrame = new MainFrame(titleText, false);
/* 19 */     Dimension localDimension = calcFrameSize();
/* 20 */     mainFrame.setSize(localDimension);
/* 21 */     mainFrame.setVisible(true);
/* 22 */     HelpPanel.addHelp(welcomeText);
/*    */   }
/*    */   
/*    */   private static Dimension calcFrameSize()
/*    */   {
/* 27 */     Toolkit localToolkit = Toolkit.getDefaultToolkit();
/* 28 */     Dimension localDimension = localToolkit.getScreenSize();
/* 29 */     localDimension.width /= 3;
/* 30 */     localDimension.height = (localDimension.height * 2 / 5);
/* 31 */     return localDimension;
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/DMasMsbn/DMasMsbn.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */