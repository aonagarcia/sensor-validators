/*     */ package Network;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Button;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Panel;
/*     */ import java.awt.Point;
/*     */ import java.awt.Scrollbar;
/*     */ import java.awt.TextField;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.AdjustmentEvent;
/*     */ import java.awt.event.AdjustmentListener;
/*     */ 
/*     */ public class ProbDialog
/*     */   extends Dialog implements ActionListener, AdjustmentListener
/*     */ {
/*     */   Bridge panel;
/*     */   String titleStr;
/*     */   int tbIndx;
/*     */   String[] varName;
/*     */   String[][] varState;
/*     */   float[] bfls;
/*     */   int fwidth;
/*     */   int height;
/*     */   TextField bfColLabel;
/*     */   TextField[] tf;
/*     */   TextField[][] rowLabel;
/*     */   int[][] index;
/*     */   Button closeButton;
/*     */   Button normButton;
/*     */   static final String distrButtonString = "Normalize";
/*     */   static final String potenButtonString = "Potential";
/*     */   static final String distrLabelString = "Distribution";
/*     */   static final String potenLabelString = " Potential  ";
/*     */   Scrollbar bar;
/*  40 */   int row0 = 0;
/*  41 */   int row1 = 0;
/*  42 */   int rows = 0;
/*     */   
/*  44 */   Font helvetica15 = new Font("Helvetic", 1, 15);
/*     */   
/*     */ 
/*     */   public ProbDialog(Frame paramFrame, Bridge paramBridge, String paramString, String[] paramArrayOfString, String[][] paramArrayOfString1, float[] paramArrayOfFloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/*  49 */     super(paramFrame, "", false);
/*  50 */     setLocation(paramFrame.getLocationOnScreen().x + paramInt4, paramFrame.getLocationOnScreen().y + paramInt5);
/*     */     
/*  52 */     setFont(this.helvetica15);
/*     */     
/*  54 */     this.panel = paramBridge;
/*  55 */     this.titleStr = paramString;
/*  56 */     this.varName = paramArrayOfString;
/*  57 */     this.varState = paramArrayOfString1;
/*  58 */     this.bfls = paramArrayOfFloat;
/*  59 */     this.fwidth = paramInt2;
/*  60 */     this.height = paramInt3;
/*  61 */     this.tbIndx = paramInt1;
/*  62 */     setUpDialog();
/*     */   }
/*     */   
/*     */   void setUpDialog() {
/*  66 */     int i = this.varName.length;
/*  67 */     int j = this.bfls.length;
/*     */     
/*  69 */     int[] arrayOfInt = new int[i];
/*  70 */     for (int k = 0; k < i; k++) arrayOfInt[k] = this.varState[k].length;
/*  71 */     this.index = MATH.getAllMix(arrayOfInt);
/*     */     
/*     */ 
/*  74 */     setLayout(new BorderLayout());
/*  75 */     Panel localPanel1 = new Panel();
/*  76 */     add("North", localPanel1);
/*  77 */     Panel localPanel2 = new Panel();
/*  78 */     add("Center", localPanel2);
/*  79 */     Panel localPanel3 = new Panel();
/*  80 */     add("South", localPanel3);
/*  81 */     Panel localPanel4 = new Panel();
/*  82 */     add("East", localPanel4);
/*     */     
/*     */ 
/*  85 */     localPanel1.setLayout(new BorderLayout());
/*  86 */     Panel localPanel5 = new Panel();
/*  87 */     localPanel1.add("West", localPanel5);
/*  88 */     Panel localPanel6 = new Panel();
/*  89 */     localPanel1.add("East", localPanel6);
/*     */     
/*     */ 
/*  92 */     TextField localTextField = new TextField(this.fwidth);
/*  93 */     localTextField.setEditable(false);
/*  94 */     localTextField.setText(this.titleStr);
/*  95 */     localPanel5.add(localTextField);
/*     */     
/*     */ 
/*  98 */     this.normButton = new Button("Normalize");
/*  99 */     this.normButton.addActionListener(this);
/* 100 */     localPanel6.add(this.normButton);
/* 101 */     this.closeButton = new Button("Close");
/* 102 */     this.closeButton.addActionListener(this);
/* 103 */     localPanel6.add(this.closeButton);
/*     */     
/*     */ 
/* 106 */     localPanel2.setLayout(new BorderLayout());
/* 107 */     Panel localPanel7 = new Panel();
/* 108 */     localPanel2.add("Center", localPanel7);
/* 109 */     Panel localPanel8 = new Panel();
/* 110 */     localPanel2.add("East", localPanel8);
/*     */     
/*     */ 
/*     */ 
/* 114 */     localPanel7.setLayout(new GridLayout(1, 1));
/* 115 */     this.bfColLabel = new TextField(this.fwidth);
/* 116 */     this.bfColLabel.setEditable(false);
/* 117 */     this.bfColLabel.setText(" Potential  ");
/* 118 */     localPanel7.add(this.bfColLabel);
/* 119 */     for (int m = 0; m < i; m++) {
/* 120 */       localTextField = new TextField(this.fwidth);
/* 121 */       localTextField.setEditable(false);
/* 122 */       localTextField.setText(this.varName[m]);
/* 123 */       localPanel7.add(localTextField);
/*     */     }
/*     */     
/*     */ 
/* 127 */     this.rows = (j > this.height ? this.height : j);
/* 128 */     this.tf = new TextField[this.rows];
/* 129 */     this.rowLabel = new TextField[this.rows][i];
/*     */     
/*     */ 
/* 132 */     localPanel3.setLayout(new BorderLayout());
/* 133 */     Panel localPanel9 = new Panel();
/* 134 */     localPanel3.add("West", localPanel9);
/* 135 */     Panel localPanel10 = new Panel();
/* 136 */     localPanel3.add("Center", localPanel10);
/* 137 */     this.bar = new Scrollbar(1, this.row0, this.rows, 0, j);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 142 */     this.bar.addAdjustmentListener(this);
/* 143 */     localPanel3.add("East", this.bar);
/*     */     
/*     */ 
/* 146 */     localPanel9.setLayout(new GridLayout(this.rows, 1));
/* 147 */     this.row0 = 0;
/* 148 */     for (int n = this.row0; n < this.rows; n++) {
/* 149 */       this.tf[n] = new TextField(this.fwidth);
/* 150 */       this.tf[n].setText(String.valueOf(this.bfls[n]));
/* 151 */       this.tf[n].setEditable(false);
/* 152 */       localPanel9.add(this.tf[n]);
/*     */     }
/*     */     
/*     */ 
/* 156 */     localPanel10.setLayout(new GridLayout(this.rows, i));
/* 157 */     for (n = this.row0; n < this.rows; n++) {
/* 158 */       for (int i1 = 0; i1 < i; i1++) {
/* 159 */         localTextField = this.rowLabel[n][i1] = new TextField(this.fwidth);
/* 160 */         localTextField.setEditable(false);
/* 161 */         localTextField.setText(this.varState[i1][this.index[n][i1]]);
/* 162 */         localPanel10.add(localTextField);
/*     */       }
/*     */     }
/*     */     
/* 166 */     getPageLimit();
/* 167 */     pack();
/*     */   }
/*     */   
/*     */ 
/*     */   void getPageLimit()
/*     */   {
/* 173 */     int i = this.bfls.length;
/* 174 */     if (this.row0 + this.rows >= i) {
/* 175 */       this.row1 = (i - 1);
/* 176 */       this.row0 = (this.row1 - this.rows + 1);
/*     */     } else {
/* 178 */       this.row1 = (this.row0 + this.rows - 1);
/*     */     }
/*     */   }
/*     */   
/*     */   void showOnePageElement() {
/* 183 */     for (int i = this.row0; i <= this.row1; i++) {
/* 184 */       this.tf[(i - this.row0)].setText(String.valueOf(this.bfls[i]));
/*     */     }
/*     */   }
/*     */   
/*     */   void showOnePageRowLabel()
/*     */   {
/* 190 */     int i = this.varName.length;
/* 191 */     for (int j = this.row0; j <= this.row1; j++) {
/* 192 */       for (int k = 0; k < i; k++) {
/* 193 */         StringBuffer localStringBuffer = new StringBuffer(this.varState[k][this.index[j][k]]);
/* 194 */         localStringBuffer.setLength(this.fwidth);
/* 195 */         this.rowLabel[(j - this.row0)][k].setText(localStringBuffer.toString());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void actionPerformed(ActionEvent paramActionEvent)
/*     */   {
/* 203 */     if (paramActionEvent.getSource() == this.closeButton) {
/* 204 */       this.panel.showNet();
/* 205 */       dispose();
/*     */     }
/* 207 */     else if (paramActionEvent.getSource() == this.normButton) {
/* 208 */       this.bfls = this.panel.getVector(this.tbIndx);
/* 209 */       if (this.normButton.getLabel() == "Normalize") {
/* 210 */         this.normButton.setLabel("Potential");
/* 211 */         this.bfColLabel.setText("Distribution");
/* 212 */         this.bfls = MATH.normalize(this.bfls);
/*     */       }
/*     */       else {
/* 215 */         this.normButton.setLabel("Normalize");
/* 216 */         this.bfColLabel.setText(" Potential  ");
/*     */       }
/* 218 */       showOnePageElement();
/*     */     }
/*     */   }
/*     */   
/*     */   public void adjustmentValueChanged(AdjustmentEvent paramAdjustmentEvent)
/*     */   {
/* 224 */     this.row0 = ((Scrollbar)paramAdjustmentEvent.getSource()).getValue();
/* 225 */     getPageLimit();
/* 226 */     showOnePageElement();
/* 227 */     showOnePageRowLabel();
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/ProbDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */