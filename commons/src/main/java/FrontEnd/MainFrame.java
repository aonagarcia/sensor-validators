/*    */ package FrontEnd;
/*    */ 
/*    */ import Network.HelpPanel;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Frame;
/*    */ import java.awt.Toolkit;
/*    */ import java.awt.event.WindowAdapter;
/*    */ import java.awt.event.WindowEvent;
/*    */ 
/*    */ class MainFrame extends Frame
/*    */ {
/* 12 */   static boolean isApplet = false;
/*    */   CommandPanel cmdPanel;
/*    */   HelpPanel helpPanel;
/*    */   MenuPanel menuPanel;
/* 16 */   static final String welcomeText = new String("\tWelcome to \nWEBWEAVR-IV Front End!");
/*    */   
/*    */ 
/*    */   MainFrame(String paramString)
/*    */   {
/* 21 */     this(paramString, false);
/*    */   }
/*    */   
/*    */   MainFrame(String paramString, boolean paramBoolean) {
/* 25 */     super(paramString);
/* 26 */     isApplet = paramBoolean;
/*    */     
/* 28 */     this.helpPanel = new HelpPanel();
/* 29 */     this.menuPanel = new MenuPanel(this);
/* 30 */     this.cmdPanel = new CommandPanel(this, this.menuPanel);
/*    */     
/* 32 */     int i = 0;int j = 5;
/* 33 */     setLayout(new java.awt.BorderLayout(i, j));
/*    */     
/*    */ 
/*    */ 
/* 37 */     add("North", this.cmdPanel);
/* 38 */     add("Center", this.menuPanel);
/* 39 */     add("South", this.helpPanel);
/*    */     
/* 41 */     Dimension localDimension = calcFrameSize();
/* 42 */     setSize(localDimension);
/* 43 */     setLocation(500, 0);
/* 44 */     setVisible(true);
/* 45 */     HelpPanel.addHelp(welcomeText);
/*    */     
/* 47 */     addWindowListener(new WindowAdapter()
/*    */     {
/*    */       public void windowClosing(WindowEvent paramAnonymousWindowEvent) {
/* 50 */         System.exit(0);
/*    */       }
/*    */     });
/*    */   }
/*    */   
/*    */ 
/*    */   private static Dimension calcFrameSize()
/*    */   {
/* 58 */     Toolkit localToolkit = Toolkit.getDefaultToolkit();
/* 59 */     Dimension localDimension = localToolkit.getScreenSize();
/* 60 */     localDimension.width = (localDimension.width * 1 / 4);
/* 61 */     localDimension.height = (localDimension.height * 3 / 4);
/* 62 */     return localDimension;
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/FrontEnd/MainFrame.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */