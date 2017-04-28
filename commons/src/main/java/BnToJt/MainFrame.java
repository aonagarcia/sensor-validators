/*    */ package BnToJt;
/*    */ 
/*    */ import Network.HelpPanel;
/*    */ import java.awt.Frame;
/*    */ import java.awt.event.WindowAdapter;
/*    */ import java.awt.event.WindowEvent;
/*    */ 
/*    */ class MainFrame extends Frame
/*    */ {
/* 10 */   static boolean isApplet = false;
/*    */   CommandPanel cmdPanel;
/*    */   HelpPanel helpPanel;
/*    */   NetworkPanel netPanel;
/*    */   
/*    */   MainFrame(String paramString)
/*    */   {
/* 17 */     this(paramString, false);
/*    */   }
/*    */   
/*    */   MainFrame(String paramString, boolean paramBoolean) {
/* 21 */     super(paramString);
/* 22 */     isApplet = paramBoolean;
/*    */     
/* 24 */     this.helpPanel = new HelpPanel();
/* 25 */     this.netPanel = new NetworkPanel(this);
/* 26 */     this.cmdPanel = new CommandPanel(this, this.netPanel);
/*    */     
/* 28 */     int i = 0;int j = 5;
/* 29 */     setLayout(new java.awt.BorderLayout(i, j));
/*    */     
/*    */ 
/*    */ 
/* 33 */     add("North", this.cmdPanel);
/* 34 */     add("Center", this.netPanel);
/* 35 */     add("South", this.helpPanel);
/* 36 */     addWindowListener(new WindowAdapter()
/*    */     {
/*    */       public void windowClosing(WindowEvent paramAnonymousWindowEvent) {
/* 39 */         System.exit(0);
/*    */       }
/*    */     });
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/BnToJt/MainFrame.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */