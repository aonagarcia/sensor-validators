/*    */ package FrontEnd;
/*    */ 
/*    */ import java.awt.Frame;
/*    */ import java.awt.ScrollPane;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MenuPanel
/*    */   extends ScrollPane
/*    */ {
/*    */   Frame frame;
/*    */   Menu menu;
/*    */   static final int START_MODE = 0;
/*    */   static final int RUN_MODE = 1;
/*    */   static final int CUT_MODE = 2;
/*    */   static final int PASTE_MODE = 3;
/*    */   static final int HELP_MODE = 4;
/* 22 */   int mode = 0;
/*    */   
/*    */   public MenuPanel(Frame paramFrame)
/*    */   {
/* 26 */     this.frame = paramFrame;
/* 27 */     this.menu = new Menu(paramFrame, this);
/* 28 */     add(this.menu);
/* 29 */     repaint();
/*    */   }
/*    */   
/* 32 */   void add() { this.menu.add(); }
/* 33 */   void load() { this.menu.load(); }
/* 34 */   void save() { this.menu.save(); }
/* 35 */   void sort() { this.menu.sort(); }
/*    */   
/*    */   void setMode(String paramString)
/*    */   {
/* 39 */     if (paramString.equals(CommandPanel.runLabel)) { this.mode = 1;
/* 40 */     } else if (paramString.equals(CommandPanel.cutLabel)) { this.mode = 2;
/* 41 */     } else if (paramString.equals(CommandPanel.pasteLabel)) { this.mode = 3;
/* 42 */     } else if (paramString.equals(CommandPanel.helpLabel)) this.mode = 4; else {
/* 43 */       this.mode = 0;
/*    */     }
/*    */   }
/*    */   
/* 47 */   boolean isRunMode() { return this.mode == 1; }
/* 48 */   boolean isCutMode() { return this.mode == 2; }
/* 49 */   boolean isPasteMode() { return this.mode == 3; }
/* 50 */   boolean isHelpMode() { return this.mode == 4; }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/FrontEnd/MenuPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */