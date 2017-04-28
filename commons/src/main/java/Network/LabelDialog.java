/*    */ package Network;
/*    */ 
/*    */ import java.awt.Frame;
/*    */ import java.awt.Panel;
/*    */ import java.awt.TextField;
/*    */ 
/*    */ public class LabelDialog extends java.awt.Dialog implements java.awt.event.ActionListener
/*    */ {
/*    */   String title;
/*    */   String prompt;
/*    */   TextField labelField;
/* 12 */   final int FIELD_WIDTH = 16;
/*    */   
/*    */   java.awt.Button doneButton;
/*    */   Bridge netPanel;
/*    */   Frame frame;
/*    */   
/*    */   public LabelDialog(Frame paramFrame, Bridge paramBridge, String paramString1, String paramString2, String paramString3)
/*    */   {
/* 20 */     super(paramFrame, paramString1, false);
/* 21 */     setLocation(paramFrame.getLocationOnScreen().x + 100, paramFrame.getLocationOnScreen().y + 100);
/*    */     
/* 23 */     setFont(NetPaint.helvetica15);
/* 24 */     this.frame = paramFrame;
/* 25 */     this.netPanel = paramBridge;
/*    */     
/* 27 */     Panel localPanel = new Panel();
/* 28 */     java.awt.Label localLabel = new java.awt.Label(paramString2 + ":");
/* 29 */     localPanel.add(localLabel);
/* 30 */     this.labelField = new TextField(16);
/* 31 */     this.labelField.setText(paramString3);
/* 32 */     localPanel.add(this.labelField);
/*    */     
/* 34 */     this.doneButton = new java.awt.Button("Done");
/* 35 */     this.doneButton.addActionListener(this);
/* 36 */     localPanel.add(this.doneButton);
/* 37 */     add("Center", localPanel);
/*    */     
/* 39 */     pack();
/*    */     
/* 41 */     this.title = new String(paramString1);
/* 42 */     this.prompt = new String(paramString2);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void actionPerformed(java.awt.event.ActionEvent paramActionEvent)
/*    */   {
/* 51 */     if (paramActionEvent.getSource() == this.doneButton) {
/* 52 */       String[] arrayOfString = new String[3];
/* 53 */       arrayOfString[0] = this.labelField.getText();
/* 54 */       arrayOfString[1] = new String(this.title);
/* 55 */       arrayOfString[2] = new String(this.prompt);
/* 56 */       this.netPanel.setVector2(arrayOfString);
/* 57 */       this.netPanel.showNet();
/* 58 */       dispose();
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/LabelDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */