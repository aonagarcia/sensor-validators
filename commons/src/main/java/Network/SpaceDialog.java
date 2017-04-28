/*    */ package Network;
/*    */ 
/*    */ import java.awt.Frame;
/*    */ import java.awt.Panel;
/*    */ import java.awt.TextField;
/*    */ 
/*    */ public class SpaceDialog extends java.awt.Dialog implements java.awt.event.ActionListener
/*    */ {
/*    */   TextField labelField;
/*    */   TextField scntField;
/* 11 */   final int FIELD_WIDTH = 16;
/*    */   
/*    */   java.awt.Button doneButton;
/*    */   
/*    */   Bridge netPanel;
/*    */   Frame frame;
/*    */   VectorDialog stateDialog;
/* 18 */   int scnt = 2;
/*    */   
/*    */   public SpaceDialog(Frame paramFrame, String paramString, Bridge paramBridge) {
/* 21 */     super(paramFrame, paramString, false);
/* 22 */     setLocation(paramFrame.getLocationOnScreen().x + 100, paramFrame.getLocationOnScreen().y + 100);
/*    */     
/* 24 */     setFont(NetPaint.helvetica15);
/* 25 */     this.frame = paramFrame;
/* 26 */     this.netPanel = paramBridge;
/*    */     
/* 28 */     Panel localPanel1 = new Panel();
/* 29 */     java.awt.Label localLabel1 = new java.awt.Label("Variable Label :");
/* 30 */     localPanel1.add(localLabel1);
/* 31 */     this.labelField = new TextField(16);
/* 32 */     String[] arrayOfString = this.netPanel.getVector3(-1);
/* 33 */     this.labelField.setText(arrayOfString[0]);
/* 34 */     localPanel1.add(this.labelField);
/* 35 */     add("Center", localPanel1);
/*    */     
/* 37 */     Panel localPanel2 = new Panel();
/* 38 */     java.awt.Label localLabel2 = new java.awt.Label("Number of Values :");
/* 39 */     localPanel2.add(localLabel2);
/* 40 */     this.scntField = new TextField(5);
/* 41 */     this.scntField.setText(String.valueOf(this.netPanel.getVector2(-1).length));
/* 42 */     localPanel2.add(this.scntField);
/* 43 */     this.doneButton = new java.awt.Button("Done");
/* 44 */     localPanel2.add(this.doneButton);
/* 45 */     add("South", localPanel2);
/* 46 */     this.doneButton.addActionListener(this);
/* 47 */     pack();
/*    */   }
/*    */   
/*    */   public void actionPerformed(java.awt.event.ActionEvent paramActionEvent)
/*    */   {
/* 52 */     if (paramActionEvent.getSource() == this.doneButton) {
/* 53 */       getUserInput();
/* 54 */       this.netPanel.showNet();
/* 55 */       dispose();
/*    */     }
/*    */   }
/*    */   
/*    */   void getUserInput()
/*    */   {
/* 61 */     String[] arrayOfString1 = new String[1];
/* 62 */     arrayOfString1[0] = new String(this.labelField.getText());
/* 63 */     this.netPanel.setVector2(arrayOfString1);
/*    */     
/* 65 */     java.util.StringTokenizer localStringTokenizer = new java.util.StringTokenizer(this.scntField.getText());
/* 66 */     this.scnt = Integer.parseInt(localStringTokenizer.nextToken());
/* 67 */     if (this.scnt < 2) {
/* 68 */       this.scnt = 2;
/* 69 */       HelpPanel.showError("Number of states is set to default 2.");
/*    */     }
/*    */     
/*    */ 
/* 73 */     String[] arrayOfString2 = new String[this.scnt];
/* 74 */     for (int i = 0; i < this.scnt; i++) { arrayOfString2[i] = new String("State " + i);
/*    */     }
/* 76 */     String[] arrayOfString3 = this.netPanel.getVector2(-1);
/* 77 */     String[] arrayOfString4 = new String[this.scnt];
/* 78 */     for (int j = 0; j < this.scnt; j++) {
/* 79 */       if (j < arrayOfString3.length) arrayOfString4[j] = new String(arrayOfString3[j]); else
/* 80 */         arrayOfString4[j] = new String("");
/*    */     }
/* 82 */     this.stateDialog = new VectorDialog(this.frame, this.netPanel, this.labelField.getText(), arrayOfString2, arrayOfString4, 16, 100, 100);
/*    */     
/* 84 */     this.stateDialog.setVisible(true);
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/SpaceDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */