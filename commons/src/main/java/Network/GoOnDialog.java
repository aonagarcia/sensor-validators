/*    */ package Network;
/*    */ 
/*    */ import java.awt.Frame;
/*    */ import java.awt.Panel;
/*    */ import java.awt.event.ActionEvent;
/*    */ 
/*    */ public class GoOnDialog extends java.awt.Dialog implements java.awt.event.ActionListener
/*    */ {
/*    */   public java.awt.Button button;
/*    */   Object caller;
/*    */   
/*    */   public GoOnDialog(Frame paramFrame, Object paramObject, String paramString1, String paramString2, int paramInt1, int paramInt2)
/*    */   {
/* 14 */     super(paramFrame, "", true);
/* 15 */     setLocation(paramFrame.getLocationOnScreen().x + paramInt1, paramFrame.getLocationOnScreen().y + paramInt2);
/*    */     
/* 17 */     setFont(NetPaint.helvetica15);
/*    */     
/* 19 */     this.caller = paramObject;
/* 20 */     Panel localPanel = new Panel();
/* 21 */     add(localPanel);
/* 22 */     localPanel.add(new java.awt.Label(paramString1));
/* 23 */     this.button = new java.awt.Button(paramString2);
/* 24 */     this.button.addActionListener(this);
/* 25 */     localPanel.add(this.button);
/* 26 */     pack();
/*    */   }
/*    */   
/*    */   public void actionPerformed(ActionEvent paramActionEvent)
/*    */   {
/* 31 */     if (paramActionEvent.getSource() == this.button) {
/* 32 */       dispose();
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/GoOnDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */