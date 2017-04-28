/*    */ package Network;
/*    */ 
/*    */ import java.awt.Button;
/*    */ import java.awt.Dialog;
/*    */ import java.awt.Frame;
/*    */ import java.awt.Point;
/*    */ import java.awt.TextArea;
/*    */ import java.awt.event.ActionEvent;
/*    */ 
/*    */ public class HelpDialog extends Dialog implements java.awt.event.ActionListener
/*    */ {
/*    */   Button button;
/*    */   
/*    */   public HelpDialog(Frame paramFrame, String paramString, int paramInt1, int paramInt2)
/*    */   {
/* 16 */     super(paramFrame, "", true);
/* 17 */     setLocation(paramFrame.getLocationOnScreen().x + paramInt1, paramFrame.getLocationOnScreen().y + paramInt2);
/*    */     
/* 19 */     setFont(NetPaint.helvetica14);
/*    */     
/* 21 */     setLayout(new java.awt.BorderLayout());
/* 22 */     this.button = new Button("Done");
/* 23 */     this.button.addActionListener(this);
/* 24 */     add("North", this.button);
/*    */     
/* 26 */     TextArea localTextArea = new TextArea(10, 50);
/* 27 */     localTextArea.setEditable(false);
/* 28 */     localTextArea.setText(paramString);
/* 29 */     add("South", localTextArea);
/* 30 */     pack();
/*    */   }
/*    */   
/*    */   public void actionPerformed(ActionEvent paramActionEvent)
/*    */   {
/* 35 */     if (paramActionEvent.getSource() == this.button) dispose();
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/HelpDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */