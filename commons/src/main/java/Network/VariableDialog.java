/*     */ package Network;
/*     */ 
/*     */ import java.awt.Frame;
/*     */ import java.awt.Label;
/*     */ import java.awt.Panel;
/*     */ import java.awt.TextField;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class VariableDialog extends java.awt.Dialog implements java.awt.event.ActionListener
/*     */ {
/*     */   TextField labelField;
/*     */   TextField scntField;
/*     */   TextField lowField;
/*     */   TextField highField;
/*  16 */   final int FIELD_WIDTH = 16;
/*     */   
/*     */   java.awt.Button doneButton;
/*     */   
/*     */   Bridge netPanel;
/*     */   Frame frame;
/*     */   VectorDialog valueDialog;
/*  23 */   int scnt = 2;
/*     */   
/*     */   public VariableDialog(Frame paramFrame, String paramString, Bridge paramBridge) {
/*  26 */     super(paramFrame, paramString, false);
/*  27 */     setLocation(paramFrame.getLocationOnScreen().x + 100, paramFrame.getLocationOnScreen().y + 100);
/*     */     
/*  29 */     setFont(NetPaint.helvetica15);
/*  30 */     this.frame = paramFrame;
/*  31 */     this.netPanel = paramBridge;
/*     */     
/*  33 */     Panel localPanel1 = new Panel();
/*  34 */     Label localLabel1 = new Label("Variable Label");
/*  35 */     localPanel1.add(localLabel1);
/*  36 */     this.labelField = new TextField(16);
/*  37 */     String[] arrayOfString = this.netPanel.getVector3(-1);
/*  38 */     this.labelField.setText(arrayOfString[0]);
/*  39 */     localPanel1.add(this.labelField);
/*  40 */     add("North", localPanel1);
/*     */     
/*  42 */     Panel localPanel2 = new Panel();
/*  43 */     Label localLabel2 = new Label("Number of Values");
/*  44 */     localPanel2.add(localLabel2);
/*  45 */     this.scntField = new TextField(5);
/*  46 */     this.scntField.setText(String.valueOf(this.netPanel.getVector2(-1).length));
/*  47 */     localPanel2.add(this.scntField);
/*     */     
/*  49 */     this.doneButton = new java.awt.Button("Done");
/*  50 */     localPanel2.add(this.doneButton);
/*  51 */     add("Center", localPanel2);
/*  52 */     this.doneButton.addActionListener(this);
/*     */     
/*  54 */     Panel localPanel3 = new Panel();
/*  55 */     Label localLabel3 = new Label("If continous: low");
/*  56 */     localPanel3.add(localLabel3);
/*  57 */     this.lowField = new TextField(5);
/*  58 */     localPanel3.add(this.lowField);
/*  59 */     Label localLabel4 = new Label("high");
/*  60 */     localPanel3.add(localLabel4);
/*  61 */     this.highField = new TextField(5);
/*  62 */     localPanel3.add(this.highField);
/*  63 */     add("South", localPanel3);
/*     */     
/*  65 */     pack();
/*     */   }
/*     */   
/*     */   public void actionPerformed(ActionEvent paramActionEvent)
/*     */   {
/*  70 */     if (paramActionEvent.getSource() == this.doneButton) {
/*  71 */       getUserInput();
/*  72 */       this.netPanel.showNet();
/*  73 */       dispose();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void getUserInput()
/*     */   {
/*  96 */     String[] arrayOfString1 = new String[1];
/*  97 */     arrayOfString1[0] = new String(this.labelField.getText());
/*  98 */     this.netPanel.setVector2(arrayOfString1);
/*     */     
/*     */ 
/* 101 */     StringTokenizer localStringTokenizer1 = new StringTokenizer(this.scntField.getText());
/* 102 */     this.scnt = Integer.parseInt(localStringTokenizer1.nextToken());
/* 103 */     if (this.scnt < 2) {
/* 104 */       this.scnt = 2;
/* 105 */       HelpPanel.showError("Set number of values to 2.");
/*     */     }
/*     */     
/* 108 */     localStringTokenizer1 = new StringTokenizer(this.lowField.getText());
/* 109 */     StringTokenizer localStringTokenizer2 = new StringTokenizer(this.highField.getText());
/* 110 */     String[] arrayOfString2 = new String[this.scnt];
/* 111 */     if ((localStringTokenizer1.countTokens() > 0) && (localStringTokenizer2.countTokens() > 0)) {
/* 112 */       float f1 = Float.parseFloat(localStringTokenizer1.nextToken());
/* 113 */       float f2 = Float.parseFloat(localStringTokenizer2.nextToken());
/* 114 */       if (f1 < f2) {
/* 115 */         float f3 = (f2 - f1) / this.scnt;
/* 116 */         for (int k = 0; k < this.scnt; k++) arrayOfString2[k] = new String("" + (f1 + f3 * k));
/*     */       }
/*     */     } else {
/* 119 */       arrayOfString2 = this.netPanel.getVector2(-1);
/*     */     }
/*     */     
/* 122 */     String[] arrayOfString3 = new String[this.scnt];
/* 123 */     for (int i = 0; i < this.scnt; i++) { arrayOfString3[i] = new String("State " + i);
/*     */     }
/* 125 */     String[] arrayOfString4 = new String[this.scnt];
/* 126 */     for (int j = 0; j < this.scnt; j++) {
/* 127 */       if (j < arrayOfString2.length) arrayOfString4[j] = new String(arrayOfString2[j]); else
/* 128 */         arrayOfString4[j] = new String("s" + j);
/*     */     }
/* 130 */     this.valueDialog = new VectorDialog(this.frame, this.netPanel, this.labelField.getText(), arrayOfString3, arrayOfString4, 16, 100, 100);
/*     */     
/* 132 */     this.valueDialog.setVisible(true);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/VariableDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */